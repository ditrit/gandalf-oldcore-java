package functions;

import com.ditrit.gandalf.gandalfjava.core.zeromqcore.constant.Constant;
import com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.domain.Function;
import org.zeromq.ZMsg;

public class FunctionListRepositories extends Function {
    @Override
    public Constant.Result executeCommand(ZMsg command) {
        return null;
    }

    @Override
    public void executeEvent(ZMsg event) {

    }
}
