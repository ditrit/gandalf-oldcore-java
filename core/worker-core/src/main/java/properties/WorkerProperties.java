package properties;

public class WorkerProperties {

    private String workerName;
    private String workerCommandFrontEndReceiveConnection;
    private String workerEventFrontEndReceiveConnection;
    private String workerServiceConnection;

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
