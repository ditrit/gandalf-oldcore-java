package com.orness.gandalf.core.module.zeromqmodule.publisher;

import com.orness.gandalf.core.module.messagemodule.domain.MessageGandalf;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZThread;
import org.zeromq.ZMQ.Socket;

public class PublisherZeroMQ {

    private ZContext context;
    private Socket publisher;
    private String connection;

    public PublisherZeroMQ(String connectio) {
        this.connection = connection;
    }

    public Socket getPublisher() {
        return publisher;
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
