package com.orness.gandalf.core.module.databasemodule.manager;

import java.util.List;

public abstract class ConnectorDatabaseNormativeManager {

    public abstract List list(Class classObject);

    public abstract Object select(Class classObject, Long id);

    public abstract void insert(Class classObject, Object object);

    public abstract void update(Class classObject, Long id, Object object);

    public abstract void delete(Class classObject, Long id);
}
