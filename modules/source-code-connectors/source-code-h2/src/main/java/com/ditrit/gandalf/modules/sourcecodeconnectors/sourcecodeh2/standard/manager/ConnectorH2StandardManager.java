package com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodeh2.standard.manager;

import com.ditrit.gandalf.modules.abstractconnectors.abstractdatabase.manager.ConnectorDatabaseStandardManager;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodeh2.properties.ConnectorH2Properties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value = "standardManager")
@ConditionalOnBean(ConnectorH2Properties.class)
public class ConnectorH2StandardManager extends ConnectorDatabaseStandardManager {

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
