package com.orness.gandalf.service.orchestratorservice.bash;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

import static com.orness.gandalf.core.module.constantmodule.bash.BashConstant.*;

@Service
public class BashService {

    public boolean execute(String service, String command) {
        Process process;
        try {
            process = new ProcessBuilder( SCRIPT_DEPLOY_FILE, service, command).directory(new File(SCRIPT_DEPLOY_RESSOURCES_FILE + "/")).start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        return process.exitValue() == 0 ? true : false;
    }

}
