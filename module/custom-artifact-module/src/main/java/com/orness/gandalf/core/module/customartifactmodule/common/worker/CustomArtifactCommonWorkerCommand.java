package com.orness.gandalf.core.module.customartifactmodule.common.worker;

import com.orness.gandalf.core.module.customartifactmodule.common.manager.CustomArtifactCommonManager;
import com.orness.gandalf.core.module.customartifactmodule.core.CustomArtifactCommand;
import com.orness.gandalf.core.module.zeromqmodule.command.domain.MessageCommandZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.command.worker.RunnableWorkerZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import static com.orness.gandalf.core.module.artifactmodule.constant.ArtifactConstant.*;

@Component(value = "commonWorkerCommand")
@Profile(value = "custom-artifact-module")
public class CustomArtifactCommonWorkerCommand extends RunnableWorkerZeroMQ {

    @Autowired
    private CustomArtifactCommonManager customArtifactCommonManager;

    public CustomArtifactCommonWorkerCommand(String connection) {
        super(connection);
    }

    @Override
    public Object parse(String messageContent) {
        return mapper.fromJson(messageContent, CustomArtifactCommand.class);
    }

    @Override
    public void command(MessageCommandZeroMQ messageCommandZeroMQ) {
        System.out.println("ID " + this.identity);
        System.out.println("SENDER " + messageCommandZeroMQ.getSender());
        System.out.println("RECEIVER " + messageCommandZeroMQ.getReceiver());
        System.out.println("TYPE_COMMAND " + messageCommandZeroMQ.getTypeCommand());
        System.out.println("COMMAND " + messageCommandZeroMQ.getCommand());

        switch(messageCommandZeroMQ.getTypeCommand().toString()) {
            case COMMAND_LIST:
                this.customArtifactCommonManager.listArtifacts();
                break;
            case COMMAND_DOWNLOAD:
                //TODO ADD ARTIFACT COMMAND
                this.customArtifactCommonManager.upload(null);
                break;
            case COMMAND_UPLOAD:
                //TODO ADD ARTIFACT COMMAND
                this.customArtifactCommonManager.download("");
                break;
            default:
                //DO NOTHING
                break;
        }
    }
}
