package com.orness.gandalf.core.module.databasemodule.manager;

import java.util.List;

public abstract class ConnectorDatabaseNormativeManager {

    public abstract List list(String payload);

    public abstract Object select(String payload);

    public abstract void insert(String payload);

    public abstract void update(String payload);

    public abstract void delete(String payload);
}
