package com.orness.gandalf.core.artifactservice.service;

import feign.form.FormData;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.orness.gandalf.core.module.constantmodule.storage.StorageConstant.BUILD_PROJECT_DIRECTORY;

@Service
public class ArtifactStorageService {

    private final Path fileStorageLocation;

    @Autowired
    public ArtifactStorageService() {
        this.fileStorageLocation = Paths.get(BUILD_PROJECT_DIRECTORY)
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
        }
    }

    public String storeFile(FormData file, FormData conf, String version) throws Exception {

        File confSaveVersion = new File(fileStorageLocation + "/" + (conf.getFileName()) + "_" +  version + ".ini");
        FileUtils.writeByteArrayToFile(confSaveVersion, conf.getData());
        conf.createNewFile();
	    File confSaveVersin = new File(fileStorageLocation + "/" + (file.getName()) + "_" +  version + ".ini");
        confSaveVersion.createNewFile();
        System.out.println("toto 1");
        FileChannel src = new FileInputStream(conf).getChannel();
        System.out.println("toto 2");
        FileChannel dest = new FileOutputStream(confSaveVersion).getChannel();
        System.out.println("toto 3");
        dest.transferFrom(src, 0, src.size());
        System.out.println("toto 4");
        //Files.copy(conf.toPath(), confSaveVersion.toPath(), StandardCopyOption.REPLACE_EXISTING);

        File fileSaveVersion = new File(fileStorageLocation + "/" + (file.getFileName()) + "_" +  version + ".zip");
        FileUtils.writeByteArrayToFile(fileSaveVersion, file.getData());

        return confSaveVersion.getName();
    }

    public Resource loadFileAsResource(String fileName) throws FileNotFoundException {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                return null;
            }
        } catch (MalformedURLException ex) {
            throw new FileNotFoundException();
        }
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
