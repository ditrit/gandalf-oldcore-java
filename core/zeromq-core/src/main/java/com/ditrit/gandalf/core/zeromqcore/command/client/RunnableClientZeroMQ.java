package com.ditrit.gandalf.core.zeromqcore.command.client;

import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

import java.util.LinkedList;
import java.util.List;

public class RunnableClientZeroMQ extends ClientZeroMQ implements Runnable {

    //TODO MAYBE REQUESTS
    private LinkedList<ZMsg> responses;

    protected void initRunnable(String identity, List<String> backEndClientConnections) {
        this.init(identity, backEndClientConnections);
        responses = new LinkedList<>();
    }

    @Override
    public void run() {
        ZMQ.Poller poller = this.context.createPoller(1);
        poller.register(this.backEndClient, ZMQ.Poller.POLLIN);

        ZMsg response = null;
        boolean more = false;

        while (!Thread.currentThread().isInterrupted()) {
            poller.poll(1000);
            //Client
            if (poller.pollin(0)) {
                while (true) {
                    response = ZMsg.recvMsg(this.backEndClient, ZMQ.NOBLOCK);
                    more = this.backEndClient.hasReceiveMore();

                    System.out.println(response);
                    System.out.println(more);

                    if (response == null) {
                        break; // Interrupted
                    }
                    this.responses.add(response.duplicate());

                    if(!more) {
                        break;
                    }
                }
            }
        }
        if (Thread.currentThread().isInterrupted()) {
            System.out.println("W: interrupted");
            poller.close();
            this.close(); // interrupted
        }
    }

    public ZMsg getLastReponses() {
        if(this.responses.isEmpty()) {
            return null;
        }
        return this.responses.getLast();
    }
}
