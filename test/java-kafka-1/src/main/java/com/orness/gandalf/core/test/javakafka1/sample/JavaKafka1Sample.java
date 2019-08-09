package com.orness.gandalf.core.test.javakafka1.sample;

import com.orness.gandalf.core.library.gandalfjavaclient.GandalfJavaClient;
import com.orness.gandalf.core.module.messagemodule.gandalf.domain.GandalfEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Component
@ComponentScan(basePackages = {"com.orness.gandalf.core.library.gandalfjavaclient"})
public class JavaKafka1Sample implements CommandLineRunner {

    @Autowired
    private GandalfJavaClient gandalfJavaClient;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Start sample");

        test();
    }

    public void test() {
        for(int i=0; i< 50; i++) {
            System.out.println("indice " + i);
            gandalfJavaClient.sendEvent("test", "test", String.valueOf(i));
        }

    }
}
