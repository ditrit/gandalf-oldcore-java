package com.orness.gandalf.core.module.zeromqmodule.publisher;

import com.orness.gandalf.core.module.messagemodule.domain.MessageGandalf;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZThread;
import org.zeromq.ZMQ.Socket;

public class Publisher implements ZThread.IDetachedRunnable {

    private ZContext context;
    private Socket publisher;
    private String connection;
    private int i;

    /*public static void main(String... args) {
        new Publisher("ipc://pub").run(null);
    }


    public Publisher(String connection) {
        this.connection = connection;
        this.run(null);
    }*/


    @Override
    public void run(Object[] args) {
        open();
        MessageGandalf messageGandalf = new MessageGandalf("toto", "toto", "2018-09-01 09:01:15", "2014-10-21", "toto");
        while (!Thread.currentThread().isInterrupted()) {
            MessageGandalf current = messageGandalf;
            current.setContent(messageGandalf.getContent()+"_"+i++);
            //TOPIC
            String topic = "test";
            publisher.sendMore(topic);
            //DATA
            publisher.send(messageGandalf.toString());
            //PRINT
            System.out.println(topic + " " + messageGandalf.toString());
            try {
                Thread.sleep(10);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
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
