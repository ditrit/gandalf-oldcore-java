package com.orness.gandalf.core.module.h2module.normative.controller;

import com.orness.gandalf.core.module.databasemodule.controller.ConnectorDatabaseNormativeController;
import com.orness.gandalf.core.module.h2module.normative.manager.ConnectorH2NormativeManager;
import com.orness.gandalf.core.module.h2module.properties.ConnectorH2Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "normativeController")
@ConditionalOnBean(ConnectorH2Properties.class)
public class ConnectorH2NormativeController extends ConnectorDatabaseNormativeController {

    private ConnectorH2NormativeManager h2CommonManager;

    @Autowired
    public ConnectorH2NormativeController(ConnectorH2NormativeManager h2CommonManager) {
        this.h2CommonManager = h2CommonManager;
    }

    @Override
    public void command(String command) {

    }

    @Override
    public void event(String event) {

    }
}
