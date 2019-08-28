package com.orness.gandalf.core.module.customartifactmodule.custom.controller;

import com.orness.gandalf.core.module.customartifactmodule.normative.manager.CustomArtifactCommonManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

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