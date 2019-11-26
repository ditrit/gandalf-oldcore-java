package com.ditrit.gandalf.gandalfjava.core.workercore.function;

import com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.domain.CommandFunction;
import com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.domain.EventFunction;
import com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.domain.ThreadFunction;
import com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.domain.ThreadFunction;import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.zeromq.ZMsg;

import java.util.Map;

@Service
@Scope("singleton")
public class WorkerFunctionsService {

    private Map<String , CommandFunction> commands;
    private Map<String, EventFunction> events;

    public Map<String, CommandFunction> getCommands() {
        return commands;
    }

    public void setCommands(Map<String, CommandFunction> commands) {
        this.commands = commands;
    }

    public Map<String, EventFunction> getEvents() {
        return events;
    }

    public void setEvents(Map<String, EventFunction> events) {
        this.events = events;
    }

    public CommandFunction getFunctionByCommand(ZMsg commandExecute) {
        CommandFunction function = null;
        function = this.getCommands().get(commandExecute.toArray()[6]);
        return function;
    }

    public EventFunction getFunctionByEvent(ZMsg eventExecute) {
        EventFunction function = null;
        function = this.getEvents().get(eventExecute.toArray()[1]);
        return function;
    }

    public void addFunctionCommand(String command, CommandFunction function) {
        if(!this.getCommands().containsKey(command)) {
            this.getCommands().put(command, function);
        }
    }

    public void addFunctionEvent(String event, EventFunction function) {
        if(!this.getEvents().containsKey(event)) {
            this.getEvents().put(event, function);
        }
    }
}
