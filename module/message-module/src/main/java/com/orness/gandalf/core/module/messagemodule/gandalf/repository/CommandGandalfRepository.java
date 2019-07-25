package com.orness.gandalf.core.module.messagemodule.gandalf.repository;

import com.orness.gandalf.core.module.messagemodule.gandalf.domain.CommandGandalf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommandGandalfRepository extends JpaRepository<CommandGandalf, Long> {
}
