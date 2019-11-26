package com.ditrit.gandalf.gandalfjava.core.workercore.worker;

import com.ditrit.gandalf.gandalfjava.core.workercore.function.WorkerFunctionsService;
import com.ditrit.gandalf.gandalfjava.core.workercore.loader.WorkerJarFileLoaderService;
import com.ditrit.gandalf.gandalfjava.core.zeromqcore.constant.Constant;
import com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.RunnableWorkerZeroMQ;
import com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.domain.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;
import com.ditrit.gandalf.gandalfjava.core.workercore.properties.WorkerProperties;

@Component(value = "com/ditrit/gandalf/gandalfjava/functions/functionszeebe/worker")
@Scope("singleton")
public class Worker extends RunnableWorkerZeroMQ {

    private WorkerProperties workerProperties;
    private WorkerFunctionsService workerFunctionsService;
    private WorkerJarFileLoaderService workerJarFileLoaderService;

    @Autowired
    public Worker(WorkerProperties workerProperties, WorkerFunctionsService workerFunctionsService, WorkerJarFileLoaderService workerJarFileLoaderService) {
        this.workerProperties = workerProperties;
        this.workerFunctionsService = workerFunctionsService;
        this.workerJarFileLoaderService = workerJarFileLoaderService;
        this.initRunnable(this.workerProperties.getWorkerName(), this.workerProperties.getWorkerCommandFrontEndReceiveConnection(), this.workerProperties.getWorkerEventFrontEndReceiveConnection(), null);
        this.getConfiguration();
    }

    private void getConfiguration() {
        ZMsg req_configuration = new ZMsg();
        req_configuration.add("CONFIGURATION");
        req_configuration.add(this.workerProperties.getWorkerName());
        req_configuration.add(this.workerProperties.getWorkerType());
        req_configuration.send(this.workerCommandFrontEndReceive);
        req_configuration.destroy();

        ZMsg rep_configuration = null;

        while(rep_configuration == null) {
            rep_configuration = ZMsg.recvMsg(this.workerCommandFrontEndReceive, ZMQ.NOBLOCK);
            boolean more = this.workerCommandFrontEndReceive.hasReceiveMore();

            if(more) {
                break;
            }
        }

        Object[] configurationArray = rep_configuration.toArray();

        // PUB SEND
        this.workerProperties.setWorkerEventFrontEndReceiveConnection(configurationArray[5].toString());
        //TODO
        //REQ AGGREGATOR
        // NOM
        // AGGREGATOR
        // UUID
        // IP
        // MAC
        // TYPE C
        // TYPE P
    }

    @Override
    protected String executeWorkerCommandFunction(ZMsg kcommandExecute) {
        Object[] commandExecuteArray = commandExecute.toArray();
        Function functionExecute = this.workerFunctionsService.getFunctionByCommand(commandExecute);
        String payload = "";
        if(functionExecute != null) {
            payload = functionExecute.executeCommand(commandExecute, this.commandStateManager.getMapUUIDCommandStatesByUUID(commandExecuteArray[13].toString()), this.commandStateManager.getMapUUIDStateByUUID(commandExecuteArray[13].toString()));
        }
        return payload;
    }

    @Override
    protected void executeWorkerEventFunction(ZMsg commandExecute) {
        Function functionExecute = this.workerFunctionsService.getFunctionByEvent(commandExecute);
        if(functionExecute != null) {
            functionExecute.executeEvent(commandExecute);
        }
    }

    @Override
    protected void sendReadyCommand() {
        ZMsg ready = new ZMsg();
        ready.add(Constant.COMMAND_READY);
        ready.add(this.workerFunctionsService.getCommands().keySet().toString());
        ready.send(this.workerCommandFrontEndReceive);
        ready.destroy();
    }
}
