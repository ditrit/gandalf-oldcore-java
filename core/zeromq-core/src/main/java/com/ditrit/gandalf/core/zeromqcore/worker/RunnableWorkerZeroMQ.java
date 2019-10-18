package com.ditrit.gandalf.core.zeromqcore.worker;

import com.ditrit.gandalf.core.zeromqcore.constant.Constant;
import com.ditrit.gandalf.core.zeromqcore.worker.domain.Function;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

import java.util.List;
import java.util.Map;


public abstract class RunnableWorkerZeroMQ extends WorkerZeroMQ implements Runnable {

    private List<String> topics;
    protected Map<String , Function> commands;
    protected Map<String, Function> events;


    protected void initRunnable(String identity, String workerCommandFrontEndReceiveConnection, String workerEventFrontEndReceiveConnection, List<String> topics) {
        this.init(identity, workerCommandFrontEndReceiveConnection, workerEventFrontEndReceiveConnection);
        this.topics = topics;
/*        for(String topic : this.topics) {
            this.frontEndSubscriberWorker.subscribe(topic.getBytes(ZMQ.CHARSET));
        }*/
        this.workerEventFrontEndReceive.subscribe(ZMQ.SUBSCRIPTION_ALL);
    }

    @Override
    public void run() {
        ZMQ.Poller poller = this.context.createPoller(2);
        poller.register(this.workerCommandFrontEndReceive, ZMQ.Poller.POLLIN);
        poller.register(this.workerEventFrontEndReceive, ZMQ.Poller.POLLIN);

        ZMsg command;
        ZMsg event;
        boolean more = false;

        while (!Thread.currentThread().isInterrupted()) {
            this.sendReadyCommand();

            poller.poll(1000);

            if (poller.pollin(0)) {
                while (true) {
                    command = ZMsg.recvMsg(this.workerCommandFrontEndReceive, ZMQ.NOBLOCK);
                    more = this.workerCommandFrontEndReceive.hasReceiveMore();
                    System.out.println(command);
                    System.out.println(more);

                    if (command == null) {
                        break;
                    }

                    this.processRoutingWorkerCommand(command);

                    if(!more) {
                        break;
                    }
                }
            }

            if (poller.pollin(1)) {
                while (true) {
                    event = ZMsg.recvMsg(this.workerEventFrontEndReceive, ZMQ.NOBLOCK);
                    more = this.workerEventFrontEndReceive.hasReceiveMore();
                    System.out.println(event);
                    System.out.println(more);

                    if (event == null) {
                        break;
                    }

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
            this.close();
        }
    }

    private void processRoutingWorkerCommand(ZMsg command) {
        String result = this.executeWorkerCommandFunction(command.duplicate()).toString();
        this.sendResultCommand(command, result);
        command.destroy();
    }

    private void processRoutingSubscriberCommand(ZMsg event) {
        this.executeWorkerEventFunction(event.duplicate());
        event.destroy();
    }

    private ZMsg updateHeaderFrontEndWorker(ZMsg command) {
        command.removeFirst();
        return command;
    }

    protected void reconnectToConnector() {
        if(this.workerCommandFrontEndReceive != null) {
            this.context.destroySocket(this.workerCommandFrontEndReceive);
        }
        if(this.workerEventFrontEndReceive != null) {
            this.context.destroySocket(this.workerEventFrontEndReceive);
        }
        this.initRunnable(this.identity, this.workerCommandFrontEndReceiveConnection, this.workerEventFrontEndReceiveConnection, this.topics);

        // Register service with broker
        this.sendReadyCommand();
    }

    @Override
    protected void sendReadyCommand() {
        ZMsg ready = new ZMsg();
        ready.add(Constant.COMMAND_READY);
        ready.add(this.commands.keySet().toString());
        ready.send(this.workerCommandFrontEndReceive);
        ready.destroy();
    }

    protected abstract Constant.Result executeWorkerCommandFunction(ZMsg commandExecute);

    protected abstract void executeWorkerEventFunction(ZMsg commandExecute);

    protected Function getFunctionByCommand(ZMsg commandExecute) {
        Function function = null;
        function = this.commands.get(commandExecute.toArray()[6]);
        return function;
    }

    protected Function getFunctionByEvent(ZMsg eventExecute) {
        Function function = null;
        function = this.events.get(eventExecute.toArray()[1]);
        return function;
    }

    protected void addFunctionCommand(String command, Function function) {
        if(!commands.containsKey(command)) {
            this.commands.put(command, function);
        }
    }

    protected void addFunctionEvent(String event, Function function) {
        if(!events.containsKey(event)) {
            this.events.put(event, function);
        }
    }
}
