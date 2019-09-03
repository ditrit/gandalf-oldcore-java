package com.orness.gandalf.core.module.nexusmodule.normative.manager.feign;

import org.sonatype.nexus.rest.model.RepositoryListResource;
import org.sonatype.nexus.rest.model.ArtifactResolveResource;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "${${instance.name}.${connector.type}.${connector.name}.feignName}", url = "${${instance.name}.${connector.type}.${connector.name}.artifactEndPointConnection}")
public interface NexusFeign {

    @RequestMapping(method = RequestMethod.GET, value = "/service/rest/v1/repositories")
    List<RepositoryListResource> listRepositories();

    @RequestMapping(method = RequestMethod.GET, value = "/service/rest/v1/components")
    List<ArtifactResolveResource> listComponents();

    @RequestMapping(method = RequestMethod.GET, value = "/service/rest/v1/components/{id}")
    ArtifactResolveResource downloadComponent(@PathVariable("id") Long id);

    @RequestMapping(method = RequestMethod.POST, value = "/service/rest/v1/components")
    void uploadComponent(ArtifactResolveResource component);

    @RequestMapping(method = RequestMethod.POST, value = "/service/rest/v1/components/{id}")
    void deleteComponent(@PathVariable("id") Long id);
}
