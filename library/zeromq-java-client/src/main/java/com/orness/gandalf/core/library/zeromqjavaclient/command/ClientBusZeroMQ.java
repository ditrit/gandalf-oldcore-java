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
    }

    public void deleteTopic(String topic) {
        //REQUEST
        client.sendMore(identity);
        client.sendMore(COMMAND_DELETE_TOPIC);
        client.send(topic, 0);

        //RESPONSE
    }

    public void subscribeTopic(String topic) {

    }

    public void unsubscribeTopic(String topic) {

    }

    public void command(String identity, String command, String content) {
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
