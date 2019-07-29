package com.orness.gandalf.core.module.customorchestratormodule.core;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.orness.gandalf.core.module.constantmodule.bash.BashConstant.*;
import static com.orness.gandalf.core.module.constantmodule.bash.BashConstant.SCRIPT_BUILD_DIRECTORY;

@Service
public class CustomOrchestratorBashService {

    public boolean execute(String service, String command) {
        Process process;
        try {

            process = new ProcessBuilder(SCRIPT_RESSOURCES_DIRECTORY + "/" + SCRIPT_COMMAND_FILE, command, service).start();
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
            process = new ProcessBuilder(SCRIPT_RESSOURCES_DIRECTORY + "/" + SCRIPT_REGISTER_FILE, service, version).start();
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
            process = new ProcessBuilder(SCRIPT_RESSOURCES_DIRECTORY + "/" + SCRIPT_REGISTER_FILE, service, version).start();
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
            process = new ProcessBuilder("bash", "-c", SCRIPT_UNTAR + projectName + "_" + version + ".tar.gz").directory(new File(SCRIPT_BUILD_DIRECTORY)).start();
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
            process = new ProcessBuilder("bash", "-c", "wget artifact-service.service.gandalf:10000/download/" + projectName + "_" + version + ".tar.gz").directory(new File(SCRIPT_BUILD_DIRECTORY)).start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        return process.exitValue() == 0 ? true : false;
    }

    public boolean downloadConfiguration(String projectName, String version) {
        Process process;
        try {
            Path path = Paths.get(SCRIPT_BUILD_DIRECTORY);
            if (!Files.exists(path)) {
                Files.createDirectory(path);
                System.out.println("Directory created");
            } else {
                System.out.println("Directory already exists");
            }
            process = new ProcessBuilder("bash", "-c", "wget artifact-service.service.gandalf:10000/download/" + projectName + "_" + version + ".ini").directory(new File(SCRIPT_BUILD_DIRECTORY)).start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        return process.exitValue() == 0 ? true : false;
    }
}
