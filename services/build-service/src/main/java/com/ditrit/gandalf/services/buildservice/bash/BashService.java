package com.ditrit.gandalf.services.buildservice.bash;

import com.ditrit.gandalf.services.buildservice.constant.BuildConstant;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

@Service
public class BashService {

    @Value("${service.endPointConnection}")
    private String serviceEndPointConnection;

    public boolean cloneProject(String url) {
        Process process;
        try {
            Path path = Paths.get(BuildConstant.SCRIPT_BUILD_DIRECTORY);
            if (!Files.exists(path)) {
                Files.createDirectory(path);
                System.out.println("Directory created");
            } else {
                System.out.println("Directory already exists");
            }
            //TODO REVOIR
            System.out.println("URL");
            System.out.println(url);
            //String updatedUrl = updateUrl(url);
            String updatedUrl = url;
            System.out.println("URL UPDATED");
            System.out.println(updatedUrl);
            System.out.println(BuildConstant.SCRIPT_CLONE + " " + updatedUrl);
            process = new ProcessBuilder( "bash", "-c", BuildConstant.SCRIPT_CLONE + " " + updatedUrl).directory(new File(BuildConstant.SCRIPT_BUILD_DIRECTORY)).start();
            process.waitFor();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        return process.exitValue() == 0 ? false : true;
    }

    private String updateUrl(String url) {
        String username = "root";
        String pwd = "testgandalf";
        String beginUrl = url.substring(0,7);
        String endURl = url.substring(21);

        StringBuilder resultUrl = new StringBuilder(beginUrl);
        resultUrl.append(username);
        resultUrl.append(":");
        resultUrl.append(pwd);
        resultUrl.append("@gitlab");
        resultUrl.append(endURl);

        return resultUrl.toString();

    }

    public boolean buildProject(String projectName) {
        Process process;
        try {
            process = new ProcessBuilder("bash", "-c", BuildConstant.SCRIPT_BUILD).directory(new File(BuildConstant.SCRIPT_BUILD_DIRECTORY + "/" + projectName)).start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        return process.exitValue() == 0 ? false : true;
    }

    public boolean tarProject(String projectName, String projectNameVersion) {
        Process process;
        try {
            process = new ProcessBuilder("bash", "-c", BuildConstant.SCRIPT_TAR + projectNameVersion + ".tar.gz " + projectName).directory(new File(BuildConstant.SCRIPT_BUILD_DIRECTORY)).start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        return process.exitValue() == 0 ? false : true;
    }

    public JsonObject uploadProject(File file) {
        Process process;
        JsonObject result = new JsonObject();
        String url = "";
        try {
            process = new ProcessBuilder("bash", "-c", "curl -F 'file=@" + file.getPath() +  "' " + serviceEndPointConnection + "/upload/single/file").directory(new File(BuildConstant.SCRIPT_BUILD_DIRECTORY)).start();
            process.waitFor();
            url = new BufferedReader(new InputStreamReader(process.getInputStream())).lines().collect(Collectors.joining("\n"));
            result.addProperty("url", url);
            result.addProperty("result", process.exitValue() == 0 ? false : true);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            result.addProperty("url", url);
            result.addProperty("result", false);
        }
        return result;
    }

    public JsonObject uploadConf(File conf) {
        Process process;
        JsonObject result = new JsonObject();
        String url = "";
        try {
            process = new ProcessBuilder("bash", "-c", "curl -F 'conf=@" + conf.getPath() +  "' " + serviceEndPointConnection + "/upload/single/conf").directory(new File(BuildConstant.SCRIPT_BUILD_DIRECTORY)).start();
            process.waitFor();
            url = new BufferedReader(new InputStreamReader(process.getInputStream())).lines().collect(Collectors.joining("\n"));
            result.addProperty("url", url);
            result.addProperty("result", process.exitValue() == 0 ? false : true);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            result.addProperty("url", url);
            result.addProperty("result", false);
        }
        return result;
    }

}
