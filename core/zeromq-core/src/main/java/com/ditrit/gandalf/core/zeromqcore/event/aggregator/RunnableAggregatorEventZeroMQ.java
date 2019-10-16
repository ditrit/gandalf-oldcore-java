package com.ditrit.gandalf.core.zeromqcore.event.aggregator;

import com.google.gson.Gson;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

import java.util.List;

public abstract class RunnableAggregatorEventZeroMQ extends AggregatorEventZeroMQ implements Runnable {

    protected Gson mapper;

    public RunnableAggregatorEventZeroMQ() {
        super();
        mapper = new Gson();
    }

    protected void initRunnable(String identity, String frontEndSendAggregatorConnection, String backEndSenAggregatorConnection, List<String> frontEndReceiveAggregatorConnection, String backEndReceiveAggregatorConnection) {
        this.init(identity, frontEndSendAggregatorConnection, backEndSenAggregatorConnection, frontEndReceiveAggregatorConnection, backEndReceiveAggregatorConnection);
    }

    @Override
    public void run() {
        ZMQ.Poller poller = context.createPoller(4);
        poller.register(this.frontEndSendRoutingAggregator, ZMQ.Poller.POLLIN);
        poller.register(this.backEndSendRoutingAggregator, ZMQ.Poller.POLLIN);
        poller.register(this.frontEndReceiveRoutingAggregator, ZMQ.Poller.POLLIN);
        poller.register(this.backEndReceiveRoutingAggregator, ZMQ.Poller.POLLIN);

        ZMsg publish;
        boolean more = false;

        while (!Thread.currentThread().isInterrupted()) {
            poller.poll();

            if (poller.pollin(0)) {
                while (true) {
                    publish = ZMsg.recvMsg(this.frontEndSendRoutingAggregator);
                    more = this.frontEndSendRoutingAggregator.hasReceiveMore();
                    System.out.println("BLOOP CLUSTER");
                    System.out.println(publish);
                    if (publish == null) {
                        break;
                    }
                    publish.send(this.backEndSendRoutingAggregator);

                    if(!more) {
                        break;
                    }
                }
            }

            if (poller.pollin(1)) {
                while (true) {
                    publish = ZMsg.recvMsg(this.backEndSendRoutingAggregator);
                    more = this.backEndSendRoutingAggregator.hasReceiveMore();
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
                    publish = ZMsg.recvMsg(this.frontEndReceiveRoutingAggregator);
                    more = this.frontEndReceiveRoutingAggregator.hasReceiveMore();
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
                    publish = ZMsg.recvMsg(this.backEndReceiveRoutingAggregator);
                    more = this.backEndReceiveRoutingAggregator.hasReceiveMore();
                    System.out.println("BLOOP WORKER");
                    System.out.println(publish);
                    if (publish == null) {
                        break;
                    }
                    publish.send(this.frontEndReceiveRoutingAggregator);

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
        publish.send(this.backEndReceiveRoutingAggregator);
    }

    public void sendToProxy(ZMsg publish) {
        publish.send(this.frontEndSendRoutingAggregator);
    }
}
