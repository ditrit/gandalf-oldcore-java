package com.ditrit.gandalf.core.listenercore;

import com.ditrit.gandalf.core.listenercore.properties.LibraryListenerProperties;
import com.ditrit.gandalf.core.zeromqcore.command.listener.ThreadListenerCommandZeroMQ;
import com.ditrit.gandalf.core.zeromqcore.event.listener.ThreadListenerEventZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zeromq.ZMsg;

@Component(value = "libraryListener")
public class LibraryListener {

    private LibraryListenerProperties libraryListenerProperties;
    private ThreadListenerCommandZeroMQ threadListenerCommandZeroMQ;
    private ThreadListenerEventZeroMQ threadListenerEventZeroMQ;

    @Autowired
    public LibraryListener(LibraryListenerProperties libraryListenerProperties) {
        this.libraryListenerProperties = libraryListenerProperties;
        this.threadListenerCommandZeroMQ = new ThreadListenerCommandZeroMQ(this.libraryListenerProperties.getConnectorName(), this.libraryListenerProperties.getListenerCommandBackEndConnections());
        this.threadListenerEventZeroMQ = new ThreadListenerEventZeroMQ(this.libraryListenerProperties.getConnectorName(), this.libraryListenerProperties.getListenerEventBackEndConnection());
    }

    public ZMsg getEventSync() {
        return this.threadListenerEventZeroMQ.getEventSync();
    }

    public ZMsg getEventAsync() {
        if(this.threadListenerEventZeroMQ.isInterrupted()) {
            this.threadListenerEventZeroMQ.start();
        }
        return this.threadListenerEventZeroMQ.getEventAsync();
    }

    public ZMsg getCommandSync() {
        return this.threadListenerCommandZeroMQ.getCommandSync();
    }

    public ZMsg getCommandAsync() {
        if(this.threadListenerCommandZeroMQ.isInterrupted()) {
            this.threadListenerCommandZeroMQ.start();
        }
        return this.threadListenerCommandZeroMQ.getCommandAsync();
    }

    public void sendCommandResult(ZMsg command, boolean result) {
        this.threadListenerCommandZeroMQ.sendResultCommand(command, result);
    }
}
