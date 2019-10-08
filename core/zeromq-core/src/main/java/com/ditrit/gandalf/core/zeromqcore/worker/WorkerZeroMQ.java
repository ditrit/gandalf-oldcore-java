package com.ditrit.gandalf.core.zeromqcore.worker;

import com.ditrit.gandalf.core.zeromqcore.constant.Constant;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

import java.sql.Timestamp;


public abstract class WorkerZeroMQ {

    protected ZContext context;
    //TODO MULTIPLE
    protected ZMQ.Socket frontEndSendWorker;
    protected String frontEndSendWorkerConnection;
    protected ZMQ.Socket frontEndReceiveWorker;
    protected String frontEndReceiveWorkerConnection;
    protected ZMQ.Socket frontEndPublisher;
    protected String frontEndPublisherConnection;
    protected ZMQ.Socket frontEndSubscriberWorker;
    protected String frontEndSubscriberWorkerConnection;
    protected String workerServiceClass;

    protected void init(String workerServiceClass, String frontEndSendWorkerConnection, String frontEndReceiveWorkerConnection, String frontEndPublisherWorkerConnection, String frontEndSubscriberWorkerConnection) {
        this.context = new ZContext();
        this.workerServiceClass = workerServiceClass;
        //this.workerServiceClassType = workerServiceClassType;

        //Command Send Worker
        this.frontEndSendWorker = this.context.createSocket(SocketType.DEALER);
        this.frontEndSendWorker.setIdentity(this.workerServiceClass.getBytes(ZMQ.CHARSET));
        this.frontEndSendWorkerConnection = frontEndSendWorkerConnection;
        System.out.println("WorkerZeroMQ connect to: " + this.frontEndSendWorkerConnection);
        this.frontEndSendWorker.connect(this.frontEndSendWorkerConnection);

        //Command Receive Worker
        this.frontEndReceiveWorker = this.context.createSocket(SocketType.DEALER);
        this.frontEndReceiveWorker.setIdentity(this.workerServiceClass.getBytes(ZMQ.CHARSET));
        this.frontEndReceiveWorkerConnection = frontEndReceiveWorkerConnection;
        System.out.println("WorkerZeroMQ connect to: " + this.frontEndReceiveWorkerConnection);
        this.frontEndReceiveWorker.connect(this.frontEndReceiveWorkerConnection);


        //Event Publish Worker
        this.frontEndPublisher = this.context.createSocket(SocketType.PUB);
        this.frontEndPublisher.setIdentity(this.workerServiceClass.getBytes(ZMQ.CHARSET));
        this.frontEndPublisherConnection = frontEndPublisherWorkerConnection;
        System.out.println("WorkerZeroMQ connect to: " + this.frontEndPublisherConnection);
        this.frontEndPublisher.connect(this.frontEndPublisherConnection);

        //Event Subscribe Worker
        this.frontEndSubscriberWorker = this.context.createSocket(SocketType.SUB);
        this.frontEndSubscriberWorker.setIdentity(this.workerServiceClass.getBytes(ZMQ.CHARSET));
        this.frontEndSubscriberWorkerConnection = frontEndSubscriberWorkerConnection;
        System.out.println("WorkerZeroMQ connect to: " + this.frontEndSubscriberWorkerConnection);
        this.frontEndSubscriberWorker.connect(this.frontEndSubscriberWorkerConnection);

    }

    protected void close() {
        this.frontEndSendWorker.close();
        this.frontEndReceiveWorker.close();
        this.frontEndSubscriberWorker.close();
        this.context.close();
    }

    protected void sendReadyCommand() {
        ZMsg ready = new ZMsg();
        ready.add(Constant.COMMAND_READY);
        ready.send(this.frontEndReceiveWorker);
        ready.destroy();
    }

    protected void sendResultCommand(ZMsg request, String result) {
        request.addLast(result);
        request.send(this.frontEndReceiveWorker);
    }

    public void sendEvent(String topic, String event, String timeout, String payload) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        this.frontEndPublisher.sendMore(topic);
        this.frontEndPublisher.sendMore(event);
        this.frontEndPublisher.sendMore(timeout);
        this.frontEndPublisher.sendMore(timestamp.toString());
        this.frontEndPublisher.send(payload);
    }

    protected void sendCommand(String uuid, String connector, String serviceClass, String command, String timeout, String payload) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        this.frontEndSendWorker.sendMore(uuid);
        //this.frontEndSendWorker.sendMore(this.workerServiceClass);
        this.frontEndSendWorker.sendMore(connector);
        this.frontEndSendWorker.sendMore(serviceClass);
        this.frontEndSendWorker.sendMore(command);
        this.frontEndSendWorker.sendMore(timeout);
        this.frontEndSendWorker.sendMore(timestamp.toString());
        this.frontEndSendWorker.send(payload);
    }

}
