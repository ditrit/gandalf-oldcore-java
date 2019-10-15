package com.ditrit.gandalf.core.zeromqcore.service.listener;

import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

public class RunnableListenerServiceZeroMQ extends ListenerServiceZeroMQ implements Runnable {

    protected void initRunnable(String identity, String serviceListenerConnection) {
        this.init(identity, serviceListenerConnection);
    }

    @Override
    public void run() {
        ZMQ.Poller poller = this.context.createPoller(1);
        poller.register(this.serviceListener, ZMQ.Poller.POLLIN);

        ZMsg request = null;
        boolean more = false;

        while (!Thread.currentThread().isInterrupted()) {
            poller.poll();
            //Client
            if (poller.pollin(0)) {
                while (true) {
                    request = ZMsg.recvMsg(this.serviceListener);
                    more = this.serviceListener.hasReceiveMore();

                    System.out.println(request);
                    System.out.println(more);

                    if (request == null) {
                        break; // Interrupted
                    }
                    this.processRequestService(request);

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

    public void processRequestService(ZMsg request) {
        //TODO REQUEST
    }
}
