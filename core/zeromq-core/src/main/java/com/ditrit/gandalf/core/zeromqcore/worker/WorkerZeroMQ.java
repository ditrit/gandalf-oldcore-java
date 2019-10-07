package com.ditrit.gandalf.core.zeromqcore.worker;

import com.ditrit.gandalf.core.zeromqcore.constant.Constant;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;


public abstract class WorkerZeroMQ {

    protected ZContext context;
    //TODO MULTIPLE
    protected ZMQ.Socket frontEndWorker;
    protected String frontEndWorkerConnection;
    protected ZMQ.Socket frontEndSubscriberWorker;
    protected String frontEndSubscriberWorkerConnection;
    protected String workerServiceClass;

    protected void init(String workerServiceClass, String frontEndWorkerConnection, String frontEndSubscriberWorkerConnection) {
        this.context = new ZContext();
        this.workerServiceClass = workerServiceClass;
        //this.workerServiceClassType = workerServiceClassType;

        //Command Worker
        this.frontEndWorker = this.context.createSocket(SocketType.DEALER);
        this.frontEndWorker.setIdentity(this.workerServiceClass.getBytes(ZMQ.CHARSET));
        this.frontEndWorkerConnection = frontEndWorkerConnection;
        System.out.println("WorkerZeroMQ connect to: " + this.frontEndWorkerConnection);
        this.frontEndWorker.connect(this.frontEndWorkerConnection);


        //Event Worker
        this.frontEndSubscriberWorker = this.context.createSocket(SocketType.SUB);
        this.frontEndSubscriberWorker.setIdentity(this.workerServiceClass.getBytes(ZMQ.CHARSET));
        this.frontEndSubscriberWorkerConnection = frontEndSubscriberWorkerConnection;
        System.out.println("WorkerZeroMQ connect to: " + this.frontEndSubscriberWorkerConnection);
        this.frontEndSubscriberWorker.connect(this.frontEndSubscriberWorkerConnection);

    }

    protected void close() {
        this.frontEndWorker.close();
        this.frontEndSubscriberWorker.close();
        this.context.close();
    }

    protected void sendReadyCommand() {
        ZMsg ready = new ZMsg();
        ready.add(Constant.COMMAND_COMMAND_READY);
        ready.send(this.frontEndWorker);
        ready.destroy();
    }

    protected void sendResultCommand(ZMsg request, String result) {
        request.addFirst(Constant.COMMAND_COMMAND_RESULT);
        request.addLast(result);
        request.send(this.frontEndWorker);
    }

}
