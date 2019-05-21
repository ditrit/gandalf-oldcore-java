package com.orness.core.messagebusmodule.repository;

import com.orness.core.messagebusmodule.domain.MessageBus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageBusRepository extends JpaRepository<MessageBus, Long> {

    MessageBus findByWorkflowIdSender(String workflow_id);
}
