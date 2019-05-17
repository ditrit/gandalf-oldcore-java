package com.orness.core.workflowtopicmodule.repository;

import com.orness.core.workflowtopicmodule.domain.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface TopicRepository extends JpaRepository<Topic, Long> {
}
