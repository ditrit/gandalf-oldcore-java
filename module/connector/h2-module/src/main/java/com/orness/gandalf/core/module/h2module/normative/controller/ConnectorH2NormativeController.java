package com.orness.gandalf.core.module.h2module.normative.controller;

import com.orness.gandalf.core.module.databasemodule.controller.ConnectorDatabaseNormativeController;
import com.orness.gandalf.core.module.h2module.normative.manager.ConnectorH2NormativeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController(value = "commonController")
@Profile(value = "h2-module")
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
