package com.ditrit.gandalf.modules.abstractconnectors.abstractdatabase.manager;

import java.util.List;

public abstract class ConnectorDatabaseStandardManager {

    public abstract List list(String payload);

    public abstract Object select(String payload);

    public abstract void insert(String payload);

    public abstract void update(String payload);

    public abstract void delete(String payload);
}
