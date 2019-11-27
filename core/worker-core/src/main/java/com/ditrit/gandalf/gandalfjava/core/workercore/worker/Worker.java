package com.ditrit.gandalf.gandalfjava.core.workercore.worker;

import com.ditrit.gandalf.gandalfjava.core.workercore.function.WorkerFunctionsService;
import com.ditrit.gandalf.gandalfjava.core.workercore.loader.WorkerJarFileLoaderService;
import com.ditrit.gandalf.gandalfjava.core.zeromqcore.constant.Constant;
import com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.RunnableWorkerZeroMQ;
import com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.domain.CommandFunction;
import com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.domain.EventFunction;
import com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.domain.ThreadFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;
import com.ditrit.gandalf.gandalfjava.core.workercore.properties.WorkerProperties;

import java.util.List;

import static com.ditrit.gandalf.gandalfjava.core.zeromqcore.constant.Constant.*;

@Component(value = "worker")
@Scope("singleton")
public class Worker extends RunnableWorkerZeroMQ {

    private WorkerProperties workerProperties;
    private WorkerFunctionsService workerFunctionsService;
    private WorkerJarFileLoaderService workerJarFileLoaderService;
    private String jarPath;

    @Autowired
    public Worker(WorkerProperties workerProperties, WorkerFunctionsService workerFunctionsService, WorkerJarFileLoaderService workerJarFileLoaderService) {
        this.workerProperties = workerProperties;
        this.workerFunctionsService = workerFunctionsService;
        this.workerJarFileLoaderService = workerJarFileLoaderService;
        this.initRunnable(this.workerProperties.getWorkerName(), this.workerProperties.getWorkerCommandFrontEndReceiveConnection(), this.workerProperties.getWorkerEventFrontEndReceiveConnection(), null);

        //FUNCTIONS
        this.loadFunctions();

        //CONFIGURATIONS
        this.getConfiguration();
    }

    private void loadFunctions() {
        //PATH
        this.jarPath = new StringBuilder(ROOT_PATH).append(this.workerProperties.getConnectorName()).append(WORKERS_PATH).append(this.workerProperties.getWorkerName()).append(FUNCTIONS_PATH).toString();

        //FUNCTIONS
        List<ThreadFunction> threadFunctions = this.workerJarFileLoaderService.startFunctionsByJar(jarPath);
        for (ThreadFunction threadFunction : threadFunctions) {
            if(threadFunction instanceof CommandFunction) {
                CommandFunction currentCommandFunction = (CommandFunction) threadFunction;
                this.workerFunctionsService.addFunctionCommand(currentCommandFunction.getName(), currentCommandFunction);
            }
            else if(threadFunction instanceof EventFunction) {
                EventFunction currentEventFunction = (EventFunction) threadFunction;
                this.workerFunctionsService.addFunctionEvent(currentEventFunction.getName(), currentEventFunction);
            }
            else {
                System.out.println("Wrong function");
            }
        }
    }

    private String createWorkerPath() {
        return new StringBuilder(ROOT_PATH).append(this.workerProperties.getConnectorName()).append(WORKERS_PATH).append(this.workerProperties.getWorkerName()).append(FUNCTIONS_PATH).toString();
    }

    private void getConfiguration() {
        ZMsg req_configuration = new ZMsg();
        req_configuration.add("CONFIGURATION");
        req_configuration.add(this.workerProperties.getWorkerName());
        req_configuration.add(this.workerProperties.getWorkerType());
        req_configuration.add(this.workerFunctionsService.getCommands().toString());
        req_configuration.add(this.workerFunctionsService.getEvents().toString());
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
    protected String executeWorkerCommandFunction(ZMsg commandExecute) {
        Object[] commandExecuteArray = commandExecute.toArray();
        CommandFunction functionExecute = this.workerFunctionsService.getFunctionByCommand(commandExecute);
        String payload = "";
        if(functionExecute != null) {
            payload = functionExecute.executeCommand(commandExecute, this.commandStateManager.getMapUUIDCommandStatesByUUID(commandExecuteArray[13].toString()), this.commandStateManager.getMapUUIDStateByUUID(commandExecuteArray[13].toString()));
        }
        return payload;
    }

    @Override
    protected void executeWorkerEventFunction(ZMsg commandExecute) {
        EventFunction functionExecute = this.workerFunctionsService.getFunctionByEvent(commandExecute);
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
