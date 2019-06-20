package com.orness.gandalf.core.connector.connectorworkflowengineservice.communication.command;

import com.orness.gandalf.core.connector.connectorworkflowengineservice.manager.ConnectorWorkflowEngineManager;
import com.orness.gandalf.core.module.zeromqmodule.command.worker.WorkerZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zeromq.ZFrame;
import org.zeromq.ZMsg;

import static com.orness.gandalf.core.module.constantmodule.communication.CommunicationConstant.*;

@Component
@Scope("singleton")
public class WorkerWorkflowEngineZeroMQ extends WorkerZeroMQ implements Runnable {

    @Autowired
    private ConnectorWorkflowEngineManager connectorWorkflowEngineManager;

    public WorkerWorkflowEngineZeroMQ(@Value("${gandalf.communication.worker}") String connection) {
        super(connection);
    }

    public void command(ZFrame sender, String command, String content) {
        switch(command) {
            case COMMAND_UNSUBSCRIBE:
                System.out.println("UNSUB" );
                this.connectorWorkflowEngineManager.unsubscribeTopic(content);
                break;
            case COMMAND_SUBSCRIBE:
                System.out.println("SUB" );
                this.connectorWorkflowEngineManager.subscribeTopic(content);
                break;
            default:
                System.out.println("NOT" );
                //DO NOTHING
                break;
        }
    }

    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            ZMsg msg = ZMsg.recvMsg(this.worker);
            ZFrame sender = msg.pop();
            ZFrame command = msg.pop();
            ZFrame content = msg.pop();

            System.out.println("ID " + identity);
            System.out.println("REQ ADD " + sender);
            System.out.println("REQ COMM " + command);
            System.out.println("REQ CONT " + content);


            this.command(sender, command.toString(), content.toString());
            //  Send reply back to client
            sender.send(worker, ZFrame.REUSE + ZFrame.MORE);
            command.send(worker, ZFrame.REUSE + ZFrame.MORE);
            content.send(worker, ZFrame.REUSE);
            //worker.send("World".getBytes(), 0);
        }

    }
}
