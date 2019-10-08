package com.ditrit.gandalf.core.zeromqcore.command.routing;

import com.ditrit.gandalf.core.zeromqcore.constant.Constant;
import com.google.gson.Gson;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.ditrit.gandalf.core.zeromqcore.constant.Constant.COMMAND_CLIENT_SEND;

public abstract class RunnableRoutingWorkerZeroMQ extends RoutingWorkerZeroMQ implements Runnable {

    protected Gson mapper;
    private Map<String, LinkedList<ZMsg>> serviceClassZMsgLinkedList;

    public RunnableRoutingWorkerZeroMQ() {
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
        ZMQ.Poller poller = context.createPoller(3);
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
                    brokerMessage = ZMsg.recvMsg(this.frontEndReceiveRoutingWorker);
                    more = this.frontEndReceiveRoutingWorker.hasReceiveMore();
                    System.out.println("COMMAND CLUSTER");
                    System.out.println(brokerMessage);
                    System.out.println(more);

                    if (brokerMessage == null) {
                        break; // Interrupted
                    }

                    this.processBrokerMessage(brokerMessage);

                    if(!more) {
                        break;
                    }
                }
            }

            //Worker
            if (poller.pollin(1)) {
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
            if (poller.pollin(2)) {
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

    private void processBrokerMessage(ZMsg brokerMessage) {
        ZMsg brokerMessageBackup = brokerMessage.duplicate();
        if(brokerMessage.size() == 8) {
            String uuid = brokerMessageBackup.popString();
            String client = brokerMessageBackup.popString();
            String connector = brokerMessageBackup.popString();
            String serviceClass = brokerMessageBackup.popString();

            brokerMessage = this.updateHeaderBrokerMessage(brokerMessage, serviceClass);
            this.getServiceClassZMsgLinkedList(serviceClass).add(brokerMessage.duplicate());
        }
        else {
            System.out.println("E: invalid message");
        }
        brokerMessageBackup.destroy();
        brokerMessage.destroy();
    }

    private ZMsg updateHeaderBrokerMessage(ZMsg brokerMessage, String serviceClass) {
        //brokerMessage.removeFirst();
        brokerMessage.addFirst(serviceClass);
        return brokerMessage;
    }

    private void processWorkerSendMessage(ZMsg workerMessage) {
        workerMessage = this.updateIdentityWorkerMessage(workerMessage);
        if(workerMessage.size() == 7) {
            this.sendComandToBroker(workerMessage);
        }
        else {
            System.out.println("E: invalid message");
        }
        workerMessage.destroy();
    }

    private ZMsg updateIdentityWorkerMessage(ZMsg workerMessage) {
        String serviceClass = workerMessage.popString();
        String uuid = workerMessage.popString();
        workerMessage.addFirst(serviceClass);
        workerMessage.addFirst(this.routingWorkerConnector);
        workerMessage.addFirst(uuid);
        return workerMessage;
    }

    private ZMsg updateHeaderWorkerReceiveMessage(ZMsg response) {
        response.removeFirst();
        return response;
    }

    private void processWorkerReceiveMessage(ZMsg workerMessage) {
        ZMsg workerMessageBackup = workerMessage.duplicate();
        String serviceClass = workerMessageBackup.popString();
        String commandType = workerMessageBackup.popString();
        if(commandType.equals(Constant.COMMAND_READY)) {
            if(!this.getServiceClassZMsgLinkedList(serviceClass).isEmpty()) {
                this.sendToWorker(this.getServiceClassZMsgLinkedList(serviceClass).poll());
            }
        }
        else if(workerMessage.size() == 9) {
            System.out.println("ROUTING RESULT");
            System.out.println(workerMessage);
            workerMessage = this.updateHeaderWorkerReceiveMessage(workerMessage);
            this.sendResponseToBroker(workerMessage);
        }
        else {
            System.out.println("E: invalid message");
        }
        workerMessageBackup.destroy();
        workerMessage.destroy();
    }

    private LinkedList<ZMsg> getServiceClassZMsgLinkedList(String serviceClass) {
        if(!this.serviceClassZMsgLinkedList.containsKey(serviceClass)) {
            this.serviceClassZMsgLinkedList.put(serviceClass, new LinkedList<>());
        }
        return this.serviceClassZMsgLinkedList.get(serviceClass);
    }

    private void sendToWorker(ZMsg request) {
        //Command
        System.out.println("SEND REQ");
        System.out.println(request);
        request.send(this.backEndReceiveRoutingWorker);
    }

    private void sendResponseToBroker(ZMsg response) {
        //Worker
        System.out.println("SEND REP");
        System.out.println(response);
        response.send(this.frontEndReceiveRoutingWorker);
    }

    private void sendComandToBroker(ZMsg command) {
        //Worker
        System.out.println("SEND CMD");
        System.out.println(command);
        command.send(this.frontEndSendRoutingWorker);
    }
}
