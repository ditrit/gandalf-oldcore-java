package com.orness.gandalf.core.module.h2module.normative.manager;

import com.orness.gandalf.core.module.databasemodule.manager.ConnectorDatabaseNormativeManager;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value = "commonManager")
@Profile(value = "h2-module")
public class ConnectorH2NormativeManager extends ConnectorDatabaseNormativeManager {

    @Override
    public List list(Class classObject) {
        return null;
    }

    @Override
    public Object select(Class classObject, Long id) {
        return null;
    }

    @Override
    public void insert(Class classObject, Object object) {

    }

    @Override
    public void update(Class classObject, Long id, Object object) {

    }

    @Override
    public void delete(Class classObject, Long id) {

    }
}
