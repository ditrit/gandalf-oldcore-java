package com.orness.gandalf.core.service.buildservice.bash;

import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
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
            process = new ProcessBuilder("bash", "-c", "curl -F 'file=@" + file.getPath() +  "' artifact-service.service.gandalf:10000/upload/single/file").directory(new File(SCRIPT_BUILD_DIRECTORY)).start();
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
            process = new ProcessBuilder("bash", "-c", "curl -F 'conf=@" + conf.getPath() +  "' artifact-service.service.gandalf:10000/upload/single/conf").directory(new File(SCRIPT_BUILD_DIRECTORY)).start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        return process.exitValue() == 0 ? true : false;
    }

}
