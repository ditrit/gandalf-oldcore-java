package com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

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

    protected void sendCommandState(ZMsg request, String state, String payload) {
        ZMsg response = new ZMsg();
        Object[] requestArray = request.toArray();
        request.addLast(payload);
        request.addLast(state);
        response.addFirst(requestArray[14].toString());
        for(int i = 8; i >= 0 ; i++) {
            response.addFirst(requestArray[i].toString());
        }
        request.send(this.workerCommandFrontEndReceive);
    }
}

