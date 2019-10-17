package com.ditrit.gandalf.core.zeromqcore.command.connector;

import com.ditrit.gandalf.core.zeromqcore.constant.Constant;
import com.google.gson.Gson;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

import java.util.*;
import java.util.stream.Collectors;

public abstract class RunnableConnectorCommandZeroMQ extends ConnectorCommandZeroMQ implements Runnable {

    protected Gson mapper;
    private Map<String, LinkedList<ZMsg>> commandZMsgLinkedList;

    public RunnableConnectorCommandZeroMQ() {
        super();
        mapper = new Gson();
        this.commandZMsgLinkedList = new HashMap<>();
    }

    protected void initRunnable(String identity, String frontEndSendConnectorConnection, String frontEndReceiveConnectorConnection, String backEndSendConnectorConnection, String backEndReceiveConnectorConnection) {
        this.init(identity, frontEndSendConnectorConnection, frontEndReceiveConnectorConnection, backEndSendConnectorConnection, backEndReceiveConnectorConnection);
    }

    @Override
    public void run() {

        ZMQ.Poller poller = context.createPoller(4);
        poller.register(this.frontEndSendRoutingConnector, ZMQ.Poller.POLLIN);
        poller.register(this.frontEndReceiveRoutingConnector, ZMQ.Poller.POLLIN);
        poller.register(this.backEndSendRoutingConnector, ZMQ.Poller.POLLIN);
        poller.register(this.backEndReceiveRoutingConnector, ZMQ.Poller.POLLIN);

        ZMsg aggregatorMessage;
        ZMsg workerMessage;
        boolean more = false;

        while (!Thread.currentThread().isInterrupted()) {
            poller.poll();

            if (poller.pollin(0)) {
                while (true) {
                    aggregatorMessage = ZMsg.recvMsg(this.frontEndSendRoutingConnector);
                    more = this.frontEndSendRoutingConnector.hasReceiveMore();
                    System.out.println("RESULT COMMAND AGGREGATOR");
                    System.out.println(aggregatorMessage);
                    System.out.println(more);

                    if (aggregatorMessage == null) {
                        break;
                    }

                    this.processAggregatorSendMessage(aggregatorMessage);

                    if(!more) {
                        break;
                    }
                }
            }

            if (poller.pollin(1)) {
                while (true) {
                    aggregatorMessage = ZMsg.recvMsg(this.frontEndReceiveRoutingConnector);
                    more = this.frontEndReceiveRoutingConnector.hasReceiveMore();
                    System.out.println("COMMAND AGGREGATOR");
                    System.out.println(aggregatorMessage);
                    System.out.println(more);

                    if (aggregatorMessage == null) {
                        break;
                    }

                    this.processAggregatorReceiveMessage(aggregatorMessage);

                    if(!more) {
                        break;
                    }
                }
            }

            if (poller.pollin(2)) {
                while (true) {
                    workerMessage = ZMsg.recvMsg(this.backEndSendRoutingConnector);
                    more = this.backEndSendRoutingConnector.hasReceiveMore();
                    System.out.println("COMMAND SEND WORKER");
                    System.out.println(workerMessage);
                    System.out.println(more);

                    if (workerMessage == null) {
                        break;
                    }

                    this.processWorkerSendMessage(workerMessage);

                    if(!more) {
                        break;
                    }
                }
            }

            if (poller.pollin(3)) {
                while (true) {
                    workerMessage = ZMsg.recvMsg(this.backEndReceiveRoutingConnector);
                    more = this.backEndReceiveRoutingConnector.hasReceiveMore();
                    System.out.println("COMMAND RECEIVE WORKER");
                    System.out.println(workerMessage);
                    System.out.println(more);

                    if (workerMessage == null) {
                        break;
                    }

                    this.processWorkerReceiveMessage(workerMessage);

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

    private void processAggregatorSendMessage(ZMsg aggregatorMessage) {
        Object[] aggregatorMessageBackup = aggregatorMessage.duplicate().toArray();
        String sourceConnector = aggregatorMessageBackup[1].toString();
        aggregatorMessage = this.updateHeaderAggregatorSendMessage(aggregatorMessage, sourceConnector);
        this.sendResultToAggregator(aggregatorMessage.duplicate());

        aggregatorMessage.destroy();
    }

    private ZMsg updateHeaderAggregatorSendMessage(ZMsg aggregatorMessage, String sourceConnector) {
        aggregatorMessage.addFirst(sourceConnector);
        return aggregatorMessage;
    }

    private void processAggregatorReceiveMessage(ZMsg aggregatorMessage) {
        Object[] aggregatorMessageBackup = aggregatorMessage.duplicate().toArray();
        String command = aggregatorMessageBackup[12].toString();
        aggregatorMessage = this.updateHeaderAggregatorReceiveMessage(aggregatorMessage, command);
        this.getCommandZMsgLinkedList(command).add(aggregatorMessage.duplicate());

        aggregatorMessage.destroy();
    }

    private ZMsg updateHeaderAggregatorReceiveMessage(ZMsg aggregatorMessage, String targetConnector) {
        aggregatorMessage.addFirst(targetConnector);
        return aggregatorMessage;
    }

    private void processWorkerSendMessage(ZMsg workerMessage) {
        String sourceWorker = workerMessage.popString(); //SOURCE WORKER (DEALER)
        workerMessage = this.updateIdentityWorkerMessage(workerMessage, sourceWorker);
        this.sendCommandToAggregator(workerMessage);

        workerMessage.destroy();
    }

    private ZMsg updateIdentityWorkerMessage(ZMsg workerMessage, String sourceWorker) {
        String uuid = workerMessage.popString();
        workerMessage.addFirst(sourceWorker);
        workerMessage.addFirst(uuid);
        return workerMessage;
    }

    private void processWorkerReceiveMessage(ZMsg workerMessage) {
        Object[] workerMessageBackup = workerMessage.duplicate().toArray();
        String workerTarget = workerMessageBackup[0].toString(); //(READY)
        String commandType = workerMessageBackup[1].toString(); //(READY)
        if(commandType.equals(Constant.COMMAND_READY)) {
            String commands = workerMessageBackup[2].toString(); //(READY)
            ZMsg workerCommand = this.getCommandByWorkerCommands(commands);
            if(workerCommand != null) {
                workerCommand = this.updateIdentityWorkerReadyMessage(workerCommand, workerTarget);
                workerCommand = this.updateHeaderWorkerReceiveReadyMessage(workerCommand, workerTarget);
                this.sendCommandToWorker(workerCommand);
            }
        }
        else {
            System.out.println("ROUTING RESULT");
            System.out.println(workerMessage);
            workerMessage = this.updateHeaderWorkerReceiveMessage(workerMessage);
            this.sendResultToAggregator(workerMessage);
        }

        workerMessage.destroy();
    }

    private ZMsg getCommandByWorkerCommands(String command) {
        String[] commands = command.split(";");

        Map<String, ZMsg> zmsgList = Arrays.stream(commands)
                .collect(Collectors.toMap(o -> o, r -> this.commandZMsgLinkedList.get(r).getLast()));

        String commandMax = null;
        int timestampMax = -1;
        int timestampCurrent = -1;
        for (Map.Entry<String, ZMsg> entry : zmsgList.entrySet()) {
            System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
            if(timestampMax == -1) {
                timestampMax = Integer.valueOf(entry.getValue().toArray()[8].toString());
                commandMax = entry.getKey();
            }
            else {
                timestampCurrent = Integer.valueOf(entry.getValue().toArray()[8].toString());
                if(timestampCurrent > timestampMax) {
                    timestampMax = timestampCurrent;
                    commandMax = entry.getKey();
                }
            }
        }
        return this.commandZMsgLinkedList.get(commandMax).poll();
    }

    private ZMsg updateIdentityWorkerReadyMessage(ZMsg command, String worker) {
        //TODO REVOIR
        String aggregatorSource = command.popString();
        String connectorSource = command.popString();
        String workerSource = command.popString();
        String aggregatorTarget = command.popString();
        String connectorTarget = command.popString();

        command.addFirst(worker);
        command.addFirst(connectorTarget);
        command.addFirst(aggregatorTarget);
        command.addFirst(workerSource);
        command.addFirst(connectorSource);
        command.addFirst(aggregatorSource);
        return command;
    }

    private ZMsg updateHeaderWorkerReceiveReadyMessage(ZMsg command, String worker) {
        command.addFirst(worker);
        return command;
    }

    private ZMsg updateHeaderWorkerReceiveMessage(ZMsg response) {
        response.removeFirst();
        return response;
    }

    private LinkedList<ZMsg> getCommandZMsgLinkedList(String command) {
        if(!this.commandZMsgLinkedList.containsKey(command)) {
            this.commandZMsgLinkedList.put(command, new LinkedList<>());
        }
        return this.commandZMsgLinkedList.get(command);
    }

    private void sendCommandToWorker(ZMsg command) {
        System.out.println("SEND CMD WORKER");
        System.out.println(command);
        command.send(this.backEndReceiveRoutingConnector);
    }

    private void sendResultToWorker(ZMsg result) {
        System.out.println("SEND RESULT WORKER");
        System.out.println(result);
        result.send(this.backEndSendRoutingConnector);
    }

    private void sendResultToAggregator(ZMsg result) {
        System.out.println("SEND RESULT AGGREGATOR");
        System.out.println(result);
        result.send(this.frontEndReceiveRoutingConnector);
    }

    private void sendCommandToAggregator(ZMsg command) {
        System.out.println("SEND CMD AGGREGATOR");
        System.out.println(command);
        command.send(this.frontEndSendRoutingConnector);
    }
}
