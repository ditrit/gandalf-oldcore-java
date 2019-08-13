package com.orness.gandalf.core.module.nexusmodule.common.manager.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.sonatype.nexus.client.core.subsystem.artifact.;

import java.util.List;

//TODO REVOIR POUR OBJECT
@FeignClient(value = "jplaceholder", url = "artifact-service.service.gandalf:10000")
public interface NexusFeign {

    @RequestMapping(method = RequestMethod.GET, value = "/service/rest/v1/repositories")
    List<String> listRepositories();

    @RequestMapping(method = RequestMethod.GET, value = "/service/rest/v1/components")
    List<Component> listComponents();

    @RequestMapping(method = RequestMethod.GET, value = "/service/rest/v1/components/{id}")
    String downloadComponent(@PathVariable("id") String id);

    @RequestMapping(method = RequestMethod.POST, value = "/service/rest/v1/components")
    void uploadComponent(String component);
}
