package com.ditrit.gandalf.core.zeromqcore.library.listener;

import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

public class RunnableListenerCommandZeroMQ extends ListenerCommandZeroMQ implements Runnable {

    protected void initRunnable(String identity, String frontEndListenerConnection) {
        this.init(identity, frontEndListenerConnection);
    }

    public ZMsg getCommandSync() {
        ZMsg command;
        boolean more = false;

        command = ZMsg.recvMsg(this.frontEndListener);
        more = this.frontEndListener.hasReceiveMore();
        System.out.println(command);
        System.out.println(more);

        return command;
    }

    public ZMsg getCommandAsync() {
        if(this.commands.isEmpty()) {
            return null;
        }
        return this.commands.poll();
    }

    @Override
    public void run() {
        ZMQ.Poller poller = this.context.createPoller(1);
        poller.register(this.frontEndListener, ZMQ.Poller.POLLIN);

        ZMsg request = null;
        boolean more = false;

        while (!Thread.currentThread().isInterrupted()) {
            poller.poll();

            if (poller.pollin(0)) {
                while (true) {
                    request = ZMsg.recvMsg(this.frontEndListener, ZMQ.NOBLOCK);
                    more = this.frontEndListener.hasReceiveMore();

                    System.out.println(request);
                    System.out.println(more);

                    if (request == null) {
                        break;
                    }
                    request = this.updateHeaderBrokerMessage(request);
                    this.commands.add(request.duplicate());

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

    private ZMsg updateHeaderBrokerMessage(ZMsg request) {
        request.removeFirst();
        return request;
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
