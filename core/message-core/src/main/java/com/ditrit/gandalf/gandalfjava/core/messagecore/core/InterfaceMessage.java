package com.ditrit.gandalf.gandalfjava.core.messagecore.core;

import org.zeromq.ZMQ;

public interface InterfaceMessage {

    void sendWith(ZMQ.Socket socket, String routingInfo) throws Exception;
}
