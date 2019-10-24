package com.ditrit.gandalf.core.zeromqcore.worker;

import com.ditrit.gandalf.core.zeromqcore.constant.Constant;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

import java.sql.Timestamp;
import java.util.List;


public abstract class WorkerZeroMQ {

    protected ZContext context;
    protected ZMQ.Socket workerCommandFrontEndReceive;
    protected String workerCommandFrontEndReceiveConnection;
    protected ZMQ.Socket workerEventFrontEndReceive;
    protected String workerEventFrontEndReceiveConnection;
    protected String identity;

    protected void init(String identity, String workerCommandFrontEndReceiveConnection, String workerEventFrontEndReceiveConnection) {
        this.context = new ZContext();
        this.identity = identity;
        //this.workerServiceClassType = workerServiceClassType;

        //Command Receive Worker
        this.workerCommandFrontEndReceive = this.context.createSocket(SocketType.DEALER);
        this.workerCommandFrontEndReceive.setIdentity(this.identity.getBytes(ZMQ.CHARSET));
        this.workerCommandFrontEndReceiveConnection = workerCommandFrontEndReceiveConnection;
        System.out.println("WorkerZeroMQ connect to: " + this.workerCommandFrontEndReceiveConnection);
        this.workerCommandFrontEndReceive.connect(this.workerCommandFrontEndReceiveConnection);

        //Event Receive Worker
        this.workerEventFrontEndReceive = this.context.createSocket(SocketType.SUB);
        this.workerEventFrontEndReceive.setIdentity(this.identity.getBytes(ZMQ.CHARSET));
        this.workerEventFrontEndReceiveConnection = workerEventFrontEndReceiveConnection;
        System.out.println("WorkerZeroMQ connect to: " + this.workerEventFrontEndReceiveConnection);
        this.workerEventFrontEndReceive.connect(this.workerEventFrontEndReceiveConnection);
    }

    protected void close() {
        this.workerCommandFrontEndReceive.close();
        this.workerEventFrontEndReceive.close();
        this.context.close();
    }

    protected abstract void sendReadyCommand();

    protected void sendResultCommand(ZMsg request, String result) {
        request.addLast(result);
        request.send(this.workerCommandFrontEndReceive);
    }
}
