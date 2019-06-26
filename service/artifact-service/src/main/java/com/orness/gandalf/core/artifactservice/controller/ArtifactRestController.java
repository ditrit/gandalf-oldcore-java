package com.orness.gandalf.core.artifactservice.controller;

import com.orness.gandalf.core.artifactservice.service.ArtifactStorageService;
import feign.Headers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@RestController
public class ArtifactRestController {

    private ArtifactStorageService artifactStorageService;

    @Autowired
    public ArtifactRestController(ArtifactStorageService artifactStorageService) {
        this.artifactStorageService = artifactStorageService;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/upload")
    @Headers("Content-Type: application/zip")
    public void uploadBuild(@RequestParam("file") File file, @RequestParam("conf") File conf, @RequestParam("version") String version) {
        String fileName = null;
        System.out.println(file.getName());
        try {
            fileName = artifactStorageService.storeFile(file, conf, version);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/download/{filename}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> downloadBuild(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = null;
        String contentType = null;
        try {
            resource = artifactStorageService.loadFileAsResource(fileName);

            if(resource != null) {
                // Try to determine file's content type
                contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

                // Fallback to the default content type if type could not be determined
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
