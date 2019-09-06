package com.orness.gandalf.core.module.h2module.normative.manager;

import com.orness.gandalf.core.module.databasemodule.manager.ConnectorDatabaseNormativeManager;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value = "normativeManager")
@Profile(value = "h2")
public class ConnectorH2NormativeManager extends ConnectorDatabaseNormativeManager {

    @Override
    public List list(String payload) {
        return null;
    }

    @Override
    public Object select(String payload) {
        return null;
    }

    @Override
    public void insert(String payload) {

    }

    @Override
    public void update(String payload) {

    }

    @Override
    public void delete(String payload) {

    }
}
