package com.ditrit.gandalf.gandalfjava.functions.functionscustomartifact.functions;

import com.ditrit.gandalf.gandalfjava.core.zeromqcore.constant.Constant;
import com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.domain.Function;
import com.google.gson.Gson;
import org.zeromq.ZMsg;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.ditrit.gandalf.gandalfjava.functions.functionscustomartifact.properties.ConnectorCustomArtifactConstant.BUILD_PROJECT_DIRECTORY;

public class FunctionDeleteArtifact extends Function {

    private final Path fileStorageLocation;
    private Gson mapper;

    public FunctionDeleteArtifact() {
        super();
        this.mapper = new Gson();
        this.fileStorageLocation = Paths.get(BUILD_PROJECT_DIRECTORY).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        }
        catch (Exception ex) {
        }
    }

    @Override
    public Constant.Result executeCommand(ZMsg command) {
        return null;
    }

    @Override
    public void executeEvent(ZMsg event) {

    }
}
