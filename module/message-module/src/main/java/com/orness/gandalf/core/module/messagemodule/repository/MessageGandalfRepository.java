package com.orness.gandalf.core.module.messagemodule.repository;

import com.orness.gandalf.core.module.messagemodule.domain.MessageGandalf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageGandalfRepository extends JpaRepository<MessageGandalf, Long> {

    MessageGandalf findBySender(String sender);
}
