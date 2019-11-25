package functions;

import com.ditrit.gandalf.gandalfjava.core.zeromqcore.constant.Constant;
import com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.domain.Function;
import org.zeromq.ZMsg;

public class FunctionStart extends Function {

    public FunctionStart() {

    }

    @Override
    public Constant.Result executeCommand(ZMsg command) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void executeEvent(ZMsg event) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
