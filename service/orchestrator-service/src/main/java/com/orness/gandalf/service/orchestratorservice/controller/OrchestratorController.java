package com.orness.gandalf.service.orchestratorservice.controller;

import com.orness.gandalf.service.orchestratorservice.bash.BashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrchestratorController {

    private BashService bashService;

    @Autowired
    public OrchestratorController(BashService bashService) {
        this.bashService = bashService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/orchestrator/register/{service}/{version}")
    public boolean register(@PathVariable("service") String service, @PathVariable("version") String version) {
        System.out.println(service);
        System.out.println(version);
        this.bashService.downloadProject(service, version);
        this.bashService.downloadConf(service, version);
        this.bashService.untarProject(service, version);
        this.bashService.register(service, version);
        return true;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/orchestrator/deploy/{service}")
    public boolean deploy(@PathVariable("service") String service) {
        System.out.println(service);
        this.bashService.execute(service, "deploy");
        return true;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/orchestrator/undeploy/{service}")
    public boolean undeploy(@PathVariable("service") String service) {
        System.out.println(service);
        this.bashService.execute(service, "undeploy");
        return true;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/orchestrator/stop/{service}")
    public boolean stop(@PathVariable("service") String service) {
        System.out.println(service);
        this.bashService.execute(service, "stop");
        return true;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/orchestrator/start/{service}")
    public boolean start(@PathVariable("service") String service) {
        System.out.println(service);
        this.bashService.execute(service, "start");
        return true;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/orchestrator/scale_down/{service}")
    public boolean scaleDown(@PathVariable("service") String service) {
        this.bashService.execute(service, "scale_down");
        return true;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/orchestrator/scale_up/{service}")
    public boolean scaleUp(@PathVariable("service") String service) {
        System.out.println(service);
        this.bashService.execute(service, "scale_up");
        return true;
    }
}
