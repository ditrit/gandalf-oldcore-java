package worker;

import com.ditrit.gandalf.core.zeromqcore.constant.Constant;
import com.ditrit.gandalf.core.zeromqcore.worker.RunnableWorkerZeroMQ;
import com.ditrit.gandalf.core.zeromqcore.worker.domain.Function;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zeromq.ZMsg;
import properties.WorkerProperties;

@Component(value = "worker")
@Scope("singleton")
public class Worker extends RunnableWorkerZeroMQ {

    private WorkerProperties workerProperties;

    public Worker(WorkerProperties workerProperties) {
        this.workerProperties = workerProperties;
        this.initRunnable(this.workerProperties.getWorkerName(), this.workerProperties.getWorkerCommandFrontEndReceiveConnection(), this.workerProperties.getWorkerEventFrontEndReceiveConnection(), null);
    }

    @Override
    protected Constant.Result executeWorkerCommandFunction(ZMsg commandExecute) {
        Function functionExecute = this.getFunctionByCommand(commandExecute);
        if(functionExecute != null) {
            return functionExecute.executeCommand(commandExecute);
        }
        return Constant.Result.FAIL;
    }

    @Override
    protected void executeWorkerEventFunction(ZMsg commandExecute) {
        Function functionExecute = this.getFunctionByEvent(commandExecute);
        if(functionExecute != null) {
            functionExecute.executeEvent(commandExecute);
        }
    }
}
