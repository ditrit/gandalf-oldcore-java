package com.orness.gandalf.core.module.gandalfmodule.communication.event;

import com.google.gson.Gson;
import com.orness.gandalf.core.module.gandalfmodule.manager.GandalfGenericManager;
import com.orness.gandalf.core.module.zeromqmodule.command.domain.MessageCommandZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.event.worker.RunnableWorkerEventZeroMQ;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class GandalfWorkerEvent extends RunnableWorkerEventZeroMQ {

    private GandalfGenericManager gandalfGenericManager;

    public GandalfWorkerEvent(GandalfGenericManager gandalfGenericManager) {
        super();
        this.mapper = new Gson();
        this.gandalfGenericManager = gandalfGenericManager;
    }

    @Override
    public Object parse(String messageContent) {
        return null;
    }

    @Override
    public void command(MessageCommandZeroMQ messageCommandZeroMQ) {

        System.out.println("ID " + this.identity);
        System.out.println("SENDER " + messageCommandZeroMQ.getSender());
        System.out.println("RECEIVER " + messageCommandZeroMQ.getReceiver());
        System.out.println("TYPE_COMMAND " + messageCommandZeroMQ.getTypeCommand());
        System.out.println("COMMAND " + messageCommandZeroMQ.getCommand());

        Class gandalfGenericManagerClass = gandalfGenericManager.getClass();
        Method[] methods = gandalfGenericManagerClass.getDeclaredMethods();

        for(Method method : methods) {
            if(method.getName().contains("command_") && method.getName().equals(messageCommandZeroMQ.getTypeCommand().toString())) {
                try {
                    method.invoke(this.gandalfGenericManager, messageCommandZeroMQ.getCommand().toString());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
