package com.orness.gandalf.core.module.databasemodule.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static com.orness.gandalf.core.module.databasemodule.constant.DatabaseConstant.*;

@RequestMapping(value = URL_CONTROLLER)
public abstract class DatabaseCommonController {

    @RequestMapping(method = RequestMethod.POST, value = URL_CONTROLLER_SELECT)
    public abstract void select(Object object);

    @RequestMapping(method = RequestMethod.POST, value = URL_CONTROLLER_INSERT)
    public abstract void insert(Object object);

    @RequestMapping(method = RequestMethod.POST, value = URL_CONTROLLER_UPDATE)
    public abstract void update(Object object);

    @RequestMapping(method = RequestMethod.POST, value = URL_CONTROLLER_DELETE)
    public abstract void delete(Object object);
}
