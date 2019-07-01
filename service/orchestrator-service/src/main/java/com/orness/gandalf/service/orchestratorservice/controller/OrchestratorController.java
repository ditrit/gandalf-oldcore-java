package com.orness.gandalf.service.orchestratorservice.controller;

import com.orness.gandalf.service.orchestratorservice.bash.BashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrchestratorController {

    private BashService bashService;

    @Autowired
    public OrchestratorController(BashService bashService) {
        this.bashService = bashService;
    }

    @RequestMapping("/orchestrator/register/{service}/{version}")
    public void register(@PathVariable("service") String service, @PathVariable("version") String version) { this.bashService.execute(service, version); }

    @RequestMapping("/orchestrator/deploy/{service}")
    public void deploy(@PathVariable("service") String service) {
        this.bashService.execute(service, "deploy");
    }

    @RequestMapping("/orchestrator/undeploy/{service}")
    public void undeploy(@PathVariable("service") String service) {
        this.bashService.execute(service, "undeploy");
    }

    @RequestMapping("/orchestrator/stop/{service}")
    public void stop(@PathVariable("service") String service) {
        this.bashService.execute(service, "stop");
    }

    @RequestMapping("/orchestrator/start/{service}")
    public void start(@PathVariable("service") String service) {
        this.bashService.execute(service, "start");
    }

    @RequestMapping("/orchestrator/scale_down/{service}")
    public void scaleDown(@PathVariable("service") String service) {
        this.bashService.execute(service, "scale_down");
    }

    @RequestMapping("/orchestrator/scale_up/{service}")
    public void scaleUp(@PathVariable("service") String service) {
        this.bashService.execute(service, "scale_up");
    }
}
