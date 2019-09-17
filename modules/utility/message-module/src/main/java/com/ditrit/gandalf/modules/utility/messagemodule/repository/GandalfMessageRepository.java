package com.ditrit.gandalf.modules.utility.messagemodule.repository;

import com.ditrit.gandalf.modules.utility.messagemodule.domain.GandalfMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GandalfMessageRepository extends JpaRepository<GandalfMessage, Long> {

    GandalfMessage findBySender(String sender);
}
