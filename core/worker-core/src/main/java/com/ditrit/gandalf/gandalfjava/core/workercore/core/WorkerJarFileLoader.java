package com.ditrit.gandalf.gandalfjava.core.workercore.core;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class WorkerJarFileLoader extends URLClassLoader {

    public WorkerJarFileLoader(URL[] urls) {
        super(urls);
    }

    public void addFile (String path) throws MalformedURLException {
        String urlPath = "jar:file://" + path + "!/";
        addURL (new URL (urlPath));
    }
}
