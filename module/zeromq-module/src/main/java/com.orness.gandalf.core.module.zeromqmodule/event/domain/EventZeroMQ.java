package com.orness.gandalf.core.module.zeromqmodule.event.domain;

import org.zeromq.ZMQ;

public class EventZeroMQ {

    public static void publishEvent(ZMQ.Socket socket, String topic, String typeEvent, String event) {
        socket.sendMore(topic);
        socket.sendMore(typeEvent);
        socket.send(event);
    }

    public static ResponseEventZeroMQ subscribeEvent(ZMQ.Socket socket) {
        return new ResponseEventZeroMQ(socket);
    }
}
