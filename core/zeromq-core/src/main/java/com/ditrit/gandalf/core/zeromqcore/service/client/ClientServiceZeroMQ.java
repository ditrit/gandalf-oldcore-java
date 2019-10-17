package com.ditrit.gandalf.core.zeromqcore.service.client;

import com.ditrit.gandalf.core.zeromqcore.constant.Constant;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

public class ClientServiceZeroMQ {

    protected ZContext context;
    protected ZMQ.Socket serviceClient;
    private String serviceClientConnection;
    protected String identity;


    protected void init(String identity, String serviceClientConnection) {
        this.context = new ZContext();
        this.identity = identity;

        //Broker
        this.serviceClient = this.context.createSocket(SocketType.REQ);
        this.serviceClient.setIdentity(this.identity.getBytes(ZMQ.CHARSET));
        this.serviceClientConnection = serviceClientConnection;
        System.out.println("ClientServiceZeroMQ connect to: " + this.serviceClientConnection);
        this.serviceClient.connect(this.serviceClientConnection);
    }

    public void close() {
        this.serviceClient.close();
        this.context.close();
    }

    public ZMsg sendCommand(String uuid, String connector, String serviceClass, String command, String payload) {
        this.serviceClient.sendMore(uuid);
        this.serviceClient.sendMore(this.identity);
        this.serviceClient.sendMore(connector);
        this.serviceClient.sendMore(serviceClass);
        this.serviceClient.sendMore(command);
        this.serviceClient.send(payload);
        return this.getCommandResult();
    }

    private ZMsg getCommandResult() {
        boolean more = false;

        ZMsg response = ZMsg.recvMsg(this.serviceClient);
        more = this.serviceClient.hasReceiveMore();
        System.out.println(response);
        System.out.println(more);

        return response;
    }
}
