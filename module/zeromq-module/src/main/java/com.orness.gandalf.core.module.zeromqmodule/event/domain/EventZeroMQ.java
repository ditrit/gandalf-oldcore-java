package com.orness.gandalf.core.module.zeromqmodule.event.domain;

import org.zeromq.ZMQ;

public class EventZeroMQ {

    public static void publishEvent(ZMQ.Socket socket, String topic, String typeEvent, String event) {
        socket.sendMore(topic);
        socket.sendMore(typeEvent);
        socket.send(event);
    }

    public static void subscribeEvent(ZMQ.Socket socket, String topic) {
        socket.subscribe(topic.getBytes());
    }

    public static MessageEventZeroMQ getEvent(ZMQ.Socket socket) {
        return new MessageEventZeroMQ(socket);
    }

    public static MessageEventZeroMQ getEventByTopic(ZMQ.Socket socket, String topic) {
        MessageEventZeroMQ messageEventZeroMQ = null;
        String current_event_topic = socket.recvStr();
        if(current_event_topic.equals(topic)) {
            String typeEvent = socket.recvStr();
            String event = socket.recvStr();
            messageEventZeroMQ = new MessageEventZeroMQ(current_event_topic, typeEvent, event);
        }
        return messageEventZeroMQ;
    }
}