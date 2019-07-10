package com.orness.gandalf.core.service.buildservice.bash;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.orness.gandalf.core.module.constantmodule.bash.BashConstant.*;
import static com.orness.gandalf.core.module.constantmodule.bash.BashConstant.SCRIPT_BUILD_DIRECTORY;

@Service
public class BashService {

    public boolean cloneProject(String url) {
        Process process;
        try {
            Path path = Paths.get(SCRIPT_BUILD_DIRECTORY);
            if (!Files.exists(path)) {
                Files.createDirectory(path);
                System.out.println("Directory created");
            } else {
                System.out.println("Directory already exists");
            }

            process = new ProcessBuilder( "bash", "-c", SCRIPT_CLONE + " " + url).directory(new File(SCRIPT_BUILD_DIRECTORY)).start();
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
            process = new ProcessBuilder("bash", "-c", SCRIPT_BUILD).directory(new File(SCRIPT_BUILD_DIRECTORY)).start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        return process.exitValue() == 0 ? true : false;
    }

    public boolean tarProject(String projectName) {
        Process process;
        try {
            process = new ProcessBuilder("bash", "-c", SCRIPT_TAR + projectName + ".tar.gz " + SCRIPT_BUILD_DIRECTORY + "/" + projectName).directory(new File(SCRIPT_BUILD_DIRECTORY)).start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        return process.exitValue() == 0 ? true : false;
    }

    public boolean uploadProject(File file) {
        System.out.println(file.getPath());
        Process process;
        try {
            process = new ProcessBuilder("bash", "-c", "curl -X POST -d @" + file.getPath() +  " artifact-service.service.gandalf/upload/" + file.getName()).directory(new File(SCRIPT_BUILD_DIRECTORY)).start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        return process.exitValue() == 0 ? true : false;
    }

    public boolean uploadConf(File conf) {
        Process process;
        try {
            process = new ProcessBuilder("bash", "-c", "curl -X POST -d @" + conf.getPath() +  " artifact-service.service.gandalf/upload/" + conf.getName()).directory(new File(SCRIPT_BUILD_DIRECTORY)).start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        return process.exitValue() == 0 ? true : false;
    }

}
