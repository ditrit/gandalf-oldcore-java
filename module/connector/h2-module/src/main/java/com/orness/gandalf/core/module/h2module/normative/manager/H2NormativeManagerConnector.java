package com.orness.gandalf.core.module.h2module.normative.manager;

import com.orness.gandalf.core.module.databasemodule.manager.ConnectorDatabaseNormativeManager;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value = "commonManager")
@Profile(value = "h2-module")
public class H2NormativeManagerConnector extends ConnectorDatabaseNormativeManager {

    @Override
    public List list(Class classObject) {
        return this.getClassJpaRepositoryHashMap().get(classObject).findAll();
    }

    @Override
    public Object select(Class classObject, Long id) {
        return this.getClassJpaRepositoryHashMap().get(classObject).getOne(id);
    }

    @Override
    public void insert(Class classObject, Object object) {
        this.getClassJpaRepositoryHashMap().get(classObject).save(object);
    }

    @Override
    public void update(Class classObject, Long id, Object object) {
        this.getClassJpaRepositoryHashMap().get(classObject).save(object);
    }

    @Override
    public void delete(Class classObject, Long id) {
        this.getClassJpaRepositoryHashMap().get(classObject).delete(id);
    }
}
