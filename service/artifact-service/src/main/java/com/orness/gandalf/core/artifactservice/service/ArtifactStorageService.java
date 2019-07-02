package com.orness.gandalf.core.artifactservice.service;

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
import java.nio.file.StandardCopyOption;

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

    public String storeFile(File file, File conf, String version) throws Exception {

        File confSaveVersion = new File(fileStorageLocation + "/" + getNameWithoutExtension(file.getName()) + "_" +  version + ".ini");
        confSaveVersion.createNewFile();
        Files.copy(conf.toPath(), confSaveVersion.toPath(), StandardCopyOption.REPLACE_EXISTING);

        File fileSaveVersion = new File(fileStorageLocation + "/" + getNameWithoutExtension(file.getName()) + "_" + version + ".zip");
        fileSaveVersion.createNewFile();
        Files.copy(file.toPath(), fileSaveVersion.toPath(), StandardCopyOption.REPLACE_EXISTING);

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