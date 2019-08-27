package com.orness.gandalf.core.test.testzeromq.command;

import com.orness.gandalf.core.test.testzeromq.Constant;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

import static com.orness.gandalf.core.test.testzeromq.Constant.ROUTING_SUBSCRIBER;
import static com.orness.gandalf.core.test.testzeromq.Constant.ROUTING_WORKER;

public abstract class RunnableWorker extends Worker implements Runnable {

    protected void initRunnable(String workerServiceClass, String frontEndWorkerConnections) {
        this.init(workerServiceClass, frontEndWorkerConnections);
    }

    @Override
    public void run() {
        ZMQ.Poller poller = this.context.createPoller(1);
        poller.register(this.frontEndWorker, ZMQ.Poller.POLLIN);

        ZMsg command;
        String typeRouting;
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
                    ZMsg commandBackup = command.duplicate();

                    System.out.println(command);
                    System.out.println(more);

                    if (command == null) {
                        break; // Interrupted
                    }

                    //Process
                    typeRouting = commandBackup.popString();
                    command = this.removeHeaderFromRoutingWorker(command);

                    if(typeRouting.equals(ROUTING_WORKER)) {
                        this.processRoutingWorkerCommand(command);
                    }
                    else if(typeRouting.equals(ROUTING_SUBSCRIBER)) {
                        this.processRoutingSubscriberCommand(command);
                    }

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

    private void processRoutingWorkerCommand(ZMsg command) {
        String result = this.executeRoutingWorkerCommand(command).toString();
        this.sendResultCommand(command, result);
    }

    private void processRoutingSubscriberCommand(ZMsg command) {
        this.executeRoutingSubscriberCommand(command);
    }

    private ZMsg removeHeaderFromRoutingWorker(ZMsg command) {
        command.removeFirst();
        return command;
    }

    protected void reconnectToRoutingWorker() {
        if (this.frontEndWorker != null) {
            this.context.destroySocket(frontEndWorker);
        }
        this.initRunnable(this.workerServiceClass, this.frontEndWorkerConnections);

        // Register service with broker
        this.sendReadyCommand();
    }

    protected abstract Constant.Result executeRoutingWorkerCommand(ZMsg command);

    protected abstract void executeRoutingSubscriberCommand(ZMsg command);
}
