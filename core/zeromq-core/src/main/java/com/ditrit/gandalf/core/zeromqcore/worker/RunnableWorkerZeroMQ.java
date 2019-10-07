package com.ditrit.gandalf.core.zeromqcore.worker;

import com.ditrit.gandalf.core.zeromqcore.constant.Constant;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

import java.util.List;

import static com.ditrit.gandalf.core.zeromqcore.constant.Constant.COMMAND_CLIENT_SEND;
import static com.ditrit.gandalf.core.zeromqcore.constant.Constant.EVENT_CLIENT_SEND;

public abstract class RunnableWorkerZeroMQ extends WorkerZeroMQ implements Runnable {

    private List<String> topics;

    protected void initRunnable(String workerServiceClass, String frontEndWorkerConnection, String frontEndSubscriberWorkerConnection, List<String> topics) {
        this.init(workerServiceClass, frontEndWorkerConnection, frontEndSubscriberWorkerConnection);
        this.topics = topics;
/*        for(String topic : this.topics) {
            this.frontEndSubscriberWorker.subscribe(topic.getBytes(ZMQ.CHARSET));
        }*/
        this.frontEndSubscriberWorker.subscribe(ZMQ.SUBSCRIPTION_ALL);
    }

    @Override
    public void run() {
        ZMQ.Poller poller = this.context.createPoller(2);
        poller.register(this.frontEndWorker, ZMQ.Poller.POLLIN);
        poller.register(this.frontEndSubscriberWorker, ZMQ.Poller.POLLIN);

        ZMsg command;
        ZMsg event;
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
                    System.out.println(command);
                    System.out.println(more);

                    if (command == null) {
                        break; // Interrupted
                    }

                    //Process
                    this.processRoutingWorkerCommand(command);

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

    private void processRoutingWorkerCommand(ZMsg command) {
        ZMsg commandBackup = command.duplicate();
        String commandType = commandBackup.popString();
        if(commandType.equals(COMMAND_CLIENT_SEND)) {
            command = this.updateHeaderFrontEndWorker(command);
            String result = this.executeRoutingWorkerCommand(command).toString();
            this.sendResultCommand(command, result);
        }
        else {
            System.out.println("E: invalid message");
        }
        commandBackup.destroy();
        command.destroy();
    }

    private void processRoutingSubscriberCommand(ZMsg event) {
        ZMsg eventBackup = event.duplicate();
        String topic = eventBackup.popString();
        String commandType = eventBackup.popString();
        if(commandType.equals(EVENT_CLIENT_SEND)) {
            event = this.updateHeaderFrontEndSubscriber(event, topic);
            this.executeRoutingSubscriberCommand(event);
        }
        else {
            System.out.println("E: invalid message");
        }
        eventBackup.destroy();
        event.destroy();
    }

    private ZMsg updateHeaderFrontEndWorker(ZMsg command) {
        command.removeFirst();
        return command;
    }

    private ZMsg updateHeaderFrontEndSubscriber(ZMsg event, String topic) {
        event.removeFirst();
        event.removeFirst();
        event.addFirst(topic);
        return event;
    }

    protected void reconnectToRoutingWorker() {
        if (this.frontEndWorker != null) {
            this.context.destroySocket(frontEndWorker);
        }
        this.initRunnable(this.workerServiceClass, this.frontEndWorkerConnection, this.frontEndSubscriberWorkerConnection, this.topics);

        // Register service with broker
        this.sendReadyCommand();
    }

    protected abstract Constant.Result  executeRoutingWorkerCommand(ZMsg command);

    protected abstract void executeRoutingSubscriberCommand(ZMsg command);
}
