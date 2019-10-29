package com.ditrit.gandalf.core.zeromqcore.library.listener;

import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

public class RunnableListenerEventZeroMQ extends ListenerEventZeroMQ implements Runnable {

    protected void initRunnable(String identity, String frontEndListenerConnection) {
        this.init(identity, frontEndListenerConnection);
    }

    public ZMsg getEventSync() {

        ZMsg event;
        boolean more = false;

        event = ZMsg.recvMsg(this.frontEndListener);
        more = this.frontEndListener.hasReceiveMore();
        System.out.println(event);
        System.out.println(more);

        return event;
    }

    public ZMsg getEventAsync() {
        if(this.events.isEmpty()) {
            return null;
        }
        return this.events.poll();
    }

    @Override
    public void run() {
        ZMQ.Poller poller = this.context.createPoller(1);
        poller.register(this.frontEndListener, ZMQ.Poller.POLLIN);

        ZMsg event = null;
        boolean more = false;

        while (!Thread.currentThread().isInterrupted()) {
            poller.poll();

            if (poller.pollin(0)) {
                while (true) {
                    event = ZMsg.recvMsg(this.frontEndListener, ZMQ.NOBLOCK);
                    more = this.frontEndListener.hasReceiveMore();

                    System.out.println(event);
                    System.out.println(more);

                    if (event == null) {
                        break;
                    }
                    this.events.add(event.duplicate());

                    if(!more) {
                        break;
                    }
                }
            }
        }
        if (Thread.currentThread().isInterrupted()) {
            System.out.println("W: interrupted");
            poller.close();
            this.close();
        }
    }

    public void close() {
        this.frontEndListener.close();
        this.context.close();
    }

    protected void reconnect() {
        if (this.frontEndListenerConnection != null) {
            this.context.destroySocket(frontEndListener);
        }
        this.init(this.identity, this.frontEndListenerConnection);
    }
}
