package core.producer;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import com.ditrit.gandalf.gandalfjava.core.workercore.properties.FunctionKafkaProperties;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConditionalOnBean(FunctionKafkaProperties.class)
public class FunctionKafkaProducerConfiguration {

    private FunctionKafkaProperties functionKafkaProperties;

    @Autowired
    public FunctionKafkaProducerConfiguration(FunctionKafkaProperties functionKafkaProperties) {
        this.functionKafkaProperties = functionKafkaProperties;
    }

    @Bean
    public ProducerFactory<String, Object> kafkaProducerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, this.functionKafkaProperties.getTargetEndPointConnection());
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(kafkaProducerFactory());
    }
}
