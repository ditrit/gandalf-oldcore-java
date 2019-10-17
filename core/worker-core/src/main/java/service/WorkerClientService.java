package service;

import com.ditrit.gandalf.core.zeromqcore.service.client.RunnableClientServiceZeroMQ;
import org.zeromq.ZMsg;
import properties.WorkerProperties;

public class WorkerClientService extends RunnableClientServiceZeroMQ {

    private WorkerProperties workerProperties;

    public WorkerClientService(WorkerProperties workerProperties) {
        this.workerProperties = workerProperties;
        this.initRunnable(workerProperties.getWorkerName(), workerProperties.getWorkerServiceConnection() );
    }

    @Override
    public ZMsg sendRequest(Object request) {
        return null;
    }
}
