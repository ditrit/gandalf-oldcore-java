package com.ditrit.gandalf.gandalfjava.functions.functionscustomartifact.functions;

import com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.domain.CommandFunction;
import com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.domain.CommandState;
import com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.domain.ThreadFunction;
import com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.domain.ReferenceState;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.zeromq.ZMsg;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static com.ditrit.gandalf.gandalfjava.functions.functionscustomartifact.properties.ConnectorCustomArtifactConstant.BUILD_PROJECT_DIRECTORY;

public class FunctionDownloadArtifact extends CommandFunction {

    private final Path fileStorageLocation;
    private Gson mapper;

    public FunctionDownloadArtifact() {
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
        String payload = command.toArray()[14].toString();
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        String artifact = jsonObject.get("artifact").getAsString();
        try {
            Path filePath = this.fileStorageLocation.resolve(artifact).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                //return resource;
            }
        }
        catch (MalformedURLException ex) {
        }
        finally {
            return null;
        }
    }
}
