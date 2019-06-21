package com.orness.gandalf.core.service.buildstorageservice.controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.orness.gandalf.core.module.constantmodule.storage.StorageConstant.BUILD_PROJECT_DIRECTORY;

@RestController("/builds")
public class BuildStorageRestController {

    @RequestMapping(method = RequestMethod.POST, value = "/upload", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadBuild(@RequestParam("file") MultipartFile file) {
        byte[] bytes = new byte[0];
        try {
            bytes = file.getBytes();
            Path path = Paths.get(BUILD_PROJECT_DIRECTORY + file.getOriginalFilename());
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @RequestMapping(method = RequestMethod.GET, value = "/download/{filename}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody byte[] downloadBuild(@PathVariable("filename") String fileName) {
        try {
            ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(Paths.get(BUILD_PROJECT_DIRECTORY + fileName)));
            return resource.getByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
