package com.ditrit.gandalf.core.zeromqcore.service.listener;

import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

public abstract class RunnableListenerServiceZeroMQ extends ListenerServiceZeroMQ implements Runnable {

    public RunnableListenerServiceZeroMQ() {
        super();
    }

    protected void initRunnable(String identity, String frontEndListenerConnection) {
        this.init(identity, frontEndListenerConnection);
    }

    @Override
    public void run() {
        ZMQ.Poller poller = this.context.createPoller(1);
        poller.register(this.frontEndListener, ZMQ.Poller.POLLIN);

        ZMsg request = null;
        boolean more = false;

        while (!Thread.currentThread().isInterrupted()) {
            poller.poll();
            //Client
            if (poller.pollin(0)) {
                while (true) {
                    // Receive broker message
                    request = ZMsg.recvMsg(this.frontEndListener, ZMQ.NOBLOCK);
                    more = this.frontEndListener.hasReceiveMore();

                    System.out.println(request);
                    System.out.println(more);

                    if (request == null) {
                        break; // Interrupted
                    }
                    this.processRequestService(request.duplicate());

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

    public abstract void processRequestService(ZMsg request);

}
