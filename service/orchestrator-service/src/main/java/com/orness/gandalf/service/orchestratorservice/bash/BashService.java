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
            process = new ProcessBuilder(ResourceUtils.getFile(SCRIPT_RESSOURCES_FILE + "/./" + SCRIPT_COMMAND_FILE).getPath(), command, service).start();
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
}
