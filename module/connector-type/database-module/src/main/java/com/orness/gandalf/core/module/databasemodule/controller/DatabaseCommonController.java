package com.orness.gandalf.core.module.databasemodule.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

import static com.orness.gandalf.core.module.databasemodule.constant.DatabaseConstant.*;

@RequestMapping(value = URL_CONTROLLER)
public abstract class DatabaseCommonController {

    @RequestMapping(method = RequestMethod.POST, value = URL_CONTROLLER_LIST)
    public abstract List list(@PathVariable("classObject") Class classObject);

    @RequestMapping(method = RequestMethod.POST, value = URL_CONTROLLER_SELECT)
    public abstract Object select(@PathVariable("classObject") Class classObject, @PathVariable("id") Long id);

    @RequestMapping(method = RequestMethod.POST, value = URL_CONTROLLER_INSERT)
    public abstract void insert(@PathVariable("classObject") Class classObject, @RequestBody Object object);

    @RequestMapping(method = RequestMethod.POST, value = URL_CONTROLLER_UPDATE)
    public abstract void update(@PathVariable("classObject") Class classObject, @PathVariable("id") Long id, @RequestBody Object object);

    @RequestMapping(method = RequestMethod.POST, value = URL_CONTROLLER_DELETE)
    public abstract void delete(@PathVariable("classObject") Class classObject, @PathVariable("id") Long id);
}
