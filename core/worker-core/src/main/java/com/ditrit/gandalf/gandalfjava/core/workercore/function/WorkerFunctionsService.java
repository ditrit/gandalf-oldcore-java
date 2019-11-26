package com.ditrit.gandalf.gandalfjava.core.workercore.function;

import com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.domain.Function;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.zeromq.ZMsg;

import java.util.Map;

@Service
@Scope("singleton")
public class WorkerFunctionsService {

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

    public Function getFunctionByCommand(ZMsg commandExecute) {
        Function function = null;
        function = this.getCommands().get(commandExecute.toArray()[6]);
        return function;
    }

    public Function getFunctionByEvent(ZMsg eventExecute) {
        Function function = null;
        function = this.getEvents().get(eventExecute.toArray()[1]);
        return function;
    }

    public void addFunctionCommand(String command, Function function) {
        if(!this.getCommands().containsKey(command)) {
            this.getCommands().put(command, function);
        }
    }

    public void addFunctionEvent(String event, Function function) {
        if(!this.getEvents().containsKey(event)) {
            this.getEvents().put(event, function);
        }
    }
}
