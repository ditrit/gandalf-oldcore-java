package functions;

import com.ditrit.gandalf.core.zeromqcore.constant.Constant;
import com.ditrit.gandalf.core.zeromqcore.worker.domain.Function;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.zeromq.ZMsg;
import properties.ConnectorCustomOrchestratorProperties;

public class FunctionDownloadProject extends Function {

    private Gson mapper;
    private RestTemplate restTemplate;
    private ConnectorCustomOrchestratorProperties connectorCustomOrchestratorProperties;

    public FunctionDownloadProject() {
        this.mapper = new Gson();
        this.restTemplate = new RestTemplate();
    }

    @Override
    public Constant.Result executeCommand(ZMsg command) {
        String uri = this.connectorCustomOrchestratorProperties.getTargetEndPointConnection();
        String url = uri + "/orchestrator-service/download/";
        String payload = command.toArray()[14].toString();
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
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
