package com.orness.gandalf.core.service.buildstorageservice.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;

@RestController("/builds")
public class BuildStorageRestController {

    @RequestMapping(method = RequestMethod.POST, value = "/upload")
    public void uploadBuild() {

    }

    @RequestMapping(method = RequestMethod.GET, value = "/download/{filename}")
    public void downloadBuild(@PathVariable("filename") String fileName, HttpServletRequest request) {
        
    }

}
