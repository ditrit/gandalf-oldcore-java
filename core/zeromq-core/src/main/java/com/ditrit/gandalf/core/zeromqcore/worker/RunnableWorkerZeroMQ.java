package com.ditrit.gandalf.core.zeromqcore.worker;

import com.ditrit.gandalf.core.zeromqcore.constant.Constant;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

import java.util.LinkedList;
import java.util.List;


public abstract class RunnableWorkerZeroMQ extends WorkerZeroMQ implements Runnable {

    private List<String> topics;
    private LinkedList<ZMsg> results;


    protected void initRunnable(String workerServiceClass, String workerCommandFrontEndReceiveConnection, String workerEventFrontEndReceiveConnection, List<String> topics) {
        this.init(workerServiceClass, workerCommandFrontEndReceiveConnection, workerEventFrontEndReceiveConnection);
        this.topics = topics;
/*        for(String topic : this.topics) {
            this.frontEndSubscriberWorker.subscribe(topic.getBytes(ZMQ.CHARSET));
        }*/
        //this.workerEventFrontEndReceive.subscribe(ZMQ.SUBSCRIPTION_ALL);
        this.workerEventFrontEndReceive.subscribe("demonstration");
    }

    @Override
    public void run() {
        ZMQ.Poller poller = this.context.createPoller(2);
        poller.register(this.workerCommandFrontEndReceive, ZMQ.Poller.POLLIN);
        poller.register(this.workerEventFrontEndReceive, ZMQ.Poller.POLLIN);

        ZMsg command;
        ZMsg event;
        String typeRouting;
        boolean more = false;

        while (!Thread.currentThread().isInterrupted()) {

            poller.poll(1000);

            //Worker Receive Command
            if (poller.pollin(0)) {
                while (true) {
                    // Receive broker message
                    command = ZMsg.recvMsg(this.workerCommandFrontEndReceive, ZMQ.NOBLOCK);
                    more = this.workerCommandFrontEndReceive.hasReceiveMore();
                    System.out.println(command);
                    System.out.println(more);

                    if (command == null) {
                        break; // Interrupted
                    }

                    //Process
                    this.processRoutingWorkerCommand(command);

                    if(!more) {
                        break;
                    }
                }
            }

            //Worker Receive Event
            if (poller.pollin(1)) {
                while (true) {
                    // Receive broker message
                    event = ZMsg.recvMsg(this.workerEventFrontEndReceive, ZMQ.NOBLOCK);
                    more = this.workerEventFrontEndReceive.hasReceiveMore();
                    System.out.println(event);
                    System.out.println(more);

                    if (event == null) {
                        break; // Interrupted
                    }

                    //Process
                    this.processRoutingSubscriberCommand(event);

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

    private void processRoutingWorkerResultCommand(ZMsg result) {
        if(result.size() == 10) {
            this.results.add(result.duplicate());
        }
        else {
            System.out.println("E: invalid message");
        }
        result.destroy();
    }

    private void processRoutingWorkerCommand(ZMsg command) {
        if(command.size() == 9) {
            String result = this.executeRoutingWorkerCommand(command).toString();
            this.sendResultCommand(command, result);
        }
        else {
            System.out.println("E: invalid message");
        }
        command.destroy();
    }

    private void processRoutingSubscriberCommand(ZMsg event) {
        if(event.size() == 5) {
            this.executeRoutingSubscriberCommand(event);
        }
        else {
            System.out.println("E: invalid message");
        }
        event.destroy();
    }

    private ZMsg updateHeaderFrontEndWorker(ZMsg command) {
        command.removeFirst();
        return command;
    }

    protected void reconnectToRoutingWorker() {
        if(this.workerCommandFrontEndReceive != null) {
            this.context.destroySocket(this.workerCommandFrontEndReceive);
        }
        if(this.workerEventFrontEndReceive != null) {
            this.context.destroySocket(this.workerEventFrontEndReceive);
        }
        this.initRunnable(this.workerServiceClass, this.workerCommandFrontEndReceiveConnection, this.workerEventFrontEndReceiveConnection, this.topics);

        // Register service with broker
        this.sendReadyCommand();
    }

    protected abstract Constant.Result  executeRoutingWorkerCommand(ZMsg command);

    protected abstract void executeRoutingSubscriberCommand(ZMsg event);
}
