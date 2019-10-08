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
    protected ZMQ.Socket workerCommandFrontEndSend;
    protected String workerCommandFrontEndSendConnection;
    protected ZMQ.Socket workerCommandFrontEndReceive;
    protected String workerCommandFrontEndReceiveConnection;
    protected ZMQ.Socket workerEventFrontEndSend;
    protected String workerEventFrontEndSendConnection;
    protected ZMQ.Socket workerEventFrontEndReceive;
    protected String workerEventFrontEndReceiveConnection;
    protected String workerServiceClass;

    protected void init(String workerServiceClass, String workerCommandFrontEndSendConnection, String workerCommandFrontEndReceiveConnection, String workerEventFrontEndSendConnection, String workerEventFrontEndReceiveConnection) {
        this.context = new ZContext();
        this.workerServiceClass = workerServiceClass;
        //this.workerServiceClassType = workerServiceClassType;

        //Command Send Worker
        this.workerCommandFrontEndSend = this.context.createSocket(SocketType.DEALER);
        this.workerCommandFrontEndSend.setIdentity(this.workerServiceClass.getBytes(ZMQ.CHARSET));
        this.workerCommandFrontEndSendConnection = workerCommandFrontEndSendConnection;
        System.out.println("WorkerZeroMQ connect to: " + this.workerCommandFrontEndSendConnection);
        this.workerCommandFrontEndSend.connect(this.workerCommandFrontEndSendConnection);

        //Command Receive Worker
        this.workerCommandFrontEndReceive = this.context.createSocket(SocketType.DEALER);
        this.workerCommandFrontEndReceive.setIdentity(this.workerServiceClass.getBytes(ZMQ.CHARSET));
        this.workerCommandFrontEndReceiveConnection = workerCommandFrontEndReceiveConnection;
        System.out.println("WorkerZeroMQ connect to: " + this.workerCommandFrontEndReceiveConnection);
        this.workerCommandFrontEndReceive.connect(this.workerCommandFrontEndReceiveConnection);


        //Event Send Worker
        this.workerEventFrontEndSend = this.context.createSocket(SocketType.PUB);
        this.workerEventFrontEndSend.setIdentity(this.workerServiceClass.getBytes(ZMQ.CHARSET));
        this.workerEventFrontEndSendConnection = workerEventFrontEndSendConnection;
        System.out.println("WorkerZeroMQ connect to: " + this.workerEventFrontEndSendConnection);
        this.workerEventFrontEndSend.connect(this.workerEventFrontEndSendConnection);

        //Event Receive Worker
        this.workerEventFrontEndReceive = this.context.createSocket(SocketType.SUB);
        this.workerEventFrontEndReceive.setIdentity(this.workerServiceClass.getBytes(ZMQ.CHARSET));
        this.workerEventFrontEndReceiveConnection = workerEventFrontEndReceiveConnection;
        System.out.println("WorkerZeroMQ connect to: " + this.workerEventFrontEndReceiveConnection);
        this.workerEventFrontEndReceive.connect(this.workerEventFrontEndReceiveConnection);

    }

    protected void close() {
        this.workerCommandFrontEndSend.close();
        this.workerCommandFrontEndReceive.close();
        this.workerEventFrontEndSend.close();
        this.workerEventFrontEndReceive.close();
        this.context.close();
    }

    protected void sendReadyCommand() {
        ZMsg ready = new ZMsg();
        ready.add(Constant.COMMAND_READY);
        ready.send(this.workerCommandFrontEndReceive);
        ready.destroy();
    }

    protected void sendResultCommand(ZMsg request, String result) {
        request.addLast(result);
        request.send(this.workerCommandFrontEndReceive);
    }

    public void sendEvent(String topic, String event, String timeout, String payload) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        this.workerEventFrontEndSend.sendMore(topic);
        this.workerEventFrontEndSend.sendMore(event);
        this.workerEventFrontEndSend.sendMore(timeout);
        this.workerEventFrontEndSend.sendMore(timestamp.toString());
        this.workerEventFrontEndSend.send(payload);
    }

    protected void sendCommand(String uuid, String connector, String serviceClass, String command, String timeout, String payload) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        this.workerCommandFrontEndSend.sendMore(uuid);
        //this.frontEndSendWorker.sendMore(this.workerServiceClass);
        this.workerCommandFrontEndSend.sendMore(connector);
        this.workerCommandFrontEndSend.sendMore(serviceClass);
        this.workerCommandFrontEndSend.sendMore(command);
        this.workerCommandFrontEndSend.sendMore(timeout);
        this.workerCommandFrontEndSend.sendMore(timestamp.toString());
        this.workerCommandFrontEndSend.send(payload);
    }

}
