package com.ditrit.gandalf.gandalfjava.functions.functionsgandalfadmin.functions;

import com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.domain.CommandFunction;
import com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.domain.CommandState;
import com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.domain.ReferenceState;
import org.zeromq.ZMsg;

import java.util.List;

public class FunctionStart extends CommandFunction {

    public FunctionStart() {

    }

    @Override
    public String executeCommand(ZMsg command, List<CommandState> commandStates, ReferenceState referenceState) {
        return null;
    }
}
