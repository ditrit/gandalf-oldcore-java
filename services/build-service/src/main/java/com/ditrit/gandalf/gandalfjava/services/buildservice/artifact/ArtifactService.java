package com.ditrit.gandalf.gandalfjava.services.buildservice.artifact;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static com.ditrit.gandalf.gandalfjava.services.buildservice.constant.BuildConstant.SCRIPT_DEPLOY_DIRECTORY;

@Service
public class ArtifactService {

    private ArtifactFeign artifactFeign;

    @Autowired
    public ArtifactService(ArtifactFeign artifactFeign) {
        this.artifactFeign = artifactFeign;

    }

    public boolean sendBuildToStorage(String projectName) {
        File file = new File(SCRIPT_DEPLOY_DIRECTORY + "/" + projectName + ".tar.gz");
        File conf = new File(SCRIPT_DEPLOY_DIRECTORY + "/" + projectName + "/" + projectName + ".ini");
        String version = null;
        MultipartFile MPfile = null;
        MultipartFile MPconf = null;
        try {
            version = Files.readAllLines(conf.toPath()).get(0).split("=")[1];
          /*  MPfile = new MockMultipartFile(getNameWithoutExtension(getNameWithoutExtension(file.getName())) + "_" + version + ".tar.gz", file.getName(), null, Files.readAllBytes(file.toPath()));
            MPconf = new MockMultipartFile(getNameWithoutExtension(conf.getName()) + "_" + version + ".ini", conf.getName(), null, Files.readAllBytes(conf.toPath()));*/
        } catch (IOException e) {
            e.printStackTrace();
        }

      /*  String version = null;
        FormData formDataFile = null;
        FormData formDataConf = null;
        try {
            version = Files.readAllLines(conf.toPath()).get(0).split("=")[1];
            formDataFile = new FormData("", projectName+".tar.gz", Files.readAllBytes(file.toPath()));
            formDataConf = new FormData("", projectName+".ini", Files.readAllBytes(conf.toPath()));

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(formDataFile.getFileName());
        System.out.println(formDataConf.getFileName());
        this.artifactFeign.uploadBuild(formDataFile, formDataConf, version);*/
        System.out.println("VERSION");
        System.out.println(version);


        this.artifactFeign.uploadBuildFile(MPfile);
        this.artifactFeign.uploadBuildConf(MPconf);

        return true;
    }

    private String getNameWithoutExtension(String name) {
        String resultName = "";
        int pos = name.lastIndexOf(".");
        if (pos > 0) {
            resultName = name.substring(0, pos);
        }
        return resultName;
    }
}
