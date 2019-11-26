package com.ditrit.gandalf.gandalfjava.core.messagecore.domain;

import com.ditrit.gandalf.gandalfjava.core.messagecore.core.AbstractMessage;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

import java.util.HashMap;
import java.util.Map;

public class ResponseMessage extends AbstractMessage {

    public ResponseMessage() {
        super();
    }

    public void to(CommandMessage command, HashMap<String, Object> values) throws Exception {
        if (command instanceof CommandMessage) {
            this.setUuid(command.getUuid());
            this.setRouting(command.getRouting());
            this.setAccess(command.getAccess());
            Map<String, Object> toto = new HashMap<>();
            toto.put("state", values.get("state"));
            toto.put("payload", values.get("payload"));
            toto.put("command", command.getInfo().get("command"));
            //this.getInfo().putAll({ "state": values.state || "on_going", payload: values.payload, command: command.info.command });
        } else {
            throw new Exception("Error : can not sen a response to a non defined command...!!!");
        }
    }


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
