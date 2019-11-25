package com.ditrit.gandalf.gandalfjava.core.messagecore;

import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

public class CommandMessage extends AbstractMessage {

    @Override
    public void sendWith(ZMQ.Socket socket, String routingInfo) throws Exception {
        if (this.getUuid() != null) {
            ZMsg message = new ZMsg();
            message.add(this.getUuid());
            message.add(objectMapper.writeValueAsString(this.getRouting()));
            message.add(objectMapper.writeValueAsString(this.getAccess()));
            message.add(objectMapper.writeValueAsString(this.getInfo()));
            message.add(this.getUuid());
            //let msg = [this.uuid, msgpack.encode(this.routing), msgpack.encode(this.access), msgpack.encode(this.info)]
            //routing_info && msg.unshift(routing_info.toString())
            message.send(socket);
        }
        else {
            throw new Exception("Rsp not initialized, can not be sent");
        }

    }
}
