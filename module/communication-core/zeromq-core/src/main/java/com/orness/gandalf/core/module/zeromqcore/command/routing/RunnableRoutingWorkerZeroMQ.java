package com.orness.gandalf.core.module.zeromqcore.command.routing;

import com.google.gson.Gson;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.orness.gandalf.core.module.zeromqcore.constant.Constant.COMMAND_COMMAND_READY;
import static com.orness.gandalf.core.module.zeromqcore.constant.Constant.COMMAND_COMMAND_RESULT;

public abstract class RunnableRoutingWorkerZeroMQ extends RoutingWorkerZeroMQ implements Runnable {

    protected Gson mapper;
    private Map<String, LinkedList<ZMsg>> serviceClassZMsgLinkedList;

    public RunnableRoutingWorkerZeroMQ() {
        super();
        mapper = new Gson();
        this.serviceClassZMsgLinkedList = new HashMap<>();
    }

    protected void initRunnable(String routingWorkerConnector, List<String> frontEndWorkerConnections, String backEndWorkerConnection) {
        this.init(routingWorkerConnector, frontEndWorkerConnections, backEndWorkerConnection);
    }

    @Override
    public void run() {

        // Initialize poll set
        ZMQ.Poller poller = context.createPoller(2);
        poller.register(this.frontEndRoutingWorker, ZMQ.Poller.POLLIN);
        poller.register(this.backEndRoutingWorker, ZMQ.Poller.POLLIN);

        ZMsg request;
        ZMsg response;
        boolean more = false;

        // Switch messages between sockets
        while (!Thread.currentThread().isInterrupted()) {
            poller.poll();

            //Client
            if (poller.pollin(0)) {
                while (true) {
                    // Receive broker message
                    request = ZMsg.recvMsg(this.frontEndRoutingWorker);
                    more = this.frontEndRoutingWorker.hasReceiveMore();
                    System.out.println(request);
                    System.out.println(more);

                    if (request == null) {
                        break; // Interrupted
                    }

                    this.processRequest(request);

                    if(!more) {
                        break;
                    }
                }
            }

            //Worker
            if (poller.pollin(1)) {
                while (true) {
                    // Receive command message
                    response = ZMsg.recvMsg(this.backEndRoutingWorker);
                    more = this.backEndRoutingWorker.hasReceiveMore();
                    System.out.println(response);
                    System.out.println(more);

                    if (response == null) {
                        break; // Interrupted
                    }

                    this.processResponse(response);

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

    private void processRequest(ZMsg request) {
        ZMsg requestBackup = request.duplicate();
        String client = requestBackup.popString();
        String uuid = requestBackup.popString();
        String connector = requestBackup.popString();
        String serviceClass = requestBackup.popString();

        request = this.addHeaderToWorker(request, serviceClass);
        this.getServiceClassZMsgLinkedList(serviceClass).add(request.duplicate());
        requestBackup.destroy();
        request.destroy();
    }

    private void processResponse(ZMsg response) {
        ZMsg responseBackup = response.duplicate();
        String serviceClass = responseBackup.popString();
        String commandType = responseBackup.popString();
        responseBackup.destroy();
        if(commandType.equals(COMMAND_COMMAND_READY)) {
            if(!this.getServiceClassZMsgLinkedList(serviceClass).isEmpty()) {
                this.sendToWorker(this.getServiceClassZMsgLinkedList(serviceClass).poll());
            }
        }
        else if(commandType.equals(COMMAND_COMMAND_RESULT)) {
            response = this.removeHeaderFromWorker(response);
            this.sendToBroker(response);
        }
        else {
            System.out.println("E: invalid message");
        }
        response.destroy();
    }

    private LinkedList<ZMsg> getServiceClassZMsgLinkedList(String serviceClass) {
        if(!this.serviceClassZMsgLinkedList.containsKey(serviceClass)) {
            this.serviceClassZMsgLinkedList.put(serviceClass, new LinkedList<>());
        }
        return this.serviceClassZMsgLinkedList.get(serviceClass);
    }

    private ZMsg addHeaderToWorker(ZMsg request, String serviceClass) {
        request.addFirst(serviceClass);
        return request;
    }

    private ZMsg removeHeaderFromWorker(ZMsg response) {
        response.removeFirst();
        return response;
    }

    private void sendToWorker(ZMsg request) {
        //Command
        System.out.println("SEND REQ");
        System.out.println(request);
        request.send(this.backEndRoutingWorker);
    }

    private void sendToBroker(ZMsg response) {
        //Worker
        System.out.println("SEND REP");
        System.out.println(response);
        response.send(this.frontEndRoutingWorker);
    }
}
