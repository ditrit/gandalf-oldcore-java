package com.orness.gandalf.core.connector.connectorworkflowengineservice.communication.command;

import com.orness.gandalf.core.module.zeromqmodule.command.worker.WorkerZeroMQ;
import org.zeromq.ZFrame;
import org.zeromq.ZMsg;

import static com.orness.gandalf.core.module.constantmodule.communication.Constant.*;


public class WorkerWorkflowEngineZeroMQ extends WorkerZeroMQ implements Runnable {

    public WorkerWorkflowEngineZeroMQ(String connection) {
        super(connection);
    }

    public void command(String command) {
        switch(command) {
            case COMMAND_UNSUBSCRIBE:
                break;
            case COMMAND_SUBSCRIBE:
                break;
            case COMMAND_DELETE_TOPIC:
                break;
            //COMMAND_CREATE_TOPIC:
            default:
                break;
        }
    }

    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            ZMsg msg = ZMsg.recvMsg(this.worker);
            ZFrame address = msg.pop();
            ZFrame command = msg.pop();
            ZFrame content = msg.pop();

            System.out.println("ID " + identity);
            System.out.println("REQ ADD " + address);
            System.out.println("REQ COMM " + command);
            System.out.println("REQ CONT " + content);


            this.command(command.toString());
            //  Send reply back to client
            address.send(worker, ZFrame.REUSE + ZFrame.MORE);
            command.send(worker, ZFrame.REUSE + ZFrame.MORE);
            content.send(worker, ZFrame.REUSE);
            //worker.send("World".getBytes(), 0);
        }

    }
}
