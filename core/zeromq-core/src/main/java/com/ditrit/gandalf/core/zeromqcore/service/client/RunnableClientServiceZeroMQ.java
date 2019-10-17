package com.ditrit.gandalf.core.zeromqcore.service.client;

import org.zeromq.ZMsg;

public abstract class RunnableClientServiceZeroMQ extends ClientServiceZeroMQ implements Runnable {

    public RunnableClientServiceZeroMQ() {
        super();
    }

    protected void initRunnable(String identity, String serviceClientConnection) {
        this.init(identity, serviceClientConnection);
    }

    //TODO SYNC ASYNC
    public abstract ZMsg sendRequest(Object request);

    private ZMsg getCommandResult() {
        boolean more = false;

        ZMsg response = ZMsg.recvMsg(this.serviceClient);
        more = this.serviceClient.hasReceiveMore();
        System.out.println(response);
        System.out.println(more);

        return response;
    }

    @Override
    public void run() {
        //TODO
    }
}
