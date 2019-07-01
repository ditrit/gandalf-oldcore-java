package com.orness.gandalf.core.job.deployjob.bash;

import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.orness.gandalf.core.module.constantmodule.bash.BashConstant.*;

@Service
public class BashService {


    public boolean deployProject(String projectName, int indice) {
        Process process;
        try {
            process = new ProcessBuilder(SCRIPT_DEPLOY, projectName+"-"+indice, projectName, SCRIPT_DEPLOY_DIRECTORY+projectName+"/target").start();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return process.exitValue() == 0 ? true : false;
    }
}
