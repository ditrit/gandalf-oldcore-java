package worker;

import com.ditrit.gandalf.core.zeromqcore.constant.Constant;
import com.ditrit.gandalf.core.zeromqcore.worker.RunnableWorkerZeroMQ;
import com.ditrit.gandalf.core.zeromqcore.worker.domain.CommandState;
import com.ditrit.gandalf.core.zeromqcore.worker.domain.CommandStateManager;
import com.ditrit.gandalf.core.zeromqcore.worker.domain.Function;
import function.WorkerFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;
import properties.WorkerProperties;

@Component(value = "worker")
@Scope("singleton")
public class Worker extends RunnableWorkerZeroMQ {

    private WorkerProperties workerProperties;
    private WorkerFunctions workerFunctions;

    @Autowired
    public Worker(WorkerProperties workerProperties, WorkerFunctions workerFunctions) {
        this.workerProperties = workerProperties;
        this.workerFunctions = workerFunctions;
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
    protected String executeWorkerCommandFunction(ZMsg commandExecute) {
        Object[] commandExecuteArray = commandExecute.toArray();
        Function functionExecute = this.workerFunctions.getFunctionByCommand(commandExecute);
        String payload = "";
        if(functionExecute != null) {
            payload = functionExecute.executeCommand(commandExecute, this.commandStateManager.getMapUUIDCommandStatesByUUID(commandExecuteArray[13].toString()), this.commandStateManager.getMapUUIDStateByUUID(commandExecuteArray[13].toString()));
        }
        return payload;
    }

    @Override
    protected void executeWorkerEventFunction(ZMsg commandExecute) {
        Function functionExecute = this.workerFunctions.getFunctionByEvent(commandExecute);
        if(functionExecute != null) {
            functionExecute.executeEvent(commandExecute);
        }
    }

    @Override
    protected void sendReadyCommand() {
        ZMsg ready = new ZMsg();
        ready.add(Constant.COMMAND_READY);
        ready.add(this.workerFunctions.getCommands().keySet().toString());
        ready.send(this.workerCommandFrontEndReceive);
        ready.destroy();
    }
}
