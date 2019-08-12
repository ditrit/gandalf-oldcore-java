package com.orness.gandalf.core.module.messagemodule.gandalf.repository;

import com.orness.gandalf.core.module.messagemodule.gandalf.domain.GandalfMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GandalfMessageRepository extends JpaRepository<GandalfMessage, Long> {

    GandalfMessage findBySender(String sender);
}
