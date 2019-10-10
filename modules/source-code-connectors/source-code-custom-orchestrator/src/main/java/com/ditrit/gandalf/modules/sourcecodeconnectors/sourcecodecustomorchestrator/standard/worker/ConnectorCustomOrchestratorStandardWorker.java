package com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodecustomorchestrator.standard.worker;

import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodecustomorchestrator.standard.manager.ConnectorCustomOrchestratorStandardManager;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodecustomorchestrator.properties.ConnectorCustomOrchestratorProperties;
import com.ditrit.gandalf.core.zeromqcore.command.domain.MessageCommand;
import com.ditrit.gandalf.core.zeromqcore.constant.Constant;
import com.ditrit.gandalf.core.zeromqcore.worker.RunnableWorkerZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;
import org.zeromq.ZMsg;

import static com.ditrit.gandalf.core.connectorcore.constant.ConnectorConstant.WORKER_SERVICE_CLASS_STANDARD;

@Component(value = "standardWorker")
@ConditionalOnBean(ConnectorCustomOrchestratorProperties.class)
public class ConnectorCustomOrchestratorStandardWorker extends RunnableWorkerZeroMQ {

    private ConnectorCustomOrchestratorStandardManager connectorCustomOrchestratorStandardManager;
    private ConnectorCustomOrchestratorProperties connectorCustomOrchestratorProperties;
    private MessageCommand messageCommand;

    @Autowired
    public ConnectorCustomOrchestratorStandardWorker(ConnectorCustomOrchestratorProperties connectorCustomOrchestratorProperties, ConnectorCustomOrchestratorStandardManager connectorCustomOrchestratorStandardManager) {
        super();
        this.connectorCustomOrchestratorStandardManager = connectorCustomOrchestratorStandardManager;
        this.connectorCustomOrchestratorProperties = connectorCustomOrchestratorProperties;
        this.initRunnable(WORKER_SERVICE_CLASS_STANDARD, this.connectorCustomOrchestratorProperties.getConnectorCommandBackEndReceiveConnection(), this.connectorCustomOrchestratorProperties.getConnectorEventBackEndReceiveConnection(), null);
    }

    //TODO PAYLOAD
    @Override
    protected Constant.Result executeRoutingWorkerCommand(ZMsg command) {
        this.messageCommand = new MessageCommand(command);
        boolean resultManager;
        Constant.Result result = Constant.Result.FAIL;
        switch (messageCommand.getCommand()) {
            case "REGISTER":
                resultManager = this.connectorCustomOrchestratorStandardManager.register(messageCommand.getPayload());
                result = resultManager ? Constant.Result.SUCCESS : Constant.Result.FAIL;
                break;
            case "UNREGISTER":
                resultManager = this.connectorCustomOrchestratorStandardManager.unregister(messageCommand.getPayload());
                result = resultManager ? Constant.Result.SUCCESS : Constant.Result.FAIL;
                break;
            case "DEPLOY":
                resultManager = this.connectorCustomOrchestratorStandardManager.deploy(messageCommand.getPayload());
                result = resultManager ? Constant.Result.SUCCESS : Constant.Result.FAIL;
                break;
            case "UNDEPLOY":
                resultManager = this.connectorCustomOrchestratorStandardManager.undeploy(messageCommand.getPayload());
                result = resultManager ? Constant.Result.SUCCESS : Constant.Result.FAIL;
                break;
            case "START":
                resultManager = this.connectorCustomOrchestratorStandardManager.start(messageCommand.getPayload());
                result = resultManager ? Constant.Result.SUCCESS : Constant.Result.FAIL;
                break;
            case "STOP":
                resultManager = this.connectorCustomOrchestratorStandardManager.stop(messageCommand.getPayload());
                result = resultManager ? Constant.Result.SUCCESS : Constant.Result.FAIL;
                break;
            case "SCALE_UP":
                resultManager = this.connectorCustomOrchestratorStandardManager.scaleUp(messageCommand.getPayload());
                result = resultManager ? Constant.Result.SUCCESS : Constant.Result.FAIL;
                break;
            case "SCALE_DOWN":
                resultManager = this.connectorCustomOrchestratorStandardManager.scaleDown(messageCommand.getPayload());
                result = resultManager ? Constant.Result.SUCCESS : Constant.Result.FAIL;
                break;
            case "DOWNLOAD":
                resultManager = this.connectorCustomOrchestratorStandardManager.downloadProject(messageCommand.getPayload());
                result = resultManager ? Constant.Result.SUCCESS : Constant.Result.FAIL;
                break;
            default:
                break;
        }
        return result;
    }

    @Override
    protected void executeRoutingSubscriberCommand(ZMsg command) {

    }
}

