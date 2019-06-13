package com.orness.gandalf.core.connector.connectorbusservice.communication.command;

import com.orness.gandalf.core.connector.connectorbusservice.manager.ConnectorBusManager;
import com.orness.gandalf.core.module.zeromqmodule.command.worker.WorkerZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.zeromq.ZFrame;
import org.zeromq.ZMsg;

import static com.orness.gandalf.core.module.constantmodule.communication.Constant.*;

public class WorkerBusZeroMQ extends WorkerZeroMQ implements Runnable {

    @Autowired
    private ConnectorBusManager connectorBusManager;

    public WorkerBusZeroMQ(String connection) {
        super(connection);
    }

    public void command(ZFrame sender, String command, String content) {

        System.out.println("ID " + this.identity);
        System.out.println("REQ ID " + sender);
        System.out.println("REQ COMMAND " + command);
        System.out.println("REQ CONTENT " + content);

        switch(command) {
            case COMMAND_UNSUBSCRIBE:
                //connectorBusManager.topicUnsubscription();
                break;
            case COMMAND_SUBSCRIBE:

                //connectorBusManager.topicSubscription();
                break;
            case COMMAND_DELETE_TOPIC:
                connectorBusManager.topicSuppression(content);
                break;
            //COMMAND_CREATE_TOPIC:
            default:
                connectorBusManager.topicCreation(content);
                break;
        }
    }

    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            ZMsg msg = ZMsg.recvMsg(this.worker);
            ZFrame sender = msg.pop();
            ZFrame command = msg.pop();
            ZFrame content = msg.pop();

            this.command(sender, command.toString(), content.toString());

            //  Send reply back to client
            sender.send(worker, ZFrame.REUSE + ZFrame.MORE);
            command.send(worker, ZFrame.REUSE + ZFrame.MORE);
            content.send(worker, ZFrame.REUSE);
            //worker.send("World".getBytes(), 0);
        }

    }
}
