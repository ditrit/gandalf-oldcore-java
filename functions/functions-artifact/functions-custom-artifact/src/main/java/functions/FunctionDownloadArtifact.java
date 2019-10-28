package functions;

import com.ditrit.gandalf.core.zeromqcore.constant.Constant;
import com.ditrit.gandalf.core.zeromqcore.worker.domain.Function;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.zeromq.ZMsg;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static properties.ConnectorCustomArtifactConstant.BUILD_PROJECT_DIRECTORY;

public class FunctionDownloadArtifact extends Function {

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
    public Constant.Result executeCommand(ZMsg command) {
        String payload = command.toArray()[14].toString();
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        String artifact = jsonObject.get("artifact").getAsString();
        try {
            Path filePath = this.fileStorageLocation.resolve(artifact).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                //return resource;
            }
        } catch (MalformedURLException ex) {

        } finally {
            return null;
        }
    }

    @Override
    public void executeEvent(ZMsg event) {

    }
}
