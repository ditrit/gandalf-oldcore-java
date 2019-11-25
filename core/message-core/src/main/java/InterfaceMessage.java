import org.zeromq.ZMQ;

public interface InterfaceMessage {

    void sendWith(ZMQ.Socket socket, String routingInfo) throws Exception;
}
