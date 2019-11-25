package com.ditrit.gandalf.gandalfjava.core.connectorcore.connector;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Scope("singleton")
public class ConnectorWorkerMap {

    private Map<String, List<String>> workerCommandsMap;
    private Map<String, String> workerCommandSendFileMap;

    public ConnectorWorkerMap() {
        this.workerCommandsMap = new HashMap<>();
        this.workerCommandSendFileMap = new HashMap<>();
    }

    public List<String> getWorkerCommands(String worker) {
        return this.workerCommandsMap.get(worker);
    }

    public void addWorkerCommands(String worker, String command) {
        if(!this.workerCommandsMap.get(worker).contains(command)) {
            this.workerCommandsMap.get(worker).add(command);
        }
    }

    public String getWorkerCommandSendFile(String worker) {
        return this.workerCommandSendFileMap.get(worker);
    }

    public void addWorkerCommandSendFile(String worker, String commandSendFile) {
        if(!this.workerCommandSendFileMap.get(worker).contains(commandSendFile)) {
            this.workerCommandSendFileMap.put(worker, commandSendFile);
        }
    }
}
