package com.orness.gandalf.core.service.buildservice.bash;

import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import static com.orness.gandalf.core.module.constantmodule.bash.BashConstant.*;
import static com.orness.gandalf.core.module.constantmodule.bash.BashConstant.SCRIPT_BUILD_DIRECTORY;

@Service
public class BashService {

    @Value("${service.endPointConnection}")
    private String serviceEndPointConnection;

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
            //TODO REVOIR
            System.out.println("URL");
            System.out.println(url);
            //String updatedUrl = updateUrl(url);
            String updatedUrl = url;
            System.out.println("URL UPDATED");
            System.out.println(updatedUrl);
            System.out.println(SCRIPT_CLONE + " " + updatedUrl);
            process = new ProcessBuilder( "bash", "-c", SCRIPT_CLONE + " " + updatedUrl).directory(new File(SCRIPT_BUILD_DIRECTORY)).start();
            process.waitFor();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        return process.exitValue() == 0 ? true : false;
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
            process = new ProcessBuilder("bash", "-c", SCRIPT_BUILD).directory(new File(SCRIPT_BUILD_DIRECTORY + "/" + projectName)).start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        return process.exitValue() == 0 ? true : false;
    }

    public boolean tarProject(String projectName, String projectNameVersion) {
        Process process;
        try {
            process = new ProcessBuilder("bash", "-c", SCRIPT_TAR + projectNameVersion + ".tar.gz " + projectName).directory(new File(SCRIPT_BUILD_DIRECTORY)).start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        return process.exitValue() == 0 ? true : false;
    }

    public JsonObject uploadProject(File file) {
        Process process;
        JsonObject result = new JsonObject();
        String url = "";
        try {
            process = new ProcessBuilder("bash", "-c", "curl -F 'file=@" + file.getPath() +  "' " + serviceEndPointConnection + "/upload/single/file").directory(new File(SCRIPT_BUILD_DIRECTORY)).start();
            process.waitFor();
            url = new BufferedReader(new InputStreamReader(process.getInputStream())).lines().collect(Collectors.joining("\n"));
            result.addProperty("url", url);
            result.addProperty("result", process.exitValue() == 0 ? true : false);
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
            process = new ProcessBuilder("bash", "-c", "curl -F 'conf=@" + conf.getPath() +  "' " + serviceEndPointConnection + "/upload/single/conf").directory(new File(SCRIPT_BUILD_DIRECTORY)).start();
            process.waitFor();
            url = new BufferedReader(new InputStreamReader(process.getInputStream())).lines().collect(Collectors.joining("\n"));
            result.addProperty("url", url);
            result.addProperty("result", process.exitValue() == 0 ? true : false);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            result.addProperty("url", url);
            result.addProperty("result", false);
        }
        return result;
    }

}
