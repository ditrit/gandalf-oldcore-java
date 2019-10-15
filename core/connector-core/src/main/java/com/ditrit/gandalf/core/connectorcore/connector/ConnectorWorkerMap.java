package com.ditrit.gandalf.core.connectorcore.connector;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ConnectorWorkerMap {

    private Map<String, List<String>> serviceClassWorkersMap;

    public ConnectorWorkerMap() {
        this.serviceClassWorkersMap = new HashMap<>();
    }

    public List<String> getWorkersByServiceClass(String serviceClass) {
        return this.serviceClassWorkersMap.get(serviceClass);
    }

    public void addWorkerByServiceClass(String serviceClass, String worker) {
        if(!this.serviceClassWorkersMap.get(serviceClass).contains(worker)) {
            this.serviceClassWorkersMap.get(serviceClass).add(worker);
        }
    }
}
