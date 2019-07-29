package com.orness.gandalf.core.module.gitmodule.config.common.manager;

import com.orness.gandalf.core.module.versioncontrolmodule.common.manager.VersionControlCommonManager;
import org.springframework.stereotype.Component;

@Component
public class GitCommonManager extends VersionControlCommonManager {

    @Override
    public void cloneProject(String url) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void pull(String origin, String branch) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void add(String path) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void commit(String message) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void push(String origin, String branch) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void merge(String source, String destination) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
