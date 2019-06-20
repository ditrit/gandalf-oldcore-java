package com.orness.gandalf.core.job.buildjob.bash;

import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.orness.gandalf.core.module.constantmodule.bash.BashConstant.*;

@Service
public class BashService {

    public boolean cloneProject(String url) {
        Process process;
        try {
            process = new ProcessBuilder(SCRIPT_CLONE + url).start();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return process.exitValue() == 0 ? true : false;
    }

    public boolean buildProject(String path) {
        Process process;
        try {
            process = new ProcessBuilder(SCRIPT_BUILD_DIRECTORY + path + SCRIPT_BUILD).start();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return process.exitValue() == 0 ? true : false;
    }
}
