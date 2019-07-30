package com.orness.gandalf.core.module.versioncontrolmodule.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/versioncontrol")
public abstract class VersionControlCommonController {

    @RequestMapping(method = RequestMethod.GET, value = "/clone/{url}")
    public abstract void cloneProject(String url);

    @RequestMapping(method = RequestMethod.GET, value = "/pull/{url}")
    public abstract void pull(String origin, String branch);

    @RequestMapping(method = RequestMethod.GET, value = "/add/{path}")
    public abstract void add(String path);

    @RequestMapping(method = RequestMethod.GET, value = "/commit/{message}")
    public abstract void commit(String message);

    @RequestMapping(method = RequestMethod.GET, value = "/push/{origin}/{branch}")
    public abstract void push(String origin, String branch);

    @RequestMapping(method = RequestMethod.GET, value = "/merge/{source}/{destination}")
    public abstract void merge(String source, String destination);
}
