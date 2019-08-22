package com.orness.gandalf.core.test.testzeromq.event;

import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

import static com.orness.gandalf.core.test.testzeromq.Constant.*;

public abstract class RunnableSubscriber extends Subscriber implements Runnable {

    private String topic;
    private Map<String, Deque<ZMsg>> commandDeque;

    public RunnableSubscriber() {
        super();
    }

    public void init(String topic) {
        this.topic = topic;
        this.frontEndSubscriber.subscribe(this.topic.getBytes());
    }

    @Override
    public void run() {
        // Initialize poll set
        ZMQ.Poller poller = context.createPoller(2);
        poller.register(this.frontEndSubscriber, ZMQ.Poller.POLLIN);
        poller.register(this.backEndSubscriber, ZMQ.Poller.POLLIN);

        ZMsg publish;
        ZMsg response;

        // Switch messages between sockets
        while (!Thread.currentThread().isInterrupted()) {

            // Client
            if (poller.pollin(0)) {
                while (true) {
                    // Receive broker message
                    publish = ZMsg.recvMsg(this.frontEndSubscriber);
                    if (publish == null) {
                        break; // Interrupted
                    }
                    // Broker it
                    this.processProxyPublish(publish);
                }
            }

            // Worker
            if (poller.pollin(1)) {
                while (true) {
                    // Receive command message
                    response = ZMsg.recvMsg(this.backEndSubscriber);
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

    private void processProxyPublish(ZMsg publish) {

        ZMsg publishBackup = publish.duplicate();
        String uuid = publishBackup.popString();
        String topic = publishBackup.popString();
        String typeEvent = publishBackup.popString();
        String event;
        if(typeEvent.contains(WORKER_COMMAND_EVENT)) {
            event = publishBackup.popString();
            this.commandDeque.get(event).addLast(publish);
        }
    }

    private void processCommandResponse(ZMsg response) {

        ZMsg responseBackup = response.duplicate();
        String commandType = responseBackup.popString();
        String event;
        if (commandType.equals(COMMAND_COMMAND_READY)) {
            event = responseBackup.popString();
            if(this.commandDeque.containsKey(event)) {
                if(this.commandDeque.get(event).getFirst() != null) {
                    this.sendToCommand(this.commandDeque.get(event).getFirst());
                }
            }
            else {
                this.commandDeque.put(event, new ArrayDeque<>());
            }
        }
        else if (commandType.equals(WORKER_COMMAND_RESULT)) {
            //TODO
            //this.sendToBroker(response);
        }
        else {
            System.out.println("E: invalid message");
        }
        //TODO
        //msg.destroy();
    }

    public void sendToCommand(ZMsg publish) {
        //Command
        publish.send(this.backEndSubscriber);
    }
}
