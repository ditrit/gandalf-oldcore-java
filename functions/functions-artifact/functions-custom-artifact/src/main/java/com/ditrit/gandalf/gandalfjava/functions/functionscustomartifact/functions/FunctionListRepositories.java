package com.ditrit.gandalf.gandalfjava.functions.functionscustomartifact.functions;

import com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.domain.CommandState;
import com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.domain.Function;
import com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.domain.ReferenceState;
import org.zeromq.ZMsg;

import java.util.List;

public class FunctionListRepositories extends Function {

    @Override
    public String executeCommand(ZMsg command, List<CommandState> commandStates, ReferenceState referenceState) {
        return null;
    }
    @Override
    public void executeEvent(ZMsg event) {

    }
}
