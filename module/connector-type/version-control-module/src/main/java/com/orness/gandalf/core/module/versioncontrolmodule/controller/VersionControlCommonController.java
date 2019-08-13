package com.orness.gandalf.core.module.versioncontrolmodule.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static com.orness.gandalf.core.module.versioncontrolmodule.constant.VersionControlConstant.*;

@RequestMapping(value = URL_CONTROLLER)
public abstract class VersionControlCommonController {

    @RequestMapping(method = RequestMethod.GET, value = URL_CONTROLLER_CLONE)
    public abstract void cloneProject(String url);

    @RequestMapping(method = RequestMethod.GET, value = URL_CONTROLLER_PULL)
    public abstract void pull(String origin, String branch);

    @RequestMapping(method = RequestMethod.GET, value = URL_CONTROLLER_ADD)
    public abstract void add(String path);

    @RequestMapping(method = RequestMethod.GET, value = URL_CONTROLLER_COMMIT)
    public abstract void commit(String message);

    @RequestMapping(method = RequestMethod.GET, value = URL_CONTROLLER_PUSH)
    public abstract void push(String origin, String branch);

    @RequestMapping(method = RequestMethod.GET, value = URL_CONTROLLER_MERGE)
    public abstract void merge(String source, String destination);
}
