package com.orness.gandalf.core.service.databasebusservice.sample;

import com.orness.gandalf.core.module.messagemodule.domain.MessageGandalf;
import com.orness.gandalf.core.module.messagemodule.repository.MessageGandalfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SampleCLR implements CommandLineRunner {

    @Autowired
    private MessageGandalfRepository messageGandalfRepository;


    @Override
    public void run(String... args) throws Exception {
        MessageGandalf messageGandalf;
        for(int i = 0; i < 5 ; i++) {
            messageGandalf = new MessageGandalf("topic_"+i, "sender_"+i, "2009-10-20 00:00:00.0", "2020-12-06", "content_"+i);
            messageGandalfRepository.save(messageGandalf);
        }
    }

}
