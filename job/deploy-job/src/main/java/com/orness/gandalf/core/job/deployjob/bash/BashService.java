package com.orness.gandalf.core.job.deployjob.bash;

import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.orness.gandalf.core.module.constantmodule.bash.BashConstant.SCRIPT_DEPLOY;
import static com.orness.gandalf.core.module.constantmodule.bash.BashConstant.SCRIPT_DEPLOY_PREFIX_TARGET;

@Service
public class BashService {


    public boolean deployProject(String path, int indice) {
        Process process;
        try {
            process = new ProcessBuilder(SCRIPT_DEPLOY, path+"-"+indice, path, SCRIPT_DEPLOY_PREFIX_TARGET+path+"/target").start();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return process.exitValue() == 0 ? true : false;
    }
}
