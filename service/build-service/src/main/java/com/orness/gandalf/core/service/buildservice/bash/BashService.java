package com.orness.gandalf.core.service.buildservice.bash;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

import static com.orness.gandalf.core.module.constantmodule.bash.BashConstant.*;
import static com.orness.gandalf.core.module.constantmodule.bash.BashConstant.SCRIPT_BUILD_DIRECTORY;

@Service
public class BashService {

    private File build_directory;

    public boolean cloneProject(String url) {
        Process process;
        try {
            build_directory = new File(SCRIPT_BUILD_DIRECTORY + "/");
            build_directory.createNewFile();
            process = new ProcessBuilder( "bash", "-c", SCRIPT_CLONE + " " + url).directory(build_directory).start();
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
            process = new ProcessBuilder("bash", "-c", SCRIPT_BUILD).directory(build_directory).start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        return process.exitValue() == 0 ? true : false;
    }
}
