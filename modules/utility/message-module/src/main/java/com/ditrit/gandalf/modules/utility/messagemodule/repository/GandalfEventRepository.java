package com.ditrit.gandalf.modules.utility.messagemodule.repository;

import com.ditrit.gandalf.modules.utility.messagemodule.domain.GandalfEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GandalfEventRepository extends JpaRepository<GandalfEvent, Long> {
}
