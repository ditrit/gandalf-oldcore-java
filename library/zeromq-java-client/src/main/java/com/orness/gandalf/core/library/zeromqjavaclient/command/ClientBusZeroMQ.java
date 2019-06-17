package com.orness.gandalf.core.library.zeromqjavaclient.command;

import com.orness.gandalf.core.module.zeromqmodule.command.client.ClientZeroMQ;
import org.zeromq.ZFrame;
import org.zeromq.ZMsg;

import static com.orness.gandalf.core.module.constantmodule.communication.Constant.*;

public class ClientBusZeroMQ extends ClientZeroMQ {

    public ClientBusZeroMQ(String connection) {
        super(connection);
    }

    public void createTopic(String topic) {
        //REQUEST
        client.sendMore(identity);
        client.sendMore(COMMAND_CREATE_TOPIC);
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

    public void deleteTopic(String topic) {
        //REQUEST
        client.sendMore(identity);
        client.sendMore(COMMAND_DELETE_TOPIC);
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

    public void sendMessageTopic(String topic, String message) {
        //REQUEST
        client.sendMore(identity);
        client.sendMore(COMMAND_SEND_MESSAGE_TOPIC);
        client.send(topic + "#" + message, 0);

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
