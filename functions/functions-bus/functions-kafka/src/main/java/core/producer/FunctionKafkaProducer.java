package core.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import com.ditrit.gandalf.gandalfjava.core.workercore.properties.FunctionKafkaProperties;

@Component
@ConditionalOnBean(FunctionKafkaProperties.class)
public class FunctionKafkaProducer {

    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    public FunctionKafkaProducer(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendKafka(String topic, Object message) {
        kafkaTemplate.send(topic, message);
    }
}
