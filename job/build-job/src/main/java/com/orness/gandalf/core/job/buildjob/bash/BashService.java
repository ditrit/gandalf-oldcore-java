package com.orness.gandalf.core.job.buildjob.bash;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

import static com.orness.gandalf.core.module.constantmodule.bash.BashConstant.*;

@Service
public class BashService {

    public boolean cloneProject(String url) {
        //Process process_cd;
        Process process;
        try {
            //process_cd = new ProcessBuilder("bash", "-c", "cd " + SCRIPT_BUILD_DIRECTORY + "/").start();
            process = new ProcessBuilder( "bash", "-c", SCRIPT_CLONE + " " + url).directory(new File(SCRIPT_BUILD_DIRECTORY + "/")).start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        return process.exitValue() == 0 ? true : false;
    }

    public boolean buildProject(String projectName) {
        Process process;
        try {
            process = new ProcessBuilder("bash", "-c", SCRIPT_BUILD).directory(new File(SCRIPT_BUILD_DIRECTORY + "/" + projectName + "/")).start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        return process.exitValue() == 0 ? true : false;
    }
}
