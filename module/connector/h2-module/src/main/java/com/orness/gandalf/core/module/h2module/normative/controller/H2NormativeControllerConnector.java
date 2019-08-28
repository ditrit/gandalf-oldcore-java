package com.orness.gandalf.core.module.h2module.normative.controller;

import com.orness.gandalf.core.module.databasemodule.controller.ConnectorDatabaseNormativeController;
import com.orness.gandalf.core.module.h2module.normative.manager.H2NormativeManagerConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController(value = "commonController")
@Profile(value = "h2-module")
public class H2NormativeControllerConnector extends ConnectorDatabaseNormativeController {

    private H2NormativeManagerConnector h2CommonManager;

    @Autowired
    public H2NormativeControllerConnector(H2NormativeManagerConnector h2CommonManager) {
        this.h2CommonManager = h2CommonManager;
    }

    @Override
    public List list(Class classObject) {
        return this.h2CommonManager.list(classObject);
    }

    @Override
    public Object select(Class classObject, Long id) {
        return this.h2CommonManager.select(classObject, id);
    }

    @Override
    public void insert(Class classObject, Object object) {
        this.h2CommonManager.insert(classObject, object);
    }

    @Override
    public void update(Class classObject, Long id, Object object) {
        this.h2CommonManager.update(classObject, id, object);
    }

    @Override
    public void delete(Class classObject, Long id) {
        this.h2CommonManager.delete(classObject, id);
    }
}
