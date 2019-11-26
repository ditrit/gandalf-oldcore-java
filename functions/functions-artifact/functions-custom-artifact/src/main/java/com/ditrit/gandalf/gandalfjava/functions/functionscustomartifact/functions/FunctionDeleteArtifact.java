package com.ditrit.gandalf.gandalfjava.functions.functionscustomartifact.functions;

import com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.domain.CommandState;
import com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.domain.Function;
import com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.domain.ReferenceState;
import com.google.gson.Gson;
import org.zeromq.ZMsg;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

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
    public String executeCommand(ZMsg command, List<CommandState> commandStates, ReferenceState referenceState) {
        return null;
    }

    @Override
    public void executeEvent(ZMsg event) {

    }
}
