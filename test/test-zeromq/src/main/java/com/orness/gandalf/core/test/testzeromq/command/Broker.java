package com.orness.gandalf.core.test.testzeromq.command;

import org.zeromq.*;

import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

import static com.orness.gandalf.core.test.testzeromq.Constant.*;

public class Broker {

    public static ZMQ.Socket frontEndCommand;
    private String frontEndCommandConnection;
    public static ZMQ.Socket backEndCommand;
    private String backEndCommandConnection;
    public static ZMQ.Socket backEndCommandCapture;
    private String backEndCaptureCommandConnection;
    private ZContext context;
    private Map<String, Deque<ZMsg>> serviceDeque;

    public Broker(String frontEndCommandConnection, String backEndCommandConnection, String backEndCaptureCommandConnection) {

        this.frontEndCommandConnection = frontEndCommandConnection;
        this.backEndCommandConnection = backEndCommandConnection;
        this.backEndCaptureCommandConnection = backEndCaptureCommandConnection;
        this.serviceDeque = new HashMap<>();
        this.open();
        this.run();
    }

    public void open() {

        this.context = new ZContext();

        // Client EndPoint
        this.frontEndCommand = this.context.createSocket(SocketType.ROUTER);
        System.out.println("BrokerClientZeroMQ binding to: " + this.frontEndCommandConnection);
        this.frontEndCommand.bind(this.frontEndCommandConnection);

        // Worker EndPoint
        this.backEndCommand = this.context.createSocket(SocketType.ROUTER);
        System.out.println("BrokerClientZeroMQ binding to: " + this.backEndCommandConnection);
        this.backEndCommand.bind(this.backEndCommandConnection);

        //Capture EndPoint
        this.backEndCommandCapture = this.context.createSocket(SocketType.DEALER);
        System.out.println("BrokerCaptureZeroMQ binding to: " + this.backEndCaptureCommandConnection);
        this.backEndCommandCapture.bind(this.backEndCaptureCommandConnection);
    }

    public void run() {

        // Initialize poll set
        ZMQ.Poller poller = context.createPoller(2);
        poller.register(this.frontEndCommand, ZMQ.Poller.POLLIN);
        poller.register(this.backEndCommand, ZMQ.Poller.POLLIN);

        ZMsg request;
        ZMsg response;

        // Switch messages between sockets
        while (!Thread.currentThread().isInterrupted()) {

            // Client
            if (poller.pollin(0)) {
                while (true) {
                    // Receive client message
                    request = ZMsg.recvMsg(this.frontEndCommand);
                    if (request == null) {
                        break; // Interrupted
                    }
                    // Broker it
                    this.processRequest(request);
                }
            }

            // Worker
            if (poller.pollin(1)) {
                while (true) {
                    // Receive worker message
                    response = ZMsg.recvMsg(this.backEndCommand);
                    if (response == null) {
                        break; // Interrupted
                    }
                    // Broker it
                   this.processResponse(response);
                }
            }
        }

        if (Thread.currentThread().isInterrupted()) {
            System.out.println("W: interrupted");
            poller.close();
            this.close(); // interrupted
        }
    }

    public void close() {

        this.frontEndCommand.close();
        this.backEndCommand.close();
        this.backEndCommandCapture.close();
        this.context.destroy();
    }

    private void processRequest(ZMsg request) {

        ZMsg requestBackup = request.duplicate();
        String uuid = requestBackup.popString();
        String commandType = requestBackup.popString();
        String service = requestBackup.popString();
        this.serviceDeque.get(service).addLast(request);
    }

    private void processResponse(ZMsg response) {

        ZMsg responseBackup = response.duplicate();
        String commandType = responseBackup.popString();
        String service;
        if (commandType.equals(WORKER_COMMAND_READY)) {
            service = responseBackup.popString();
            this.sendToWorker(this.serviceDeque.get(service).getFirst());

        }
        else if (commandType.equals(WORKER_COMMAND_RESULT)) {
            this.sendToClient(response);
        }
        else {
            System.out.println("E: invalid message");
        }
        //TODO
        //msg.destroy();
    }

    public void sendToWorker(ZMsg request) {

        ZMsg requestCapture = request.duplicate();
        //Capture
        requestCapture.send(this.backEndCommandCapture);
        //Worker
        request.send(this.backEndCommand);
    }

    public void sendToClient(ZMsg response) {

        ZMsg responseCapture = response.duplicate();
        //Capture
        responseCapture.send(this.backEndCommandCapture);
        //Client
        response.send(this.frontEndCommand);
    }
}
