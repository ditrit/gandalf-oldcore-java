import com.fasterxml.jackson.databind.ObjectMapper;
import org.msgpack.jackson.dataformat.MessagePackFactory;
import org.zeromq.ZMQ;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractMessage implements InterfaceMessage {

    private String uuid;
    private String topic;
    private Map<String, String> routing;
    private Map<String, String> access;
    private Map<String, String> info;
    ObjectMapper objectMapper;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Map<String, String> getRouting() {
        return routing;
    }

    public void setRouting(Map<String, String> routing) {
        this.routing = routing;
    }

    public Map<String, String> getAccess() {
        return access;
    }

    public void setAccess(Map<String, String> access) {
        this.access = access;
    }

    public Map<String, String> getInfo() {
        return info;
    }

    public void setInfo(Map<String, String> info) {
        this.info = info;
    }

    public AbstractMessage() {
        this.objectMapper = new ObjectMapper(new MessagePackFactory());
        this.routing = new HashMap<>();
        this.access = new HashMap<>();
        this.info = new HashMap<>();
    }

    @Override
    public abstract void sendWith(ZMQ.Socket socket, String routingInfo) throws Exception;
 }
