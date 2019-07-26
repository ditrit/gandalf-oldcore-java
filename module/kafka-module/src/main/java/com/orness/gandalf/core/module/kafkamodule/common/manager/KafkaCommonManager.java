package com.orness.gandalf.core.module.kafkamodule.common.manager;

import com.orness.gandalf.core.module.busmodule.common.manager.BusCommonManager;
import org.springframework.stereotype.Component;

@Component
public class KafkaCommonManager extends BusCommonManager {

    @Override
    public void createTopic() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deleteTopic() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void sendTopic() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void receiveTopic() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
