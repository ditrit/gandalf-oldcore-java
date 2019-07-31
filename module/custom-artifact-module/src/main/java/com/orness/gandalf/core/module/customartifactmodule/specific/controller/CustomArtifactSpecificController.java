package com.orness.gandalf.core.module.customartifactmodule.specific.controller;

import com.orness.gandalf.core.module.customartifactmodule.common.manager.CustomArtifactCommonManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController(value = "specificController")
@Profile(value = "custom-artifact-module")
public class CustomArtifactSpecificController {

    private CustomArtifactCommonManager customArtifactCommonManager;

    @Autowired
    public CustomArtifactSpecificController(CustomArtifactCommonManager customArtifactCommonManager) {
        //this.customArtifactCommonManager = customArtifactCommonManager;

    }

   /* @RequestMapping(method = RequestMethod.POST, value = "/upload/single/file")
    //@Headers("Content-Type: multipart/form-data")
    public void uploadBuildSingleFile(@RequestParam MultipartFile file) {
        String fileName = null;
        try {
            this.customArtifactCommonManager.upload(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @RequestMapping(method = RequestMethod.POST, value = "/upload/single/conf")
    //@Headers("Content-Type: multipart/form-data")
    public void uploadBuildSingleConf(@RequestParam MultipartFile conf) {
        String fileName = null;
        try {
            this.customArtifactCommonManager.upload(conf);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/download/{fileName}")
    public ResponseEntity<Resource> downloadBuild(@PathVariable("fileName") String fileName) {
        // Load file as Resource
        Resource resource = null;
        String contentType = null;
        resource = (Resource) this.customArtifactCommonManager.download(fileName);
        if(resource != null) {
            if (contentType == null) {
                contentType = "application/octet-stream";
            }
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }*/

}