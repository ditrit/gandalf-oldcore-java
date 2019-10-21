package com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodenexus.standard.manager.feign;

import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodenexus.properties.ConnectorNexusProperties;
import org.sonatype.nexus.rest.model.RepositoryListResource;
import org.sonatype.nexus.rest.model.ArtifactResolveResource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "${${instance.name}.connectors.${connector.type}.${connector.name}.feignName}", url = "${${instance.name}.connectors.${connector.type}.${connector.name}.endPointConnection}")
@ConditionalOnBean(ConnectorNexusProperties.class)
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
