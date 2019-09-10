package com.orness.gandalf.core.artifactservice.service;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.orness.gandalf.core.artifactservice.constant.ArtifactConstant.ARTIFACTS_PROJECT_DIRECTORY;


@Service
public class ArtifactStorageService {

    private final Path fileStorageLocation;

    @Autowired
    public ArtifactStorageService() {
        this.fileStorageLocation = Paths.get(ARTIFACTS_PROJECT_DIRECTORY)
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
        }
    }

    public String storeFile(MultipartFile file) throws Exception {

        File fileSaveVersion = new File(fileStorageLocation + "/" + (file.getName()));
        FileUtils.writeByteArrayToFile(fileSaveVersion, file.getBytes());

        return fileSaveVersion.getName();
    }

    public String storeConf(MultipartFile conf) throws Exception {

        File confSaveVersion = new File(fileStorageLocation + "/" + (conf.getName()));
        FileUtils.writeByteArrayToFile(confSaveVersion, conf.getBytes());

        return confSaveVersion.getName();
    }

    public String storeSingleFile(MultipartFile file) throws Exception {
        File fileSaveVersion = new File(fileStorageLocation + "/" + (file.getOriginalFilename()));
        FileUtils.writeByteArrayToFile(fileSaveVersion, file.getBytes());
        return fileSaveVersion.getName();
    }

    public String storeSingleConf(MultipartFile conf) throws Exception {
        File confSaveVersion = new File(fileStorageLocation + "/" + (conf.getOriginalFilename()));
        FileUtils.writeByteArrayToFile(confSaveVersion, conf.getBytes());
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
}
