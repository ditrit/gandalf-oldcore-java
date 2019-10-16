package com.ditrit.gandalf.core.zeromqcore.command.broker;

import com.ditrit.gandalf.core.zeromqcore.constant.Constant;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

public class BrokerZeroMQ {

    public ZMQ.Socket frontEndCommand;
    private String frontEndCommandConnection;
    public ZMQ.Socket backEndCommand;
    private String backEndCommandConnection;
    public ZMQ.Socket backEndCommandCapture;
    private String backEndCaptureCommandConnection;
    private ZContext context;

    public BrokerZeroMQ(String frontEndCommandConnection, String backEndCommandConnection, String backEndCaptureCommandConnection) {

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

        ZMsg frontEndMessage;
        ZMsg backEndMessage;
        boolean more = false;

        // Switch messages between sockets
        while (!Thread.currentThread().isInterrupted()) {
            poller.poll();

            //Client
            if (poller.pollin(0)) {
                while (true) {
                    frontEndMessage = ZMsg.recvMsg(this.frontEndCommand);
                    more = this.frontEndCommand.hasReceiveMore();
                    System.out.println(frontEndMessage);
                    System.out.println(more);

                    if (frontEndMessage == null) {
                        break; // Interrupted
                    }

                    // Broker it
                    this.processFrontEndMessage(frontEndMessage);

                    if(!more) {
                        break;
                    }
                }
            }

            //Worker
            if (poller.pollin(1)) {
                while (true) {
                    backEndMessage = ZMsg.recvMsg(this.backEndCommand);
                    more = this.backEndCommand.hasReceiveMore();

                    System.out.println(backEndMessage);
                    System.out.println(more);

                    if (backEndMessage == null) {
                        break; // Interrupted
                    }

                    // Broker it
                    this.processBackEndMessage(backEndMessage);

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

    protected void processFrontEndMessage(ZMsg frontEndMessage) {
        ZMsg frontEndMessageBackup = frontEndMessage.duplicate();
        String sourceAggregator = frontEndMessageBackup.popString();
        String uuid = frontEndMessageBackup.popString();
        String sourceConnector = frontEndMessageBackup.popString();
        String sourceWorker = frontEndMessageBackup.popString();
        String targetAggregator = frontEndMessageBackup.popString();
        //Capture
        //TODO
        //requestCapture.send(this.backEndCommandCapture);
        //Worker
        frontEndMessage = this.updateIdentityAggregatorMessage(frontEndMessage);
        frontEndMessage = this.updateHeaderFrontEndMessage(frontEndMessage, targetAggregator);
        frontEndMessageBackup.destroy();
        System.out.println("REQ " + frontEndMessage);
        frontEndMessage.send(this.backEndCommand);

        frontEndMessageBackup.destroy();
        frontEndMessage.destroy();
    }

    private ZMsg updateIdentityAggregatorMessage(ZMsg frontEndMessage) {
        String sourceAggregator = frontEndMessage.popString();
        String uuid = frontEndMessage.popString();
        frontEndMessage.addFirst(sourceAggregator);
        frontEndMessage.addFirst(uuid);
        return frontEndMessage;
    }

    private ZMsg updateHeaderFrontEndMessage(ZMsg frontEndMessage, String targetAggregator) {
        frontEndMessage.addFirst(targetAggregator);
        return frontEndMessage;
    }

    protected void processBackEndMessage(ZMsg backEndMessage) {
        ZMsg backEndMessageBackup = backEndMessage.duplicate();
        String targetAggregator = backEndMessageBackup.popString();
        String uuid = backEndMessageBackup.popString();
        String sourceAggregator = backEndMessageBackup.popString();
        String sourceConnector = backEndMessageBackup.popString();
        //TODO
        //Capture
        ZMsg backEndMessageCapture = backEndMessage.duplicate();
        //responseCapture.send(this.backEndCommandCapture);
        //Client
        backEndMessage = this.updateHeaderBackEndMessage(backEndMessage, sourceConnector);
        System.out.println("REP " + backEndMessage);
        backEndMessage.send(this.frontEndCommand);

        backEndMessageBackup.destroy();
        backEndMessage.destroy();
    }

    private ZMsg updateHeaderBackEndMessage(ZMsg backEndMessage, String client) {
        backEndMessage.removeFirst();
        backEndMessage.addFirst(client);
        return backEndMessage;
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
    protected void sendToClient(ZMsg response, String client) {
        ZMsg responseCapture = response.duplicate();
        //Capture
        //TODO
        //responseCapture.send(this.backEndCommandCapture);
        //Client
        response = this.removeHeaderToClient(response);
        response.addFirst(client);
        System.out.println("REP " + response);
        response.send(this.frontEndCommand);
    }

    private ZMsg removeHeaderToClient(ZMsg response) {
        response.removeFirst();
        //response.removeFirst();
        return response;
    }
}
