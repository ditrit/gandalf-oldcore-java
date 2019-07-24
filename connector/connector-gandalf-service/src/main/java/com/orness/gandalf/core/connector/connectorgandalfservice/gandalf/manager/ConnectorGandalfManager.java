package com.orness.gandalf.core.connector.connectorgandalfservice.gandalf.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

//TODO FINISH
@Component
public class ConnectorGandalfManager {

    private String gandalfBrokerAddress;

    @Autowired
    public ConnectorGandalfManager(@Value("${gandalf.bus.broker}") String gandalfBrokerAddress) {
        this.gandalfBrokerAddress = gandalfBrokerAddress;
    }

    public void publish() {

    }

    public void subscribe() {

    }

    public void unsubscribe() {

    }
}
