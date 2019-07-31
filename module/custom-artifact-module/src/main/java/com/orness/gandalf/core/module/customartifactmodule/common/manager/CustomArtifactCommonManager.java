package com.orness.gandalf.core.module.customartifactmodule.common.manager;

import com.orness.gandalf.core.module.artifactmodule.manager.ArtifactCommonManager;
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

import static com.orness.gandalf.core.module.constantmodule.storage.StorageConstant.BUILD_PROJECT_DIRECTORY;

@Component(value = "commonManager")
@Profile(value = "custom-artifact-module")
public class CustomArtifactCommonManager extends ArtifactCommonManager {

    private final Path fileStorageLocation;

    @Autowired
    public CustomArtifactCommonManager() {
        super();
        this.fileStorageLocation = Paths.get(BUILD_PROJECT_DIRECTORY).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        }
        catch (Exception ex) {
        }
    }

    @Override
    public void listArtifacts() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void upload(MultipartFile artifact) {
        File fileSaveVersion = new File(fileStorageLocation + "/" + (artifact.getOriginalFilename()));
        try {
            FileUtils.writeByteArrayToFile(fileSaveVersion, artifact.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object download(String artifact) {
        try {
            Path filePath = this.fileStorageLocation.resolve(artifact).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            }
        } catch (MalformedURLException ex) {

        } finally {
            return null;
        }
    }
}
