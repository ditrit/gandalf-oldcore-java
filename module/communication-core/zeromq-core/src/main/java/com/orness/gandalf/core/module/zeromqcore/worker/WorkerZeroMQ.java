package com.orness.gandalf.core.module.zeromqcore.worker;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

import static com.orness.gandalf.core.module.zeromqcore.constant.Constant.COMMAND_COMMAND_READY;
import static com.orness.gandalf.core.module.zeromqcore.constant.Constant.COMMAND_COMMAND_RESULT;


public abstract class WorkerZeroMQ {

    protected ZContext context;
    //TODO MULTIPLE
    protected ZMQ.Socket frontEndWorker;
    protected String frontEndWorkerConnections; //IPC
    protected ZMQ.Socket frontEndSubscriberWorker;
    protected String frontEndSubscriberWorkerConnections; //IPC
    protected String workerServiceClass;

    protected void init(String workerServiceClass, String frontEndWorkerConnections, String frontEndSubscriberWorkerConnections) {
        this.context = new ZContext();
        this.workerServiceClass = workerServiceClass;
        //this.workerServiceClassType = workerServiceClassType;

        //Worker
        this.frontEndWorker = this.context.createSocket(SocketType.DEALER);
        this.frontEndWorker.setIdentity(this.workerServiceClass.getBytes(ZMQ.CHARSET));
        this.frontEndWorkerConnections = frontEndWorkerConnections;
        System.out.println("WorkerZeroMQ connect to: " + this.frontEndWorkerConnections);
        this.frontEndWorker.connect(this.frontEndWorkerConnections);

        //Worker
        this.frontEndSubscriberWorker = this.context.createSocket(SocketType.SUB);
        this.frontEndSubscriberWorker.setIdentity(this.workerServiceClass.getBytes(ZMQ.CHARSET));
        this.frontEndSubscriberWorkerConnections = frontEndSubscriberWorkerConnections;
        System.out.println("WorkerZeroMQ connect to: " + this.frontEndSubscriberWorkerConnections);
        this.frontEndSubscriberWorker.connect(this.frontEndSubscriberWorkerConnections);

    }

    protected void close() {
        this.frontEndWorker.close();
        this.frontEndSubscriberWorker.close();
        this.context.close();
    }

    protected void sendReadyCommand() {
        ZMsg ready = new ZMsg();
        ready.add(COMMAND_COMMAND_READY);
        ready.send(this.frontEndWorker);
        ready.destroy();
    }

    protected void sendResultCommand(ZMsg request, String result) {
        request.addFirst(COMMAND_COMMAND_RESULT);
        request.addLast(result);
        request.send(this.frontEndWorker);
    }

}
