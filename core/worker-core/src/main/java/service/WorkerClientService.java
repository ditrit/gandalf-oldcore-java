package service;

import com.ditrit.gandalf.core.zeromqcore.service.client.RunnableClientServiceZeroMQ;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zeromq.ZMsg;
import properties.WorkerProperties;

@Component(value = "workerClientService")
@Scope("singleton")
public class WorkerClientService extends RunnableClientServiceZeroMQ {

    private WorkerProperties workerProperties;

    public WorkerClientService(WorkerProperties workerProperties) {
        this.workerProperties = workerProperties;
        this.initRunnable(workerProperties.getWorkerName(), workerProperties.getWorkerServiceConnection() );
    }

    @Override
    public ZMsg sendRequest(Object request) {
        this.serviceClient.send(request.toString());
        return this.getRequestResult();
    }
}
