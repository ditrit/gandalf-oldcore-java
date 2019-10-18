package properties;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
        this.workerClientService.sendRequest("configuration");
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
