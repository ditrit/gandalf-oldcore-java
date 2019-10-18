package worker;

import com.ditrit.gandalf.core.zeromqcore.constant.Constant;
import com.ditrit.gandalf.core.zeromqcore.worker.RunnableWorkerZeroMQ;
import com.ditrit.gandalf.core.zeromqcore.worker.domain.Function;
import function.WorkerFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
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
    }

    @Override
    protected Constant.Result executeWorkerCommandFunction(ZMsg commandExecute) {
        Function functionExecute = this.workerFunctions.getFunctionByCommand(commandExecute);
        if(functionExecute != null) {
            return functionExecute.executeCommand(commandExecute);
        }
        return Constant.Result.FAIL;
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
