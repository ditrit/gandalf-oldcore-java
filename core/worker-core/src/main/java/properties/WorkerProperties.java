package properties;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.zeromq.ZMsg;
import service.WorkerClientService;

public class WorkerProperties {

    private WorkerClientService workerClientService;
    private Gson mapper;

    @Value("${worker.name}")
    private String workerName;

    @Value("${worker.service.endpoint}")
    private String workerServiceConnection;
    private String workerCommandFrontEndReceiveConnection;
    private String workerEventFrontEndReceiveConnection;

    @Autowired
    public WorkerProperties(WorkerClientService workerClientService) {
        this.workerClientService = workerClientService;
        this.mapper = new Gson();
        this.initProperties();
    }

    private void initProperties() {

        ZMsg response = this.workerClientService.sendRequest("configuration");
        Object[] responseConnections =  response.toArray();

        this.workerCommandFrontEndReceiveConnection = responseConnections[0].toString();
        this.workerEventFrontEndReceiveConnection = responseConnections[1].toString();
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public String getWorkerCommandFrontEndReceiveConnection() {
        return workerCommandFrontEndReceiveConnection;
    }

    public void setWorkerCommandFrontEndReceiveConnection(String workerCommandFrontEndReceiveConnection) {
        this.workerCommandFrontEndReceiveConnection = workerCommandFrontEndReceiveConnection;
    }

    public String getWorkerEventFrontEndReceiveConnection() {
        return workerEventFrontEndReceiveConnection;
    }

    public void setWorkerEventFrontEndReceiveConnection(String workerEventFrontEndReceiveConnection) {
        this.workerEventFrontEndReceiveConnection = workerEventFrontEndReceiveConnection;
    }

    public String getWorkerServiceConnection() {
        return workerServiceConnection;
    }

    public void setWorkerServiceConnection(String workerServiceConnection) {
        this.workerServiceConnection = workerServiceConnection;
    }
}
