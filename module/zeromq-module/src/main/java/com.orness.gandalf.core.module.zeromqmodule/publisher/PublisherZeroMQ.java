package com.orness.gandalf.core.module.zeromqmodule.publisher;

import com.orness.gandalf.core.module.messagemodule.domain.MessageGandalf;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZThread;
import org.zeromq.ZMQ.Socket;

public class PublisherZeroMQ implements ZThread.IDetachedRunnable {

    private ZContext context;
    private Socket publisher;
    private String connection;
    private String topic;
    private MessageGandalf messageGandalf;

    public void setMessageGandalf(MessageGandalf messageGandalf) {
        this.messageGandalf = messageGandalf;
    }

    public PublisherZeroMQ(String connection, String topic) {
        this.connection = connection;
        this.topic = topic;
        this.run(null);
    }


    @Override
    public void run(Object[] args) {
        open();
        while (!Thread.currentThread().isInterrupted()) {
            if(messageGandalf != null) {
                //TOPIC
                publisher.sendMore(topic);
                //DATA
                publisher.send(messageGandalf.toString());
                //PRINT
                System.out.println(topic + " " + messageGandalf.toString());
                //RESET
                this.messageGandalf = null;
            }
        }
        close();
    }

    public void open() {
        context = new ZContext();
        publisher = context.createSocket(SocketType.PUB);
        //publisher.bind(connection);
        publisher.connect(connection);
    }


    public void close() {
        publisher.close();
        context.close();
    }
}
