package com.orness.gandalf.core.module.customorchestratormodule.normative.manager;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(url = "${${instance.name}.connectors.${connector.type}.${connector.name}.target.endpoint.uri}")
@Profile(value = "custom-orchestrator")
public interface OrchestratorServiceFeign {

    @RequestMapping(method = RequestMethod.GET, value = "/orchestrator/download/{urlProject}/{urlConf}")
    boolean download(@PathVariable("urlProject") String urlProject, @PathVariable("urlConf") String urlConf);

    @RequestMapping(method = RequestMethod.GET, value = "/orchestrator/register/{service}/{version}")
    boolean register(@PathVariable("service") String service, @PathVariable("version") String version);

    @RequestMapping(method = RequestMethod.GET, value = "/orchestrator/register/{service}/{version}")
    boolean unregister(@PathVariable("service") String service, @PathVariable("version") String version);

    @RequestMapping(method = RequestMethod.GET, value = "/orchestrator/deploy/{service}")
    boolean deploy(@PathVariable("service") String service);

    @RequestMapping(method = RequestMethod.GET, value = "/orchestrator/undeploy/{service}")
    boolean undeploy(@PathVariable("service") String service);

    @RequestMapping(method = RequestMethod.GET, value = "/orchestrator/stop/{service}")
    boolean stop(@PathVariable("service") String service);

    @RequestMapping(method = RequestMethod.GET, value = "/orchestrator/start/{service}")
    boolean start(@PathVariable("service") String service);

    @RequestMapping(method = RequestMethod.GET, value = "/orchestrator/scale_down/{service}")
    boolean scaleDown(@PathVariable("service") String service);

    @RequestMapping(method = RequestMethod.GET, value = "/orchestrator/scale_up/{service}")
    boolean scaleUp(@PathVariable("service") String service);
}
