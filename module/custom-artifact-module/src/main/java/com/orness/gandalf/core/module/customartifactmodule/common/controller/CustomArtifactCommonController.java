package com.orness.gandalf.core.module.customartifactmodule.common.controller;

import com.orness.gandalf.core.module.artifactmodule.common.controller.ArtifactCommonController;
import com.orness.gandalf.core.module.customartifactmodule.common.manager.CustomArtifactCommonManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class CustomArtifactCommonController extends ArtifactCommonController {

    private CustomArtifactCommonManager customArtifactCommonManager;

    @Autowired
    public CustomArtifactCommonController(CustomArtifactCommonManager customArtifactCommonManager) {
        this.customArtifactCommonManager = customArtifactCommonManager;

    }

    @Override
    public void listArtifacts() {
        //TODO
    }

    @Override
    @RequestMapping(method = RequestMethod.POST, value = "/upload/artifact")
    public void upload(@RequestParam MultipartFile artifact) {
        String fileName = null;
        try {
            this.customArtifactCommonManager.upload(artifact);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @RequestMapping(method = RequestMethod.GET, value = "/download/{artifact}")
    public Object download(@PathVariable("artifact") String artifact) {
        Resource resource = null;
        String contentType = null;
        resource = (Resource) this.customArtifactCommonManager.download(artifact);
        if(resource != null) {
            if (contentType == null) {
                contentType = "application/octet-stream";
            }
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}