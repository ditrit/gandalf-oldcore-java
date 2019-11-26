package com.ditrit.gandalf.gandalfjava.core.messagecore.factory;

import com.ditrit.gandalf.gandalfjava.core.messagecore.domain.CommandMessage;
import com.ditrit.gandalf.gandalfjava.core.messagecore.domain.EventMessage;
import com.ditrit.gandalf.gandalfjava.core.messagecore.domain.ResponseMessage;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.msgpack.jackson.dataformat.MessagePackFactory;
import org.zeromq.ZMsg;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class FactoryMessage {

    ObjectMapper objectMapper = new ObjectMapper(new MessagePackFactory());;

    protected String getString(ZMsg message) {
        return message.popString();
    }

    protected HashMap<String, String> getHashMap(ZMsg message) throws IOException {
        return objectMapper.readValue(message.popString(), new TypeReference<Map<String, String>>() {});
    }

    public CommandMessage getCommandByMessage(ZMsg message) throws IOException {
        CommandMessage commandMessage = new CommandMessage();
        commandMessage.setUuid(this.getString(message));
        commandMessage.setRouting(objectMapper.readValue(message.popString(), new TypeReference<Map<String, String>>() {}));
        commandMessage.setAccess(objectMapper.readValue(message.popString(), new TypeReference<Map<String, String>>() {}));
        commandMessage.setInfo(objectMapper.readValue(message.popString(), new TypeReference<Map<String, String>>() {}));

        return commandMessage;
    }

    public ResponseMessage getResponseByMessage(ZMsg message) throws IOException {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setUuid(this.getString(message));
        responseMessage.setRouting(objectMapper.readValue(message.popString(), new TypeReference<Map<String, String>>() {}));
        responseMessage.setAccess(objectMapper.readValue(message.popString(), new TypeReference<Map<String, String>>() {}));
        responseMessage.setInfo(objectMapper.readValue(message.popString(), new TypeReference<Map<String, String>>() {}));

        return responseMessage;
    }

    public EventMessage getEventByMessage(ZMsg message) throws IOException {
        EventMessage eventMessage = new EventMessage();
        eventMessage.setTopic(this.getString(message));
        eventMessage.setUuid(this.getString(message));
        eventMessage.setAccess(objectMapper.readValue(message.popString(), new TypeReference<Map<String, String>>() {}));
        eventMessage.setInfo(objectMapper.readValue(message.popString(), new TypeReference<Map<String, String>>() {}));

        return eventMessage;
    }
}
