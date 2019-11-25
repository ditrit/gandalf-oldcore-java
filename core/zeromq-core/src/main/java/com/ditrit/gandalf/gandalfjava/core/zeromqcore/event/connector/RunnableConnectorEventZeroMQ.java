package com.ditrit.gandalf.gandalfjava.core.zeromqcore.event.connector;

import com.google.gson.Gson;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

public abstract class RunnableConnectorEventZeroMQ extends ConnectorEventZeroMQ implements Runnable {

    protected Gson mapper;

    public RunnableConnectorEventZeroMQ() {
        super();
        mapper = new Gson();
    }

    protected void initRunnable(String identity, String frontEndSendConnectorConnection, String backEndSenConnectorConnection, String frontEndReceiveConnectorConnection, String backEndReceiveConnectorConnection) {
        this.init(identity, frontEndSendConnectorConnection, backEndSenConnectorConnection, frontEndReceiveConnectorConnection, backEndReceiveConnectorConnection);
    }

    @Override
    public void run() {
        ZMQ.Poller poller = context.createPoller(4);
        poller.register(this.frontEndSendRoutingConnector, ZMQ.Poller.POLLIN);
        poller.register(this.backEndSendRoutingConnector, ZMQ.Poller.POLLIN);
        poller.register(this.frontEndReceiveRoutingConnector, ZMQ.Poller.POLLIN);
        poller.register(this.backEndReceiveRoutingConnector, ZMQ.Poller.POLLIN);

        ZMsg publish;
        boolean more = false;

        while (!Thread.currentThread().isInterrupted()) {
            poller.poll();

            if (poller.pollin(0)) {
                while (true) {
                    publish = ZMsg.recvMsg(this.frontEndSendRoutingConnector);
                    more = this.frontEndSendRoutingConnector.hasReceiveMore();
                    System.out.println("BLOOP CLUSTER");
                    System.out.println(publish);
                    if (publish == null) {
                        break;
                    }
                    publish.send(this.backEndSendRoutingConnector);

                    if(!more) {
                        break;
                    }
                }
            }

            if (poller.pollin(1)) {
                while (true) {
                    publish = ZMsg.recvMsg(this.backEndSendRoutingConnector);
                    more = this.backEndSendRoutingConnector.hasReceiveMore();
                    System.out.println("PUBLISH WORKER");
                    System.out.println(publish);
                    if (publish == null) {
                        break;
                    }
                    this.processConnectorPublish(publish);

                    if(!more) {
                        break;
                    }
                }
            }

            if (poller.pollin(2)) {
                while (true) {
                    publish = ZMsg.recvMsg(this.frontEndReceiveRoutingConnector);
                    more = this.frontEndReceiveRoutingConnector.hasReceiveMore();
                    System.out.println("PUBLISH CLUSTER");
                    System.out.println(publish);
                    if (publish == null) {
                        break;
                    }
                    this.processProxyPublish(publish);

                    if(!more) {
                        break;
                    }
                }
            }

            if (poller.pollin(3)) {
                while (true) {
                    publish = ZMsg.recvMsg(this.backEndReceiveRoutingConnector);
                    more = this.backEndReceiveRoutingConnector.hasReceiveMore();
                    System.out.println("BLOOP WORKER");
                    System.out.println(publish);
                    if (publish == null) {
                        break;
                    }
                    publish.send(this.frontEndReceiveRoutingConnector);

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

    private void processProxyPublish(ZMsg publish) {
        this.sendToConnector(publish);
        publish.destroy();
    }

    private void processConnectorPublish(ZMsg publish) {
        this.sendToProxy(publish);
        publish.destroy();
    }

    public void sendToConnector(ZMsg publish) {
        publish.send(this.backEndReceiveRoutingConnector);
    }

    public void sendToProxy(ZMsg publish) {
        publish.send(this.frontEndSendRoutingConnector);
    }
}
