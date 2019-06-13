package com.orness.gandalf.core.library.zeromqjavaclient.command;

import com.orness.gandalf.core.module.zeromqmodule.command.client.ClientZeroMQ;
import org.zeromq.ZFrame;
import org.zeromq.ZMsg;

import static com.orness.gandalf.core.module.constantmodule.communication.Constant.*;

public class ClientWorkflowEngineZeroMQ extends ClientZeroMQ {

    public ClientWorkflowEngineZeroMQ(String connection) {
        super(connection);
    }

    public void command(String command) {
        switch(command) {
            case COMMAND_UNSUBSCRIBE:
                break;
            //COMMAND_SUBSCRIBE:
            default:
                break;
        }
    }

    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            //this.command();
            client.sendMore(identity);
            client.sendMore("command");
            client.send("toto", 0);

            ZMsg msg = ZMsg.recvMsg(client);
            ZFrame address = msg.pop();
            ZFrame command = msg.pop();
            ZFrame content = msg.pop();

            System.out.println("ID " + identity);
            System.out.println("REP ADD " + address);
            System.out.println("REP COMM " + command);
            System.out.println("REP CONT " + content);
            msg.destroy();
        }
    }
}
