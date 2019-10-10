package com.ditrit.gandalf.core.zeromqcore.command.routing;

import com.ditrit.gandalf.core.zeromqcore.constant.Constant;
import com.google.gson.Gson;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.ditrit.gandalf.core.zeromqcore.constant.Constant.GANDALF_SERVICECLASS;

public abstract class RunnableAggregatorWorkerZeroMQ extends AggregatorWorkerZeroMQ implements Runnable {

    protected Gson mapper;
    private Map<String, LinkedList<ZMsg>> serviceClassZMsgLinkedList;

    public RunnableAggregatorWorkerZeroMQ() {
        super();
        mapper = new Gson();
        this.serviceClassZMsgLinkedList = new HashMap<>();
    }

    protected void initRunnable(String routingWorkerConnector, List<String> frontEndSendWorkerConnections, List<String> frontEndReceiveWorkerConnections, String backEndSendWorkerConnection, String backEndReceiveWorkerConnection) {
        this.init(routingWorkerConnector, frontEndSendWorkerConnections, frontEndReceiveWorkerConnections, backEndSendWorkerConnection, backEndReceiveWorkerConnection);
    }

    @Override
    public void run() {

        // Initialize poll set
        ZMQ.Poller poller = context.createPoller(4);
        poller.register(this.frontEndSendRoutingWorker, ZMQ.Poller.POLLIN);
        poller.register(this.frontEndReceiveRoutingWorker, ZMQ.Poller.POLLIN);
        poller.register(this.backEndSendRoutingWorker, ZMQ.Poller.POLLIN);
        poller.register(this.backEndReceiveRoutingWorker, ZMQ.Poller.POLLIN);

        ZMsg brokerMessage;
        ZMsg workerMessage;
        boolean more = false;

        // Switch messages between sockets
        while (!Thread.currentThread().isInterrupted()) {
            poller.poll();

            //Client
            if (poller.pollin(0)) {
                while (true) {
                    // Receive broker message
                    brokerMessage = ZMsg.recvMsg(this.frontEndSendRoutingWorker);
                    more = this.frontEndSendRoutingWorker.hasReceiveMore();
                    System.out.println("RESULT COMMAND CLUSTER");
                    System.out.println(brokerMessage);
                    System.out.println(more);

                    if (brokerMessage == null) {
                        break; // Interrupted
                    }

                    this.processBrokerSendMessage(brokerMessage);

                    if(!more) {
                        break;
                    }
                }
            }

            //Client
            if (poller.pollin(1)) {
                while (true) {
                    // Receive broker message
                    brokerMessage = ZMsg.recvMsg(this.frontEndReceiveRoutingWorker);
                    more = this.frontEndReceiveRoutingWorker.hasReceiveMore();
                    System.out.println("COMMAND CLUSTER");
                    System.out.println(brokerMessage);
                    System.out.println(more);

                    if (brokerMessage == null) {
                        break; // Interrupted
                    }

                    this.processBrokerReceiveMessage(brokerMessage);

                    if(!more) {
                        break;
                    }
                }
            }

            //Worker
            if (poller.pollin(2)) {
                while (true) {
                    // Receive command message
                    workerMessage = ZMsg.recvMsg(this.backEndSendRoutingWorker);
                    more = this.backEndSendRoutingWorker.hasReceiveMore();
                    System.out.println("COMMAND SEND WORKER");
                    System.out.println(workerMessage);
                    System.out.println(more);

                    if (workerMessage == null) {
                        break; // Interrupted
                    }

                    this.processWorkerSendMessage(workerMessage);

                    if(!more) {
                        break;
                    }
                }
            }

            //Worker
            if (poller.pollin(3)) {
                while (true) {
                    // Receive command message
                    workerMessage = ZMsg.recvMsg(this.backEndReceiveRoutingWorker);
                    more = this.backEndReceiveRoutingWorker.hasReceiveMore();
                    System.out.println("COMMAND RECEIVE WORKER");
                    System.out.println(workerMessage);
                    System.out.println(more);

                    if (workerMessage == null) {
                        break; // Interrupted
                    }

                    this.processWorkerReceiveMessage(workerMessage);

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

    private void processBrokerSendMessage(ZMsg brokerMessage) {
        ZMsg brokerMessageBackup = brokerMessage.duplicate();
        if(brokerMessage.size() == 10) {
            String uuid = brokerMessageBackup.popString();
            String sourceConnector = brokerMessageBackup.popString();
            String sourceServiceClass = brokerMessageBackup.popString();
            String targetConnector = brokerMessageBackup.popString();
            String targetServiceClass = brokerMessageBackup.popString();
            if(GANDALF_SERVICECLASS.contains(targetServiceClass)) {
                brokerMessage = this.updateHeaderBrokerMessage(brokerMessage, targetServiceClass);
                this.sendResultToWorker(brokerMessage.duplicate());
            }
            else {
                System.out.println("E: invalid serviceClass");
            }
        }
        else {
            System.out.println("E: invalid message");
        }
        brokerMessageBackup.destroy();
        brokerMessage.destroy();
    }

    private void processBrokerReceiveMessage(ZMsg brokerMessage) {
        ZMsg brokerMessageBackup = brokerMessage.duplicate();
        if(brokerMessage.size() == 9) {
            String uuid = brokerMessageBackup.popString();
            String sourceConnector = brokerMessageBackup.popString();
            String sourceServiceClass = brokerMessageBackup.popString();
            String targetConnector = brokerMessageBackup.popString();
            String targetServiceClass = brokerMessageBackup.popString();
            if(GANDALF_SERVICECLASS.contains(targetServiceClass)) {
                brokerMessage = this.updateHeaderBrokerMessage(brokerMessage, targetServiceClass);
                this.getServiceClassZMsgLinkedList(targetServiceClass).add(brokerMessage.duplicate());
            }
            else {
                System.out.println("E: invalid serviceClass");
            }
        }
        else {
            System.out.println("E: invalid message");
        }
        brokerMessageBackup.destroy();
        brokerMessage.destroy();
    }

    private ZMsg updateHeaderBrokerMessage(ZMsg brokerMessage, String targetServiceClass) {
        //brokerMessage.removeFirst();
        brokerMessage.addFirst(targetServiceClass);
        return brokerMessage;
    }

    private void processWorkerSendMessage(ZMsg workerMessage) {
        if(workerMessage.size() == 7) {
            String serviceClass = workerMessage.popString();
            if(GANDALF_SERVICECLASS.contains(serviceClass)) {
                workerMessage = this.updateIdentityWorkerMessage(workerMessage, serviceClass);
                this.sendComandToBroker(workerMessage);
            }
            else {
                System.out.println("E: invalid serviceClass");
            }
        }
        else {
            System.out.println("E: invalid message");
        }
        workerMessage.destroy();
    }

    private ZMsg updateIdentityWorkerMessage(ZMsg workerMessage, String serviceClass) {
        //String serviceClass = workerMessage.popString();
        String uuid = workerMessage.popString();
        workerMessage.addFirst(serviceClass);
        workerMessage.addFirst(this.routingWorkerConnector);
        workerMessage.addFirst(uuid);
        return workerMessage;
    }

    private void processWorkerReceiveMessage(ZMsg workerMessage) {
        ZMsg workerMessageBackup = workerMessage.duplicate();
        String serviceClass = workerMessageBackup.popString();
        String commandType = workerMessageBackup.popString();
        if(commandType.equals(Constant.COMMAND_READY)) {
            if(GANDALF_SERVICECLASS.contains(serviceClass)) {
                if(!this.getServiceClassZMsgLinkedList(serviceClass).isEmpty()) {
                    this.sendCommandToWorker(this.getServiceClassZMsgLinkedList(serviceClass).poll());
                }
            }
            else {
                System.out.println("E: invalid serviceClass");
            }
        }
        else if(workerMessage.size() == 10) {
            System.out.println("ROUTING RESULT");
            System.out.println(workerMessage);
            workerMessage = this.updateHeaderWorkerReceiveMessage(workerMessage);
            this.sendResultToBroker(workerMessage);
        }
        else {
            System.out.println("E: invalid message");
        }
        workerMessageBackup.destroy();
        workerMessage.destroy();
    }

    private ZMsg updateHeaderWorkerReceiveMessage(ZMsg response) {
        response.removeFirst();
        return response;
    }

    private LinkedList<ZMsg> getServiceClassZMsgLinkedList(String serviceClass) {
        if(!this.serviceClassZMsgLinkedList.containsKey(serviceClass)) {
            this.serviceClassZMsgLinkedList.put(serviceClass, new LinkedList<>());
        }
        return this.serviceClassZMsgLinkedList.get(serviceClass);
    }

    private void sendCommandToWorker(ZMsg command) {
        //Command
        System.out.println("SEND CMD WORKER");
        System.out.println(command);
        command.send(this.backEndReceiveRoutingWorker);
    }

    private void sendResultToWorker(ZMsg result) {
        //Command
        System.out.println("SEND RESULT BROKER");
        System.out.println(result);
        result.send(this.backEndSendRoutingWorker);
    }

    private void sendResultToBroker(ZMsg result) {
        //Worker
        System.out.println("SEND RESULT BROKER");
        System.out.println(result);
        result.send(this.frontEndReceiveRoutingWorker);
    }

    private void sendComandToBroker(ZMsg command) {
        //Worker
        System.out.println("SEND CMD BROKER");
        System.out.println(command);
        command.send(this.frontEndSendRoutingWorker);
    }
}
