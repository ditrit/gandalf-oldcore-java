package com.orness.gandalf.core.service.gandalfdatabaseservice.sample;

import com.orness.gandalf.core.module.messagemodule.gandalf.domain.GandalfMessage;
import com.orness.gandalf.core.module.messagemodule.gandalf.repository.GandalfMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SampleCLR implements CommandLineRunner {

    @Autowired
    private GandalfMessageRepository gandalfMessageRepository;


    @Override
    public void run(String... args) throws Exception {
        GandalfMessage gandalfMessage;
        for(int i = 0; i < 5 ; i++) {
            gandalfMessage = new GandalfMessage("topic_"+i, "sender_"+i, "2009-10-20 00:00:00.0", "2020-12-06", "content_"+i);
            gandalfMessageRepository.save(gandalfMessage);
        }
    }

}
