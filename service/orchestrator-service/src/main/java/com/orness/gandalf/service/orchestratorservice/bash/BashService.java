package com.orness.gandalf.service.orchestratorservice.bash;

import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;

import static com.orness.gandalf.core.module.constantmodule.bash.BashConstant.*;

@Service
public class BashService {

    public boolean execute(String service, String command) {
        Process process;
        try {
            process = new ProcessBuilder( SCRIPT_COMMAND_FILE, command, service).directory(new File(SCRIPT_RESSOURCES_FILE + "/")).start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        return process.exitValue() == 0 ? true : false;
    }

    public boolean register(String service, String version) {
        Process process;
        try {
            process = new ProcessBuilder(ResourceUtils.getFile(SCRIPT_RESSOURCES_FILE + "/./" + SCRIPT_REGISTER_FILE).getPath(), service, version).start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        return process.exitValue() == 0 ? true : false;
    }

    public boolean downloadProject(String projectName, String version) {
        Process process;
        try {
            process = new ProcessBuilder("bash", "-c", "wget artifact-service.service.gandalf:10000/download/" + projectName + "_" + version + ".tar.gz").directory(new File(SCRIPT_BUILD_DIRECTORY)).start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        return process.exitValue() == 0 ? true : false;
    }

    public boolean downloadConf(String projectName, String version) {
        Process process;
        try {
            process = new ProcessBuilder("bash", "-c", "wget artifact-service.service.gandalf:10000/download/" + projectName + "_" + version + ".ini").directory(new File(SCRIPT_BUILD_DIRECTORY)).start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        return process.exitValue() == 0 ? true : false;
    }
}
