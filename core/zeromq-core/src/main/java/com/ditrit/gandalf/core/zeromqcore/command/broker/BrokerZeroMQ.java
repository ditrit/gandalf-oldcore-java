package com.ditrit.gandalf.core.zeromqcore.command.broker;

import com.ditrit.gandalf.core.zeromqcore.command.router.ClusterCommandRouter;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

import static com.ditrit.gandalf.core.zeromqcore.constant.Constant.WORKER_SERVICE_CLASS_CAPTURE;

public class BrokerZeroMQ {

    public ZMQ.Socket frontEndCommand;
    private String frontEndCommandConnection;
    public ZMQ.Socket backEndCommand;
    private String backEndCommandConnection;
    public ZMQ.Socket backEndCommandCapture;
    private String backEndCaptureCommandConnection;
    private ClusterCommandRouter clusterCommandRouter;
    private ZContext context;

    public BrokerZeroMQ(String frontEndCommandConnection, String backEndCommandConnection, String backEndCaptureCommandConnection, ClusterCommandRouter clusterCommandRouter) {

        this.frontEndCommandConnection = frontEndCommandConnection;
        this.backEndCommandConnection = backEndCommandConnection;
        this.backEndCaptureCommandConnection = backEndCaptureCommandConnection;
        this.clusterCommandRouter = clusterCommandRouter;
        this.open();
        this.run();
    }

    protected void open() {

        this.context = new ZContext();

        //FrontEnd EndPoint
        this.frontEndCommand = this.context.createSocket(SocketType.ROUTER);
        System.out.println("BrokerClientZeroMQ binding to frontEndCommandConnection: " + this.frontEndCommandConnection);
        this.frontEndCommand.bind(this.frontEndCommandConnection);

        //BackEnd EndPoint
        this.backEndCommand = this.context.createSocket(SocketType.ROUTER);
        System.out.println("BrokerClientZeroMQ binding to backEndCommandConnection: " + this.backEndCommandConnection);
        this.backEndCommand.bind(this.backEndCommandConnection);

        //Capture EndPoint
        this.backEndCommandCapture = this.context.createSocket(SocketType.ROUTER);
        System.out.println("BrokerCaptureZeroMQ binding to backEndCaptureCommandConnection: " + this.backEndCaptureCommandConnection);
        this.backEndCommandCapture.bind(this.backEndCaptureCommandConnection);

    }

    protected void run() {

        ZMQ.Poller poller = context.createPoller(2);
        poller.register(this.frontEndCommand, ZMQ.Poller.POLLIN);
        poller.register(this.backEndCommand, ZMQ.Poller.POLLIN);

        ZMsg frontEndMessage;
        ZMsg backEndMessage;
        boolean more = false;

        while (!Thread.currentThread().isInterrupted()) {
            poller.poll();

            if (poller.pollin(0)) {
                while (true) {
                    frontEndMessage = ZMsg.recvMsg(this.frontEndCommand);
                    more = this.frontEndCommand.hasReceiveMore();
                    System.out.println(frontEndMessage);
                    System.out.println(more);

                    if (frontEndMessage == null) {
                        break;
                    }

                    this.processFrontEndMessage(frontEndMessage);

                    if(!more) {
                        break;
                    }
                }
            }

            if (poller.pollin(1)) {
                while (true) {
                    backEndMessage = ZMsg.recvMsg(this.backEndCommand);
                    more = this.backEndCommand.hasReceiveMore();

                    System.out.println(backEndMessage);
                    System.out.println(more);

                    if (backEndMessage == null) {
                        break;
                    }

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
            this.close();
        }
    }

    public void close() {
        this.frontEndCommand.close();
        this.backEndCommand.close();
        this.backEndCommandCapture.close();
        this.context.destroy();
    }

    protected void processFrontEndMessage(ZMsg frontEndMessage) {
        Object[] frontEndMessageBackup = frontEndMessage.duplicate().toArray();

        //Capture
        ZMsg frontEndMessageCapture = frontEndMessage.duplicate();
        this.processFrontEndCaptureMessage(frontEndMessageCapture);

        frontEndMessage = this.updateIdentityAggregatorMessage(frontEndMessage);
        frontEndMessage = this.updateTargetMessage(frontEndMessage);
        frontEndMessage = this.updateHeaderFrontEndMessage(frontEndMessage, frontEndMessageBackup[3].toString());
        System.out.println("FRONT " + frontEndMessage);
        frontEndMessage.send(this.backEndCommand);

        frontEndMessageCapture.destroy();
        frontEndMessage.destroy();
    }

    private ZMsg updateIdentityAggregatorMessage(ZMsg frontEndMessage) {
        String sourceAggregator = frontEndMessage.popString();
        String uuid = frontEndMessage.popString();
        frontEndMessage.addFirst(sourceAggregator);
        frontEndMessage.addFirst(uuid);
        return frontEndMessage;
    }

    private ZMsg updateTargetMessage(ZMsg frontEndMessage) {
        frontEndMessage = this.clusterCommandRouter.getCommandTarget(frontEndMessage);
        return frontEndMessage;
    }

    private ZMsg updateHeaderFrontEndMessage(ZMsg frontEndMessage, String targetAggregator) {
        frontEndMessage.addFirst(targetAggregator);
        return frontEndMessage;
    }

    protected void processBackEndMessage(ZMsg backEndMessage) {
        Object[] backEndMessageBackup = backEndMessage.duplicate().toArray();

        //Capture
        ZMsg backEndMessageCapture = backEndMessage.duplicate();
        this.processBackEndCaptureMessage(backEndMessageCapture);

        backEndMessage = this.updateHeaderBackEndMessage(backEndMessage, backEndMessageBackup[1].toString());
        System.out.println("BACK " + backEndMessage);
        backEndMessage.send(this.frontEndCommand);

        backEndMessageCapture.destroy();
        backEndMessage.destroy();
    }

    private ZMsg updateHeaderBackEndMessage(ZMsg backEndMessage, String client) {
        backEndMessage.removeFirst();
        backEndMessage.addFirst(client);
        return backEndMessage;
    }

    protected void processBackEndCaptureMessage(ZMsg backEndMessageCapture) {
        backEndMessageCapture = this.updateHeaderCaptureMessage(backEndMessageCapture);
        backEndMessageCapture.send(this.backEndCommandCapture);

    }

    protected void processFrontEndCaptureMessage(ZMsg frontEndMessageCapture) {
        frontEndMessageCapture = this.updateHeaderCaptureMessage(frontEndMessageCapture);
        frontEndMessageCapture.send(this.backEndCommandCapture);
    }

    private ZMsg updateHeaderCaptureMessage(ZMsg captureMessage) {
        captureMessage.addFirst(WORKER_SERVICE_CLASS_CAPTURE);
        return captureMessage;
    }

}
