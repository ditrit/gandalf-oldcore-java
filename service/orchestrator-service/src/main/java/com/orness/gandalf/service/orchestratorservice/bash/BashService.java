package com.orness.gandalf.service.orchestratorservice.bash;

import org.springframework.stereotype.Service;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.orness.gandalf.core.module.constantmodule.bash.BashConstant.*;

@Service
public class BashService {

    public boolean execute(String service, String command) {
        Process process;
        try {
            Resource resource = new ClassPathResource(SCRIPT_RESSOURCES_FILE + "/" + SCRIPT_COMMAND_FILE);
            InputStream input = resource.getInputStream();
            File file = resource.getFile();
            System.out.println(file.getPath());
            System.out.println(file.getAbsolutePath());
            System.out.println(file.getCanonicalPath());
            process = new ProcessBuilder(file.getPath(), command, service).start();
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
            Resource resource = new ClassPathResource(SCRIPT_RESSOURCES_FILE + "/" + SCRIPT_REGISTER_FILE);
            InputStream input = resource.getInputStream();
            File file = resource.getFile();
            System.out.println(file.getPath());
            System.out.println(file.getAbsolutePath());
            System.out.println(file.getCanonicalPath());
            process = new ProcessBuilder(file.getPath(), service, version).start();
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
            Path path = Paths.get(SCRIPT_BUILD_DIRECTORY);
            if (!Files.exists(path)) {
                Files.createDirectory(path);
                System.out.println("Directory created");
            } else {
                System.out.println("Directory already exists");
            }
            process = new ProcessBuilder("bash", "-c", "wget artifact-service.service.gandalf/download/" + projectName + "_" + version + ".tar.gz").directory(new File(SCRIPT_BUILD_DIRECTORY)).start();
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
            Path path = Paths.get(SCRIPT_BUILD_DIRECTORY);
            if (!Files.exists(path)) {
                Files.createDirectory(path);
                System.out.println("Directory created");
            } else {
                System.out.println("Directory already exists");
            }
            process = new ProcessBuilder("bash", "-c", "wget artifact-service.service.gandalf/download/" + projectName + "_" + version + ".ini").directory(new File(SCRIPT_BUILD_DIRECTORY)).start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        return process.exitValue() == 0 ? true : false;
    }
}
