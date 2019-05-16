package com.orness.core.messagekafkamodule.repository;

import com.orness.core.messagekafkamodule.domain.MessageKafka;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageKafkaRepository extends JpaRepository<MessageKafka, Long> {

    MessageKafka findByWorkflowId(String workflow_id);
}
