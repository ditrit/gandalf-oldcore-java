package com.ditrit.gandalf.gandalfjava.core.workercore.loader;

import com.ditrit.gandalf.gandalfjava.core.workercore.core.WorkerJarFileLoader;
import com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.domain.ThreadFunction;
import com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.domain.ThreadFunction;import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
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
@Scope("singleton")
public class WorkerJarFileLoaderService  {

    private WorkerJarFileLoader workerJarFileLoader;



    @Autowired
    public WorkerJarFileLoaderService() {
        URL urls [] = {};
        this.workerJarFileLoader = new WorkerJarFileLoader (urls);
    }

    public List<ThreadFunction> startFunctionsByJar(String path) {
        List<String> classNames = this.retrieveClassesByJar(path);
        this.loadClassesByClassNames(classNames);
        return this.instanciateClassesByClassNames(classNames);
    }

    private List<ThreadFunction> instanciateClassesByClassNames(List<String> classNames) {
        List<ThreadFunction> functions = new ArrayList<>();
        for(String className : classNames) {
            try {
                ThreadFunction currentFunctions = ((ThreadFunction) Class.forName(className).newInstance());
                functions.add(currentFunctions);
                currentFunctions.run();
            }
            catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
                System.out.println ("Fail!");
            }
        }
        System.out.println ("Success!");
        return functions;
    }

    private List<String> retrieveClassesByJar(String path) {
        List<String> classNames = null;
        try {
            this.workerJarFileLoader.addFile (path);
            classNames = this.getAllClassesFromJar(path);
            System.out.println ("Success!");
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
            System.out.println ("Fail!");
        }
        return classNames;
    }

    private void loadClassesByClassNames (List<String> classNames) {
        try {

            for(String className : classNames) {
                this.workerJarFileLoader.loadClass(className);
                System.out.println ("Success!");
            }
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println ("Fail!");
        }
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
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return classNames;
    }
}
