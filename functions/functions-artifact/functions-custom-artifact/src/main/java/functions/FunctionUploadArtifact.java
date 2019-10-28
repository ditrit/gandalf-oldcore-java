package functions;

import com.ditrit.gandalf.core.zeromqcore.constant.Constant;
import com.ditrit.gandalf.core.zeromqcore.worker.domain.Function;
import com.google.gson.Gson;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.web.multipart.MultipartFile;
import org.zeromq.ZMsg;

import java.io.File;
import java.io.IOException;

public class FunctionUploadArtifact extends Function {

    private Gson mapper;

    public FunctionUploadArtifact() {
        this.mapper = new Gson();
    }

    @Override
    public Constant.Result executeCommand(ZMsg command) {
        String payload = "";
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
