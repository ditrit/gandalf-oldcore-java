package com.ditrit.gandalf.gandalfjava.functions.functionscustomartifact.functions;

import com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.domain.CommandState;
import com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.domain.Function;
import com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.domain.ReferenceState;
import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;
import org.zeromq.ZMsg;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static com.ditrit.gandalf.gandalfjava.functions.functionscustomartifact.properties.ConnectorCustomArtifactConstant.BUILD_PROJECT_DIRECTORY;

public class FunctionUploadArtifact extends Function {

    private Gson mapper;
    private final Path fileStorageLocation;

    public FunctionUploadArtifact() {
        this.mapper = new Gson();
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
        MultipartFile artifact = this.mapper.fromJson(payload, MultipartFile.class);
        File fileSaveVersion = new File(fileStorageLocation + "/" + (artifact.getOriginalFilename()));
        try {
            FileUtils.writeByteArrayToFile(fileSaveVersion, artifact.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void executeEvent(ZMsg event) {

    }
}
