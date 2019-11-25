package com.ditrit.gandalf.gandalfjava.services.orchestratorservice.controller;

import com.ditrit.gandalf.gandalfjava.services.orchestratorservice.bash.BashService;
import com.ditrit.gandalf.gandalfjava.services.orchestratorservice.domain.UrlDownload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrchestratorServiceController {

    private BashService bashService;

    @Autowired
    public OrchestratorServiceController(BashService bashService) {
        this.bashService = bashService;
    }


    @RequestMapping(method = RequestMethod.POST, value = "/orchestrator-service/download")
    public boolean download(@RequestBody UrlDownload urlDownload) {
        boolean result = true;
        System.out.println(urlDownload.getConf_url());
        System.out.println(urlDownload.getProject_url());
        System.out.println("DOWNLOAD");
        result &= this.bashService.downloadProject(urlDownload.getProject_url());
        result &= this.bashService.downloadConfiguration(urlDownload.getConf_url());
        return result;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/orchestrator-service/register/{service}/{version}")
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

    @RequestMapping(method = RequestMethod.GET, value = "/orchestrator-service/unregister/{service}/{version}")
    public boolean unregister(@PathVariable("service") String service, @PathVariable("version") String version) {
        System.out.println(service);
        System.out.println(version);
        System.out.println("UNREGISTER");
        return this.bashService.unregister(service, version);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/orchestrator-service/deploy/{service}")
    public boolean deploy(@PathVariable("service") String service) {
        System.out.println(service);
        return this.bashService.execute(service, "deploy");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/orchestrator-service/undeploy/{service}")
    public boolean undeploy(@PathVariable("service") String service) {
        System.out.println(service);
        return this.bashService.execute(service, "undeploy");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/orchestrator-service/stop/{service}")
    public boolean stop(@PathVariable("service") String service) {
        System.out.println(service);
        return this.bashService.execute(service, "stop");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/orchestrator-service/start/{service}")
    public boolean start(@PathVariable("service") String service) {
        System.out.println(service);
        return this.bashService.execute(service, "start");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/orchestrator-service/scale_down/{service}")
    public boolean scaleDown(@PathVariable("service") String service) {
        return this.bashService.execute(service, "scale_down");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/orchestrator-service/scale_up/{service}")
    public boolean scaleUp(@PathVariable("service") String service) {
        System.out.println(service);
        return this.bashService.execute(service, "scale_up");
    }
}
