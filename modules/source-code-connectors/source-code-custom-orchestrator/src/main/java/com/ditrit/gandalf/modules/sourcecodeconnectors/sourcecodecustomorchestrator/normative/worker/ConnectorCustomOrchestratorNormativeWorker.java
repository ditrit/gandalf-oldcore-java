package com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodecustomorchestrator.normative.worker;

import com.ditrit.gandalf.core.connectorcore.properties.ConnectorProperties;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodecustomorchestrator.normative.manager.ConnectorCustomOrchestratorNormativeManager;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodecustomorchestrator.properties.ConnectorCustomOrchestratorProperties;
import com.ditrit.gandalf.core.zeromqcore.command.domain.MessageCommand;
import com.ditrit.gandalf.core.zeromqcore.constant.Constant;
import com.ditrit.gandalf.core.zeromqcore.worker.RunnableWorkerZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;
import org.zeromq.ZMsg;

import static com.ditrit.gandalf.core.connectorcore.constant.ConnectorConstant.WORKER_SERVICE_CLASS_NORMATIVE;

@Component(value = "normativeWorker")
@ConditionalOnBean(ConnectorCustomOrchestratorProperties.class)
public class ConnectorCustomOrchestratorNormativeWorker extends RunnableWorkerZeroMQ {

    private ConnectorCustomOrchestratorNormativeManager connectorCustomOrchestratorNormativeManager;
    private ConnectorProperties connectorProperties;
    private ConnectorCustomOrchestratorProperties connectorCustomOrchestratorProperties;
    private MessageCommand messageCommand;

    @Autowired
    public ConnectorCustomOrchestratorNormativeWorker(ConnectorProperties connectorProperties, ConnectorCustomOrchestratorProperties connectorCustomOrchestratorProperties, ConnectorCustomOrchestratorNormativeManager connectorCustomOrchestratorNormativeManager) {
        super();
        this.connectorCustomOrchestratorNormativeManager = connectorCustomOrchestratorNormativeManager;
        this.connectorProperties = connectorProperties;
        this.connectorCustomOrchestratorProperties = connectorCustomOrchestratorProperties;
        this.initRunnable(WORKER_SERVICE_CLASS_NORMATIVE, this.connectorProperties.getConnectorCommandBackEndConnection(), this.connectorProperties.getConnectorEventBackEndConnection(), this.connectorCustomOrchestratorProperties.getTopics());
    }

    //TODO PAYLOAD
    @Override
    protected Constant.Result executeRoutingWorkerCommand(ZMsg command) {
        this.messageCommand = new MessageCommand(command);
        boolean resultManager;
        Constant.Result result = Constant.Result.FAIL;
        switch (messageCommand.getCommand()) {
            case "REGISTER":
                resultManager = this.connectorCustomOrchestratorNormativeManager.register(messageCommand.getPayload());
                result = resultManager ? Constant.Result.SUCCESS : Constant.Result.FAIL;
                break;
            case "UNREGISTER":
                resultManager = this.connectorCustomOrchestratorNormativeManager.unregister(messageCommand.getPayload());
                result = resultManager ? Constant.Result.SUCCESS : Constant.Result.FAIL;
                break;
            case "DEPLOY":
                resultManager = this.connectorCustomOrchestratorNormativeManager.deploy(messageCommand.getPayload());
                result = resultManager ? Constant.Result.SUCCESS : Constant.Result.FAIL;
                break;
            case "UNDEPLOY":
                resultManager = this.connectorCustomOrchestratorNormativeManager.undeploy(messageCommand.getPayload());
                result = resultManager ? Constant.Result.SUCCESS : Constant.Result.FAIL;
                break;
            case "START":
                resultManager = this.connectorCustomOrchestratorNormativeManager.start(messageCommand.getPayload());
                result = resultManager ? Constant.Result.SUCCESS : Constant.Result.FAIL;
                break;
            case "STOP":
                resultManager = this.connectorCustomOrchestratorNormativeManager.stop(messageCommand.getPayload());
                result = resultManager ? Constant.Result.SUCCESS : Constant.Result.FAIL;
                break;
            case "SCALE_UP":
                resultManager = this.connectorCustomOrchestratorNormativeManager.scaleUp(messageCommand.getPayload());
                result = resultManager ? Constant.Result.SUCCESS : Constant.Result.FAIL;
                break;
            case "SCALE_DOWN":
                resultManager = this.connectorCustomOrchestratorNormativeManager.scaleDown(messageCommand.getPayload());
                result = resultManager ? Constant.Result.SUCCESS : Constant.Result.FAIL;
                break;
            case "DOWNLOAD":
                resultManager = this.connectorCustomOrchestratorNormativeManager.downloadProject(messageCommand.getPayload());
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

