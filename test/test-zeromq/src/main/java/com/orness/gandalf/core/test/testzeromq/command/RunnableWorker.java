package com.orness.gandalf.core.test.testzeromq.command;

import com.google.gson.Gson;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

import java.util.*;

import static com.orness.gandalf.core.test.testzeromq.Constant.*;

public abstract class RunnableWorker extends Worker implements Runnable {

    protected Gson mapper;
    private Map<String, Deque<ZMsg>> commandDeque;

    public RunnableWorker() {
        super();
        mapper = new Gson();
    }

    @Override
    public void run() {

        // Initialize poll set
        ZMQ.Poller poller = context.createPoller(2);
        poller.register(this.worker, ZMQ.Poller.POLLIN);
        poller.register(this.command, ZMQ.Poller.POLLIN);

        ZMsg request;
        ZMsg response;

        // Switch messages between sockets
        while (!Thread.currentThread().isInterrupted()) {

            // Client
            if (poller.pollin(0)) {
                while (true) {
                    // Receive broker message
                    request = ZMsg.recvMsg(this.worker);
                    if (request == null) {
                        break; // Interrupted
                    }
                    // Broker it
                    this.processBrokerRequest(request);
                }
            }

            // Worker
            if (poller.pollin(1)) {
                while (true) {
                    // Receive command message
                    response = ZMsg.recvMsg(this.command);
                    if (response == null) {
                        break; // Interrupted
                    }
                    // Broker it
                    this.processCommandResponse(response);
                }
            }
            poller.close();
        }

        if (Thread.currentThread().isInterrupted()) {
            System.out.println("W: interrupted");
            this.close(); // interrupted
        }
    }

    private void processBrokerRequest(ZMsg request) {

        ZMsg requestBackup = request.duplicate();
        String uuid = requestBackup.popString();
        String clientIdentity = requestBackup.popString();
        String service = requestBackup.popString();
        String command = requestBackup.popString();
        String payload = requestBackup.popString();
        this.commandDeque.get(command).addLast(request);
    }

    private void processCommandResponse(ZMsg response) {

        ZMsg responseBackup = response.duplicate();
        String uuid = responseBackup.popString();
        String clientIdentity = responseBackup.popString();
        String service = responseBackup.popString();
        String commandType = responseBackup.popString();
        String command;
        if (commandType.equals(COMMAND_COMMAND_READY)) {
            command = responseBackup.popString();
            if(this.commandDeque.containsKey(command)) {
                if(this.commandDeque.get(command).getFirst() != null) {
                    this.sendToCommand(this.commandDeque.get(command).getFirst());
                }
            }
            else {
                this.commandDeque.put(command, new ArrayDeque<>());
            }
        }
        else if (commandType.equals(WORKER_COMMAND_RESULT)) {
            this.sendToBroker(response);
            this.sendReadyCommand();
        }
        else {
            System.out.println("E: invalid message");
        }
        //TODO
        //msg.destroy();
    }

    public void sendToCommand(ZMsg request) {
        //Command
        request.send(this.command);
    }

    public void sendToBroker(ZMsg response) {
        //Worker
        response.send(this.worker);
    }
}
