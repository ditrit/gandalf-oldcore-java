package com.ditrit.gandalf.gandalfjava.core.zeromqcore.command.aggregator;

import com.ditrit.gandalf.gandalfjava.core.zeromqcore.constant.Constant;
import com.google.gson.Gson;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class RunnableAggregatorCommandZeroMQ extends AggregatorCommandZeroMQ implements Runnable {

    protected Gson mapper;
    private Map<String, LinkedList<ZMsg>> connectorZMsgLinkedList;

    public RunnableAggregatorCommandZeroMQ() {
        super();
        mapper = new Gson();
        this.connectorZMsgLinkedList = new HashMap<>();
    }

    protected void initRunnable(String identity, List<String> frontEndSendAggregatorConnections, List<String> frontEndReceiveAggregatorConnections, String backEndSendAggregatorConnection, String backEndReceiveAggregatorConnection) {
        this.init(identity, frontEndSendAggregatorConnections, frontEndReceiveAggregatorConnections, backEndSendAggregatorConnection, backEndReceiveAggregatorConnection);
    }

    @Override
    public void run() {

        ZMQ.Poller poller = context.createPoller(4);
        poller.register(this.frontEndSendRoutingAggregator, ZMQ.Poller.POLLIN);
        poller.register(this.frontEndReceiveRoutingAggregator, ZMQ.Poller.POLLIN);
        poller.register(this.backEndSendRoutingAggregator, ZMQ.Poller.POLLIN);
        poller.register(this.backEndReceiveRoutingAggregator, ZMQ.Poller.POLLIN);

        ZMsg brokerMessage;
        ZMsg connectorMessage;
        boolean more = false;

        while (!Thread.currentThread().isInterrupted()) {
            poller.poll();

            if (poller.pollin(0)) {
                while (true) {
                    brokerMessage = ZMsg.recvMsg(this.frontEndSendRoutingAggregator);
                    more = this.frontEndSendRoutingAggregator.hasReceiveMore();
                    System.out.println("RESULT COMMAND CLUSTER");
                    System.out.println(brokerMessage);
                    System.out.println(more);

                    if (brokerMessage == null) {
                        break;
                    }

                    this.processBrokerSendMessage(brokerMessage);

                    if(!more) {
                        break;
                    }
                }
            }

            if (poller.pollin(1)) {
                while (true) {
                    brokerMessage = ZMsg.recvMsg(this.frontEndReceiveRoutingAggregator);
                    more = this.frontEndReceiveRoutingAggregator.hasReceiveMore();
                    System.out.println("COMMAND CLUSTER");
                    System.out.println(brokerMessage);
                    System.out.println(more);

                    if (brokerMessage == null) {
                        break;
                    }

                    this.processBrokerReceiveMessage(brokerMessage);

                    if(!more) {
                        break;
                    }
                }
            }

            if (poller.pollin(2)) {
                while (true) {
                    connectorMessage = ZMsg.recvMsg(this.backEndSendRoutingAggregator);
                    more = this.backEndSendRoutingAggregator.hasReceiveMore();
                    System.out.println("COMMAND SEND CONNECTOR");
                    System.out.println(connectorMessage);
                    System.out.println(more);

                    if (connectorMessage == null) {
                        break;
                    }

                    this.processConnectorSendMessage(connectorMessage);

                    if(!more) {
                        break;
                    }
                }
            }

            if (poller.pollin(3)) {
                while (true) {
                    connectorMessage = ZMsg.recvMsg(this.backEndReceiveRoutingAggregator);
                    more = this.backEndReceiveRoutingAggregator.hasReceiveMore();
                    System.out.println("COMMAND RECEIVE CONNECTOR");
                    System.out.println(connectorMessage);
                    System.out.println(more);

                    if (connectorMessage == null) {
                        break;
                    }

                    this.processConnectorReceiveMessage(connectorMessage);

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

    private void processBrokerSendMessage(ZMsg brokerMessage) {
        Object[] brokerMessageBackup = brokerMessage.duplicate().toArray();
        String sourceConnector = brokerMessageBackup[1].toString();
        brokerMessage = this.updateHeaderBrokerSendMessage(brokerMessage, sourceConnector);
        this.sendResultToConnector(brokerMessage.duplicate());

        brokerMessage.destroy();
    }

    private ZMsg updateHeaderBrokerSendMessage(ZMsg brokerMessage, String sourceConnector) {
        brokerMessage.addFirst(sourceConnector);
        return brokerMessage;
    }

    private void processBrokerReceiveMessage(ZMsg brokerMessage) {
        Object[] brokerMessageBackup = brokerMessage.duplicate().toArray();
        String targetConnector = brokerMessageBackup[4].toString();
        brokerMessage = this.updateHeaderBrokerReceiveMessage(brokerMessage, targetConnector);
        this.getConnectorZMsgLinkedList(targetConnector).add(brokerMessage.duplicate());

        brokerMessage.destroy();
    }

    private ZMsg updateHeaderBrokerReceiveMessage(ZMsg brokerMessage, String targetConnector) {
        brokerMessage.addFirst(targetConnector);
        return brokerMessage;
    }

    private void processConnectorSendMessage(ZMsg connectorMessage) {
        String sourceConnector = connectorMessage.popString(); //SOURCE CONNECTOR (DEALER)
        connectorMessage = this.updateIdentityConnectorMessage(connectorMessage, sourceConnector);
        this.sendComandToBroker(connectorMessage);

        connectorMessage.destroy();
    }

    private ZMsg updateIdentityConnectorMessage(ZMsg connectorMessage, String sourceConnector) {
        String uuid = connectorMessage.popString();
        connectorMessage.addFirst(sourceConnector);
        connectorMessage.addFirst(uuid);
        return connectorMessage;
    }

    private void processConnectorReceiveMessage(ZMsg connectorMessage) {
        Object[] connectorMessageBackup = connectorMessage.duplicate().toArray();
        String connector = connectorMessageBackup[0].toString(); //(READY)
        String commandType = connectorMessageBackup[1].toString(); //(READY)
        if(commandType.equals(Constant.COMMAND_READY)) {
            if(!this.getConnectorZMsgLinkedList(connector).isEmpty()) {
                this.sendCommandToConnector(this.getConnectorZMsgLinkedList(connector).poll());
            }
        }
        else {
            System.out.println("ROUTING RESULT");
            System.out.println(connectorMessage);
            connectorMessage = this.updateHeaderConnectorReceiveMessage(connectorMessage);
            this.sendResultToBroker(connectorMessage);
        }

        connectorMessage.destroy();
    }

    private ZMsg updateHeaderConnectorReceiveMessage(ZMsg response) {
        response.removeFirst();
        return response;
    }

    private LinkedList<ZMsg> getConnectorZMsgLinkedList(String connector) {
        if(!this.connectorZMsgLinkedList.containsKey(connector)) {
            this.connectorZMsgLinkedList.put(connector, new LinkedList<>());
        }
        return this.connectorZMsgLinkedList.get(connector);
    }

    private void sendCommandToConnector(ZMsg command) {
        System.out.println("SEND CMD WORKER");
        System.out.println(command);
        command.send(this.backEndReceiveRoutingAggregator);
    }

    private void sendResultToConnector(ZMsg result) {
        System.out.println("SEND RESULT BROKER");
        System.out.println(result);
        result.send(this.backEndSendRoutingAggregator);
    }

    private void sendResultToBroker(ZMsg result) {
        System.out.println("SEND RESULT BROKER");
        System.out.println(result);
        result.send(this.frontEndReceiveRoutingAggregator);
    }

    private void sendComandToBroker(ZMsg command) {
        System.out.println("SEND CMD BROKER");
        System.out.println(command);
        command.send(this.frontEndSendRoutingAggregator);
    }
}
