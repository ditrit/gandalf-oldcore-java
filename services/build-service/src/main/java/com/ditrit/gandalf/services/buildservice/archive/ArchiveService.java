package com.ditrit.gandalf.services.buildservice.archive;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static com.ditrit.gandalf.services.buildservice.constant.BuildConstant.SCRIPT_DEPLOY_DIRECTORY;

@Service
public class ArchiveService {

    public boolean zipArchive(String projectName) {
        boolean succes = false;
        FileOutputStream fos = null;
        try {
            Path path = Paths.get(SCRIPT_DEPLOY_DIRECTORY);
            if (!Files.exists(path)) {
                Files.createDirectory(path);
                System.out.println("Directory created");
            } else {
                System.out.println("Directory already exists");
            }

            new File(SCRIPT_DEPLOY_DIRECTORY).createNewFile();
            fos = new FileOutputStream(SCRIPT_DEPLOY_DIRECTORY + "/" + projectName + ".zip");
            ZipOutputStream zipOut = new ZipOutputStream(fos);
            File fileToZip = new File(SCRIPT_DEPLOY_DIRECTORY + "/" + projectName);
            fileToZip.createNewFile();

            zipFile(fileToZip, fileToZip.getName(), zipOut);
            zipOut.close();
            fos.close();

            succes = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return succes;
    }

    private void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
        if (fileToZip.isHidden()) {
            return;
        }
        if (fileToZip.isDirectory()) {
            if (fileName.endsWith("/")) {
                zipOut.putNextEntry(new ZipEntry(fileName));
                zipOut.closeEntry();
            } else {
                zipOut.putNextEntry(new ZipEntry(fileName + "/"));
                zipOut.closeEntry();
            }
            File[] children = fileToZip.listFiles();
            for (File childFile : children) {
                zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
            }
            return;
        }
        FileInputStream fis = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileName);
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        fis.close();
    }
}