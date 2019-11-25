import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

public class EventMessage extends AbstractMessage {
    @Override
    public void sendWith(ZMQ.Socket socket, String routingInfo) throws Exception {
        if (this.getUuid() != null) {
            ZMsg message = new ZMsg();
            message.add(this.getTopic());
            message.add(this.getUuid());
            message.add(objectMapper.writeValueAsString(this.getAccess()));
            message.add(objectMapper.writeValueAsString(this.getInfo()));
            message.add(this.getUuid());

            message.send(socket);
        }
        else {
            throw new Exception("Rsp not initialized, can not be sent");
        }

    }
}
