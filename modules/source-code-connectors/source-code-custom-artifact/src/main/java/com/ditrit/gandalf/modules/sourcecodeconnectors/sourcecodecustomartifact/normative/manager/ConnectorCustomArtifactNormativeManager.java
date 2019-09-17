package com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodecustomartifact.normative.manager;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ditrit.gandalf.module.abstractconnector.abstractartifact.manager.ConnectorArtifactNormativeManager;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodecustomartifact.properties.ConnectorCustomArtifactProperties;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
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

import static com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodecustomartifact.properties.ConnectorCustomArtifactConstant.BUILD_PROJECT_DIRECTORY;

@Component(value = "normativeManager")
@ConditionalOnBean(ConnectorCustomArtifactProperties.class)
public class ConnectorCustomArtifactNormativeManager extends ConnectorArtifactNormativeManager {

    private final Path fileStorageLocation;
    private Gson mapper;

    @Autowired
    public ConnectorCustomArtifactNormativeManager() {
        super();
        this.mapper = new Gson();
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
    public void uploadArtifact(String payload) {
        MultipartFile artifact = this.mapper.fromJson(payload, MultipartFile.class);
        File fileSaveVersion = new File(fileStorageLocation + "/" + (artifact.getOriginalFilename()));
        try {
            FileUtils.writeByteArrayToFile(fileSaveVersion, artifact.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object downloadArtifact(String payload) {
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        String artifact = jsonObject.get("artifact").getAsString();
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

    @Override
    public Object downloadArtifactById(String payload) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deleteArtifact(String payload) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
