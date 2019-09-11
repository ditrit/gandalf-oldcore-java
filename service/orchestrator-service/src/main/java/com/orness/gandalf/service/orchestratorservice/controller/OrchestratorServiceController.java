package com.orness.gandalf.service.orchestratorservice.controller;

import com.orness.gandalf.service.orchestratorservice.bash.BashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrchestratorServiceController {

    private BashService bashService;

    @Autowired
    public OrchestratorServiceController(BashService bashService) {
        this.bashService = bashService;
    }


    @RequestMapping(method = RequestMethod.GET, value = "/orchestrator/download/{urlProject}/{urlConf}")
    public boolean download(@PathVariable("urlProject") String urlProject, @PathVariable("urlConf") String urlConf) {
        boolean result = true;
        System.out.println(urlProject);
        System.out.println(urlConf);
        System.out.println("DOWNLOAD");
        result &= this.bashService.downloadProject(urlProject);
        result &= this.bashService.downloadConfiguration(urlConf);
        return result;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/orchestrator/register/{service}/{version}")
    public boolean register(@PathVariable("service") String service, @PathVariable("version") String version) {
        System.out.println(service);
        System.out.println(version);
/*        System.out.println("DOWNLOAD PROJECT");
        this.bashService.downloadProject(service, version);
        System.out.println("DOWNLOAD CONF");
        this.bashService.downloadConf(service, version);
        System.out.println("UNTAR");
        this.bashService.untarProject(service, version);*/
        System.out.println("REGISTER");
        return this.bashService.register(service, version);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/orchestrator/register/{service}/{version}")
    public boolean unregister(@PathVariable("service") String service, @PathVariable("version") String version) {
        System.out.println(service);
        System.out.println(version);
        System.out.println("UNREGISTER");
        return this.bashService.unregister(service, version);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/orchestrator/deploy/{service}")
    public boolean deploy(@PathVariable("service") String service) {
        System.out.println(service);
        return this.bashService.execute(service, "deploy");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/orchestrator/undeploy/{service}")
    public boolean undeploy(@PathVariable("service") String service) {
        System.out.println(service);
        return this.bashService.execute(service, "undeploy");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/orchestrator/stop/{service}")
    public boolean stop(@PathVariable("service") String service) {
        System.out.println(service);
        return this.bashService.execute(service, "stop");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/orchestrator/start/{service}")
    public boolean start(@PathVariable("service") String service) {
        System.out.println(service);
        return this.bashService.execute(service, "start");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/orchestrator/scale_down/{service}")
    public boolean scaleDown(@PathVariable("service") String service) {
        return this.bashService.execute(service, "scale_down");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/orchestrator/scale_up/{service}")
    public boolean scaleUp(@PathVariable("service") String service) {
        System.out.println(service);
        return this.bashService.execute(service, "scale_up");
    }
}
