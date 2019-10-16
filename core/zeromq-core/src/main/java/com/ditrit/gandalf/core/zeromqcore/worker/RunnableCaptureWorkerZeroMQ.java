package com.ditrit.gandalf.core.zeromqcore.worker;

import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

//TODO REVOIR
public abstract class RunnableCaptureWorkerZeroMQ extends CaptureWorkerZeroMQ implements Runnable {

    protected void initRunnable(String identity, String frontEndWorkerConnection, String frontEndSubscriberWorkerConnection) {
        this.init(identity, frontEndWorkerConnection, frontEndSubscriberWorkerConnection);
        this.frontEndSubscriberWorker.subscribe(ZMQ.SUBSCRIPTION_ALL);
    }

    @Override
    public void run() {
        ZMQ.Poller poller = this.context.createPoller(2);
        poller.register(this.frontEndWorker, ZMQ.Poller.POLLIN);
        poller.register(this.frontEndSubscriberWorker, ZMQ.Poller.POLLIN);

        ZMsg command;
        ZMsg event;
        boolean more = false;

        while (!Thread.currentThread().isInterrupted()) {
            this.sendReadyCommand();

            poller.poll(1000);
            //Client
            if (poller.pollin(0)) {
                while (true) {
                    // Receive broker message
                    command = ZMsg.recvMsg(this.frontEndWorker, ZMQ.NOBLOCK);
                    more = this.frontEndWorker.hasReceiveMore();
                    System.out.println(command);
                    System.out.println(more);

                    if (command == null) {
                        break; // Interrupted
                    }

                    //Process
                    this.processCommand(command);

                    if (!more) {
                        break;
                    }
                }
            }
            if (poller.pollin(1)) {
                while (true) {
                    // Receive broker message
                    event = ZMsg.recvMsg(this.frontEndSubscriberWorker, ZMQ.NOBLOCK);
                    more = this.frontEndSubscriberWorker.hasReceiveMore();
                    System.out.println(event);
                    System.out.println(more);

                    if (event == null) {
                        break; // Interrupted
                    }

                    //Process
                    this.processEvent(event);

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

    private void processCommand(ZMsg command) {
        command = this.updateHeaderCommand(command);
        this.executeRoutingWorkerCommand(command);
    }

    private void processEvent(ZMsg event) {
        event = this.updateHeaderEvent(event);
        this.executeRoutingSubscriberCommand(event);
    }

    private ZMsg updateHeaderCommand(ZMsg command) {
        command.removeFirst();
        command.removeFirst();
        return command;
    }

    private ZMsg updateHeaderEvent(ZMsg event) {
        event.removeFirst();
        event.removeFirst();
        return event;
    }

    protected void reconnectToRoutingWorker() {
        if (this.frontEndWorker != null) {
            this.context.destroySocket(frontEndWorker);
        }
        this.initRunnable(this.identity, this.frontEndWorkerConnection, this.frontEndSubscriberWorkerConnection);

        // Register service with broker
        this.sendReadyCommand();
    }

    protected abstract void executeRoutingWorkerCommand(ZMsg command);

    protected abstract void executeRoutingSubscriberCommand(ZMsg command);
}
