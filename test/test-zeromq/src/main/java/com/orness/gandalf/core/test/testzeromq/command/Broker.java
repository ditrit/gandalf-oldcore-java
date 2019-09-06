package com.orness.gandalf.core.test.testzeromq.command;

import org.zeromq.*;

import java.util.ArrayDeque;
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

    public Broker(String frontEndCommandConnection, String backEndCommandConnection, String backEndCaptureCommandConnection) {

        this.frontEndCommandConnection = frontEndCommandConnection;
        this.backEndCommandConnection = backEndCommandConnection;
        this.backEndCaptureCommandConnection = backEndCaptureCommandConnection;
        this.open();
        this.run();
    }

    protected void open() {

        this.context = new ZContext();

        // Client EndPoint
        this.frontEndCommand = this.context.createSocket(SocketType.ROUTER);
        System.out.println("BrokerClientZeroMQ binding to frontEndCommandConnection: " + this.frontEndCommandConnection);
        this.frontEndCommand.bind(this.frontEndCommandConnection);

        // Worker EndPoint
        this.backEndCommand = this.context.createSocket(SocketType.ROUTER);
        System.out.println("BrokerClientZeroMQ binding to backEndCommandConnection: " + this.backEndCommandConnection);
        this.backEndCommand.bind(this.backEndCommandConnection);

        //TODO
/*        //Capture EndPoint
        this.backEndCommandCapture = this.context.createSocket(SocketType.DEALER);
        System.out.println("BrokerCaptureZeroMQ binding to backEndCaptureCommandConnection: " + this.backEndCaptureCommandConnection);
        this.backEndCommandCapture.bind(this.backEndCaptureCommandConnection);*/
    }

    protected void run() {

        // Initialize poll set
        ZMQ.Poller poller = context.createPoller(2);
        poller.register(this.frontEndCommand, ZMQ.Poller.POLLIN);
        poller.register(this.backEndCommand, ZMQ.Poller.POLLIN);

        ZMsg request;
        ZMsg response;
        boolean more = false;

        // Switch messages between sockets
        while (!Thread.currentThread().isInterrupted()) {
            poller.poll();

            //Client
            if (poller.pollin(0)) {
                while (true) {
                    // Receive client message
                    request = ZMsg.recvMsg(this.frontEndCommand);
                    more = this.frontEndCommand.hasReceiveMore();
                    System.out.println(request);
                    System.out.println(more);

                    if (request == null) {
                        break; // Interrupted
                    }

                    // Broker it
                    this.sendToRoutingWorker(request);

                    if(!more) {
                        break;
                    }
                }
            }

            //Worker
            if (poller.pollin(1)) {
                while (true) {
                    // Receive worker message
                    response = ZMsg.recvMsg(this.backEndCommand);
                    more = this.backEndCommand.hasReceiveMore();
                    ZMsg responseBackup = response.duplicate();
                    System.out.println(response);
                    System.out.println(more);

                    if (response == null) {
                        break; // Interrupted
                    }

                    // Broker it
                    String connector = responseBackup.popString();
                    String commandType = responseBackup.popString();
                    if (commandType.equals(COMMAND_COMMAND_RESULT)) {
                        this.sendToClient(response);
                    }
                    else {
                        System.out.println("E: invalid message");
                    }
                    response.destroy();

                    if(!more) {
                        break;
                    }
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


    private void processResponse(ZMsg response) {

    }

    protected void sendToRoutingWorker(ZMsg request) {
        ZMsg requestBackup = request.duplicate();
        String uuid = requestBackup.popString();
        String client = requestBackup.popString();
        String connector = requestBackup.popString();
        //Capture
        //TODO
        //requestCapture.send(this.backEndCommandCapture);
        //Worker
        request= this.addHeaderToRoutingWorker(request, connector);
        requestBackup.destroy();
        System.out.println("REQ " + request);
        request.send(this.backEndCommand);
    }

    private ZMsg addHeaderToRoutingWorker(ZMsg request, String connector) {
        request.addFirst(connector);
        return request;
    }

    //TODO
    protected void sendToClient(ZMsg response) {
        ZMsg responseCapture = response.duplicate();
        //Capture
        //TODO
        //responseCapture.send(this.backEndCommandCapture);
        //Client
        response = this.removeHeaderToClient(response);
        System.out.println("REP " + response);
        response.send(this.frontEndCommand);
    }

    private ZMsg removeHeaderToClient(ZMsg response) {
        response.removeFirst();
        response.removeFirst();
        return response;
    }
}
