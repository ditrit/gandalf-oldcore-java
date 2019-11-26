package com.ditrit.gandalf.gandalfjava.core.workercore.core;

import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
public class WorkerJarFileLoaderService  {

    public void loadClassesByJar (String path) {
        URL urls [] = {};
        List<String> classNames;
        WorkerJarFileLoader workerJarFileLoader = new WorkerJarFileLoader (urls);

        try {
            workerJarFileLoader.addFile (path);
            classNames = this.getAllClassesFromJar(path);
            for(String className : classNames) {
                workerJarFileLoader.loadClass(className);
            }
        }
        catch (ClassNotFoundException | MalformedURLException e) {
            e.printStackTrace();
        }
        System.out.println ("Success!");
    }

    private List<String> getAllClassesFromJar(String path) {
        List<String> classNames = new ArrayList<String>();
        try {
            ZipInputStream zip = new ZipInputStream(new FileInputStream(path));
            for (ZipEntry entry = zip.getNextEntry(); entry != null; entry = zip.getNextEntry()) {
                if (!entry.isDirectory() && entry.getName().endsWith(".class")) {
                    String className = entry.getName().replace('/', '.'); // including ".class"
                    classNames.add(className.substring(0, className.length() - ".class".length()));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classNames;
    }
}
