package com.orness.gandalf.service.orchestratorservice.bash;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.orness.gandalf.service.orchestratorservice.constant.OrchestratorServiceConstant.*;

@Service
public class BashService {

    @Value("${service.endPointConnection}")
    private String serviceEndPointConnection;

    public boolean execute(String service, String command) {
        Process process;
        try {
            //process = new ProcessBuilder(SCRIPT_RESSOURCES_DIRECTORY + "/" + SCRIPT_COMMAND_FILE, command, service).start();
            process = new ProcessBuilder(this.getFileAbsolutePathFromResources(SCRIPT_COMMAND_FILE), command, service).start();
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
            //process = new ProcessBuilder(SCRIPT_RESSOURCES_DIRECTORY + "/" + SCRIPT_REGISTER_FILE, service, version).start();
            System.out.println("PATH");
            System.out.println(this.getFileAbsolutePathFromResources(SCRIPT_REGISTER_FILE));
            process = new ProcessBuilder(this.getFileAbsolutePathFromResources(SCRIPT_REGISTER_FILE), service, version).start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        return process.exitValue() == 0 ? true : false;
    }

    public boolean unregister(String service, String version) {
        Process process;
        try {
            //process = new ProcessBuilder(SCRIPT_RESSOURCES_DIRECTORY + "/" + SCRIPT_REGISTER_FILE, service, version).start();
            process = new ProcessBuilder(this.getFileAbsolutePathFromResources(SCRIPT_REGISTER_FILE), service, version).start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        return process.exitValue() == 0 ? true : false;
    }

    public boolean untarProject(String projectName, String version) {
        Process process;
        try {
            //process = new ProcessBuilder("bash", "-c", SCRIPT_UNTAR + projectName + "_" + version + ".tar.gz").directory(new File(SCRIPT_BUILD_DIRECTORY)).start();
            process = new ProcessBuilder("bash", "-c", SCRIPT_UNTAR + projectName + "_" + version + ".tar.gz").directory(new File(SCRIPT_BUILD_DIRECTORY)).start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        return process.exitValue() == 0 ? true : false;
    }

    public boolean downloadProject(String url) {
        Process process;
        try {
            Path path = Paths.get(SCRIPT_BUILD_DIRECTORY);
            if (!Files.exists(path)) {
                Files.createDirectory(path);
                System.out.println("Directory created");
            } else {
                System.out.println("Directory already exists");
            }
            process = new ProcessBuilder("bash", "-c", "wget " + serviceEndPointConnection + "/" + url).directory(new File(SCRIPT_BUILD_DIRECTORY)).start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        return process.exitValue() == 0 ? true : false;
    }

    public boolean downloadConfiguration(String url) {
        Process process;
        try {
            Path path = Paths.get(SCRIPT_BUILD_DIRECTORY);
            if (!Files.exists(path)) {
                Files.createDirectory(path);
                System.out.println("Directory created");
            } else {
                System.out.println("Directory already exists");
            }
            process = new ProcessBuilder("bash", "-c", "wget " + serviceEndPointConnection + "/" + url).directory(new File(SCRIPT_BUILD_DIRECTORY)).start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        return process.exitValue() == 0 ? true : false;
    }

    private String getFileAbsolutePathFromResources(String fileName) {
        try {
            return new ClassPathResource("script/"+fileName).getFile().getPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
