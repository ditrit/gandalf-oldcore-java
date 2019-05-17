package com.orness.core.messageworkflowenginemodule.Repository;

import com.orness.core.messageworkflowenginemodule.domain.MessageWorkflowEngine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageWorkflowEngineRepository extends JpaRepository<MessageWorkflowEngine, Long> {
}
