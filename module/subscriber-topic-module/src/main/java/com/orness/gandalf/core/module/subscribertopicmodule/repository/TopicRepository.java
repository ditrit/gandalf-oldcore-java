package com.orness.gandalf.core.module.subscribertopicmodule.repository;

import com.orness.gandalf.core.module.subscribertopicmodule.domain.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface TopicRepository extends JpaRepository<Topic, Long> {
}
