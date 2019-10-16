package com.ditrit.gandalf.core.zeromqcore.worker;

import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

public abstract class RunnableCaptureWorkerZeroMQ extends CaptureWorkerZeroMQ implements Runnable {

    protected void initRunnable(String workerServiceClass, String frontEndWorkerConnection, String frontEndSubscriberWorkerConnection) {
        this.init(workerServiceClass, frontEndWorkerConnection, frontEndSubscriberWorkerConnection);
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

            poller.poll();
            //Client
            if (poller.pollin(0)) {
                while (true) {
                    // Receive broker message
                    command = ZMsg.recvMsg(this.frontEndWorker);
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
                    event = ZMsg.recvMsg(this.frontEndSubscriberWorker);
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
        return command;
    }

    private ZMsg updateHeaderEvent(ZMsg event) {
        return event;
    }

    protected void reconnectToRoutingWorker() {
        if (this.frontEndWorker != null) {
            this.context.destroySocket(frontEndWorker);
        }
        this.initRunnable(this.workerServiceClass, this.frontEndWorkerConnection, this.frontEndSubscriberWorkerConnection);

    }

    protected abstract void executeRoutingWorkerCommand(ZMsg command);

    protected abstract void executeRoutingSubscriberCommand(ZMsg command);
}
