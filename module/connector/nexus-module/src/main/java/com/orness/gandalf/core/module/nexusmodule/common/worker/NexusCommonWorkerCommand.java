package com.orness.gandalf.core.module.nexusmodule.common.worker;

import com.orness.gandalf.core.module.nexusmodule.common.manager.NexusCommonManager;
import com.orness.gandalf.core.module.nexusmodule.core.properties.NexusProperties;
import com.orness.gandalf.core.module.zeromqmodule.command.domain.MessageCommandZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.command.worker.RunnableWorkerZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import static com.orness.gandalf.core.module.artifactmodule.constant.ArtifactConstant.*;

@Component(value = "commonWorkerCommand")
@Profile(value = "nexus-module")
public class NexusCommonWorkerCommand extends RunnableWorkerZeroMQ {

    private NexusCommonManager nexusCommonManager;
    private NexusProperties nexusProperties;

    @Autowired
    public NexusCommonWorkerCommand(NexusCommonManager nexusCommonManager, NexusProperties nexusProperties) {
        super();
        this.nexusCommonManager = nexusCommonManager;
        this.nexusProperties = nexusProperties;
        this.connect(nexusProperties.getWorker());
    }

    @Override
    public Object parse(String messageContent) {
        return null;
    }

    @Override
    public void command(MessageCommandZeroMQ messageCommandZeroMQ) {

        System.out.println("ID " + this.identity);
        System.out.println("SENDER " + messageCommandZeroMQ.getSender());
        System.out.println("RECEIVER " + messageCommandZeroMQ.getReceiver());
        System.out.println("TYPE_COMMAND " + messageCommandZeroMQ.getTypeCommand());
        System.out.println("COMMAND " + messageCommandZeroMQ.getCommand());

        //TODO REVOIR ARGS
        switch(messageCommandZeroMQ.getTypeCommand().toString()) {
            case COMMAND_LIST_REPOSITORIES:
                this.nexusCommonManager.listRepositories();
                break;
            case COMMAND_LIST_ARTIFACTS:
                this.nexusCommonManager.listArtifacts();
                break;
            case COMMAND_DOWNLOAD_ARTIFACT:
                this.nexusCommonManager.downloadArtifact(0L);
                break;
            case COMMAND_UPLOAD_ARTIFACT:
                this.nexusCommonManager.uploadArtifact("");
                break;
            case COMMAND_DELETE_ARTIFACT:
                this.nexusCommonManager.deleteArtifact(0L);
                break;
            default:
                //DO NOTHING
                break;
        }
    }
}
