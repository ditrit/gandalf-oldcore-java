package com.orness.gandalf.core.library.zeromqjavaclient.command;

import com.orness.gandalf.core.module.zeromqmodule.command.client.ClientZeroMQ;
import org.zeromq.ZFrame;
import org.zeromq.ZMsg;

import static com.orness.gandalf.core.module.constantmodule.communication.Constant.*;

public class ClientWorkflowEngineZeroMQ extends ClientZeroMQ {

    public ClientWorkflowEngineZeroMQ(String connection) {
        super(connection);
    }

    public void subscribeTopic(String topic) {
        //REQUEST
        client.sendMore(identity);
        client.sendMore(COMMAND_SUBSCRIBE);
        client.send(topic, 0);

        //RESPONSE
        ZMsg msg = ZMsg.recvMsg(client);
        ZFrame sender = msg.pop();
        ZFrame command = msg.pop();
        ZFrame content = msg.pop();

        System.out.println("ID " + identity);
        System.out.println("REP ADD " + sender);
        System.out.println("REP COMM " + command);
        System.out.println("REP CONT " + content);
        msg.destroy();
    }

    public void unsubscribeTopic(String topic) {
        //REQUEST
        client.sendMore(identity);
        client.sendMore(COMMAND_UNSUBSCRIBE);
        client.send(topic, 0);

        //RESPONSE
        ZMsg msg = ZMsg.recvMsg(client);
        ZFrame sender = msg.pop();
        ZFrame command = msg.pop();
        ZFrame content = msg.pop();

        System.out.println("ID " + identity);
        System.out.println("REP ADD " + sender);
        System.out.println("REP COMM " + command);
        System.out.println("REP CONT " + content);
        msg.destroy();
    }
}
