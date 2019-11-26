package com.ditrit.gandalf.gandalfjava.functions.functionsgandalfadmin.functions;

import com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.domain.CommandState;
import com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.domain.Function;
import com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.domain.ReferenceState;
import org.zeromq.ZMsg;

import java.util.List;

public class FunctionStop extends Function {

    FunctionStop() {

    }

    @Override
    public String executeCommand(ZMsg command, List<CommandState> commandStates, ReferenceState referenceState) {
        return null;
    }

    @Override
    public void executeEvent(ZMsg event) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
