package com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodeh2.standard.controller;

import com.ditrit.gandalf.modules.abstractconnectors.abstractdatabase.controller.ConnectorDatabaseStandardController;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodeh2.standard.manager.ConnectorH2StandardManager;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodeh2.properties.ConnectorH2Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "standardController")
@ConditionalOnBean(ConnectorH2Properties.class)
public class ConnectorH2StandardController extends ConnectorDatabaseStandardController {

    private ConnectorH2StandardManager h2CommonManager;

    @Autowired
    public ConnectorH2StandardController(ConnectorH2StandardManager h2CommonManager) {
        this.h2CommonManager = h2CommonManager;
    }

    @Override
    public void command(String command) {

    }

    @Override
    public void event(String event) {

    }
}
