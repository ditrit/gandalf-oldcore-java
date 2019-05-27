package com.orness.gandalf.core.module.messagebusmodule.repository;

import com.orness.gandalf.core.module.messagebusmodule.domain.MessageBus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageBusRepository extends JpaRepository<MessageBus, Long> {

    MessageBus findBySender(String sender);
}
