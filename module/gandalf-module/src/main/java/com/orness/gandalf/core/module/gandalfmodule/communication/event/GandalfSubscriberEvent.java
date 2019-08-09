package com.orness.gandalf.core.module.gandalfmodule.communication.event;

import com.orness.gandalf.core.module.zeromqmodule.event.domain.MessageEventZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.event.subscriber.RunnableSubscriberZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.event.worker.RunnableWorkerEventZeroMQ;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class GandalfSubscriberEvent extends RunnableSubscriberZeroMQ {

    private List<GandalfClientEvent> listGandalfClientEvent;
    private List<RunnableWorkerEventZeroMQ> listGandalfWorkerEvent;
    private GandalfClientEvent gandalfClientEvent;
    private ClassLoader classLoader;
    private String connectionClient;
    private String connectionWorker;

    public GandalfSubscriberEvent(String topic, String connection, String connectionClient, String connectionWorker) {
        super();
        this.listGandalfClientEvent = new ArrayList<>();
        this.listGandalfWorkerEvent = new ArrayList<>();
        this.classLoader = GandalfSubscriberEvent.class.getClassLoader();
        this.connectionClient = connectionClient;
        this.connectionWorker = connectionWorker;

        //SUBS
        this.connect(connection);
        this.subscribe(topic);

        //CLIENT EVENT
        this.gandalfClientEvent = new GandalfClientEvent(connectionClient);
    }

    public void addEventWorker(String gandalfWorkerEventClass) {
        Class workerClass = null;
        RunnableWorkerEventZeroMQ worker;
        try {
            workerClass = classLoader.loadClass(gandalfWorkerEventClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            worker = (RunnableWorkerEventZeroMQ) workerClass.getDeclaredConstructor(this.createConstructorArgs()).newInstance(connectionWorker);
            this.listGandalfWorkerEvent.add(worker);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private Class[] createConstructorArgs() {
        Class[] cArg = new Class[1];
        cArg[0] = String.class;
        return cArg;
    }

    @Override
    protected void sendMessageEventZeroMQ(MessageEventZeroMQ messageEventZeroMQ) {
        System.out.println("BLIP");
        System.out.println(messageEventZeroMQ);
        this.gandalfClientEvent.sendEventCommand(messageEventZeroMQ.getTypeEvent(), messageEventZeroMQ.getEvent());
    }
}
