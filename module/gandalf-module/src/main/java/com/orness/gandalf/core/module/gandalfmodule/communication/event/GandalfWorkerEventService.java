package com.orness.gandalf.core.module.gandalfmodule.communication.event;

import com.orness.gandalf.core.module.gandalfmodule.manager.GandalfGenericManager;
import org.springframework.stereotype.Service;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import static com.orness.gandalf.core.module.gandalfmodule.constant.GandalfConstant.WORKER_DIRECTORY;

@Service
public class GandalfWorkerEventService {

    public GandalfWorkerEvent createInstanceByManager(GandalfGenericManager gandalfGenericManager) {
        return new GandalfWorkerEvent(gandalfGenericManager);
    }

    public GandalfGenericManager loadInstanceByName(String name, String classpath) {

        File workerDirectory = new File(System.getProperty("user.dir") + WORKER_DIRECTORY + name);
        try {
            ClassLoader loader = URLClassLoader.newInstance(new URL[]{workerDirectory.toURL()}, getClass().getClassLoader());
            Class<?> clazz = Class.forName(classpath, true, loader);
            Class<? extends GandalfGenericManager> newClass = clazz.asSubclass(GandalfGenericManager.class);

            Constructor<? extends GandalfGenericManager> constructor = newClass.getConstructor();
            return constructor.newInstance();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
