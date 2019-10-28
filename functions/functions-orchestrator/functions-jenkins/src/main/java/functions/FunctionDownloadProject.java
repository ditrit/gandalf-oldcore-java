package functions;

import com.ditrit.gandalf.core.zeromqcore.constant.Constant;
import com.ditrit.gandalf.core.zeromqcore.worker.domain.Function;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.zeromq.ZMsg;

public class FunctionDownloadProject extends Function {

    private RestTemplate restTemplate;

    public FunctionDownloadProject() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    public Constant.Result executeCommand(ZMsg command) {

        String url = uri + "/orchestrator-service/download/";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> requestEntity = new HttpEntity<>(jsonObject.toString(), headers);

        this.restTemplate.postForObject(url, requestEntity, boolean.class);
        return null;
    }

    @Override
    public void executeEvent(ZMsg event) {

    }
}
