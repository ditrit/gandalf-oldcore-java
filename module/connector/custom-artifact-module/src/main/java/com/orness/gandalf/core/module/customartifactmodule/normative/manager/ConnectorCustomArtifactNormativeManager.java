package com.orness.gandalf.core.module.customartifactmodule.normative.manager;

import com.orness.gandalf.core.module.artifactmodule.manager.ConnectorArtifactNormativeManager;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static com.orness.gandalf.core.module.constantmodule.storage.StorageConstant.BUILD_PROJECT_DIRECTORY;

@Component(value = "commonManager")
@Profile(value = "custom-artifact-module")
public class ConnectorCustomArtifactNormativeManager extends ConnectorArtifactNormativeManager {

    private final Path fileStorageLocation;

    @Autowired
    public ConnectorCustomArtifactNormativeManager() {
        super();
        this.fileStorageLocation = Paths.get(BUILD_PROJECT_DIRECTORY).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        }
        catch (Exception ex) {
        }
    }

    @Override
    public List listRepositories() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List listArtifacts() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void uploadArtifact(Object artifact) {
        MultipartFile multipartArtifact = (MultipartFile) artifact;
        File fileSaveVersion = new File(fileStorageLocation + "/" + (multipartArtifact.getOriginalFilename()));
        try {
            FileUtils.writeByteArrayToFile(fileSaveVersion, multipartArtifact.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object downloadArtifact(Object artifact) {
        String stringArtifact = (String) artifact;
        try {
            Path filePath = this.fileStorageLocation.resolve(stringArtifact).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            }
        } catch (MalformedURLException ex) {

        } finally {
            return null;
        }
    }


    @Override
    public Object downloadArtifactById(Long id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deleteArtifact(Long id) {

    }
}
