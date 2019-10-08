package com.ditrit.gandalf.core.zeromqcore.worker;

import com.ditrit.gandalf.core.zeromqcore.constant.Constant;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

import java.util.LinkedList;
import java.util.List;


public abstract class RunnableWorkerZeroMQ extends WorkerZeroMQ implements Runnable {

    private List<String> topics;
    private LinkedList<ZMsg> results;


    protected void initRunnable(String workerServiceClass, String frontEndSendWorkerConnection, String frontEndReceiveWorkerConnection, String frontEndPublisherWorkerConnection, String frontEndSubscriberWorkerConnection, List<String> topics) {
        this.init(workerServiceClass, frontEndSendWorkerConnection, frontEndReceiveWorkerConnection, frontEndPublisherWorkerConnection, frontEndSubscriberWorkerConnection);
        this.topics = topics;
/*        for(String topic : this.topics) {
            this.frontEndSubscriberWorker.subscribe(topic.getBytes(ZMQ.CHARSET));
        }*/
        this.frontEndSubscriberWorker.subscribe(ZMQ.SUBSCRIPTION_ALL);
    }

    @Override
    public void run() {
        ZMQ.Poller poller = this.context.createPoller(3);
        poller.register(this.frontEndSendWorker, ZMQ.Poller.POLLIN);
        poller.register(this.frontEndReceiveWorker, ZMQ.Poller.POLLIN);
        poller.register(this.frontEndSubscriberWorker, ZMQ.Poller.POLLIN);

        ZMsg command;
        ZMsg event;
        String typeRouting;
        boolean more = false;

        while (!Thread.currentThread().isInterrupted()) {
            this.sendReadyCommand();

            poller.poll(1000);
            //Worker Receive Result
            if (poller.pollin(0)) {
                while (true) {
                    // Receive broker message
                    command = ZMsg.recvMsg(this.frontEndSendWorker, ZMQ.NOBLOCK);
                    more = this.frontEndSendWorker.hasReceiveMore();
                    System.out.println(command);
                    System.out.println(more);

                    if (command == null) {
                        break; // Interrupted
                    }

                    //Process
                    this.processRoutingWorkerResultCommand(command);

                    if (!more) {
                        break;
                    }
                }
            }

            //Worker Receive Command
            if (poller.pollin(1)) {
                while (true) {
                    // Receive broker message
                    command = ZMsg.recvMsg(this.frontEndReceiveWorker, ZMQ.NOBLOCK);
                    more = this.frontEndReceiveWorker.hasReceiveMore();
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
            if (poller.pollin(2)) {
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
        if(result.size() == 9) {
            this.results.add(result.duplicate());
        }
        else {
            System.out.println("E: invalid message");
        }
        result.destroy();
    }

    private void processRoutingWorkerCommand(ZMsg command) {
        if(command.size() == 7) {
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
        if (this.frontEndSendWorker != null) {
            this.context.destroySocket(frontEndSendWorker);
        }
        if(this.frontEndReceiveWorker != null) {
            this.context.destroySocket(this.frontEndReceiveWorker);
        }
        this.initRunnable(this.workerServiceClass, this.frontEndSendWorkerConnection, this.frontEndReceiveWorkerConnection, this.frontEndPublisherConnection, this.frontEndSubscriberWorkerConnection, this.topics);

        // Register service with broker
        this.sendReadyCommand();
    }


    public ZMsg sendCommandSync(String uuid, String connector, String serviceClass, String command, String timeout, String payload) {
        this.sendCommand(uuid, connector, serviceClass, command, timeout, payload);
        ZMsg result = null;
        do {
            result =  this.getCommandResult();
        } while(result == null);
        return result;
    }

    public void sendCommandAsync(String uuid, String connector, String serviceClass, String command, String timeout, String payload) {
        this.sendCommand(uuid, connector, serviceClass, command, timeout, payload);
    }

    public ZMsg getCommandResult() {
        if(this.results.isEmpty()) {
            return null;
        }
        return this.results.poll();
    }

    protected abstract Constant.Result  executeRoutingWorkerCommand(ZMsg command);

    protected abstract void executeRoutingSubscriberCommand(ZMsg event);
}
