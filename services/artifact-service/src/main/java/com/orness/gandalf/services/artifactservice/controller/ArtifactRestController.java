package com.orness.gandalf.services.artifactservice.controller;

import com.orness.gandalf.services.artifactservice.service.ArtifactStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class ArtifactRestController {

    private ArtifactStorageService artifactStorageService;

    @Autowired
    public ArtifactRestController(ArtifactStorageService artifactStorageService) {
        this.artifactStorageService = artifactStorageService;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/upload/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadBuildFile(@RequestParam("file") MultipartFile file) {
        String fileName = null;
        try {
            fileName = artifactStorageService.storeFile(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/download/" + fileName;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/upload/conf", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadBuildConf(@RequestParam("conf") MultipartFile conf) {
        String fileName = null;
        try {
            fileName = artifactStorageService.storeConf(conf);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/download/" + fileName;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/upload/single/file")
    //@Headers("Content-Type: multipart/form-data")
    public String uploadBuildSingleFile(@RequestParam MultipartFile file) {
        String fileName = null;
        try {
            fileName = artifactStorageService.storeSingleFile(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/download/" + fileName;
    }
    @RequestMapping(method = RequestMethod.POST, value = "/upload/single/conf")
    //@Headers("Content-Type: multipart/form-data")
    public String uploadBuildSingleConf(@RequestParam MultipartFile conf) {
        String fileName = null;
        try {
            fileName = artifactStorageService.storeSingleConf(conf);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/download/" + fileName;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/download/{fileName}")
    public ResponseEntity<Resource> downloadBuild(@PathVariable("fileName") String fileName) {
        // Load file as Resource
        Resource resource = null;
        String contentType = null;
        try {
            resource = artifactStorageService.loadFileAsResource(fileName);

            if(resource != null) {

                if (contentType == null) {
                    contentType = "application/octet-stream";
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}
