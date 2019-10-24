package com.ditrit.gandalf.worker.workergandalf.functions;

import com.ditrit.gandalf.core.zeromqcore.worker.domain.Function;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public abstract class WorkerGandalfFunctions {

    private Map<String , Function> commands;
    private Map<String, Function> events;

    public Map<String, Function> getCommands() {
        return commands;
    }

    public void setCommands(Map<String, Function> commands) {
        this.commands = commands;
    }

    public Map<String, Function> getEvents() {
        return events;
    }

    public void setEvents(Map<String, Function> events) {
        this.events = events;
    }
}
