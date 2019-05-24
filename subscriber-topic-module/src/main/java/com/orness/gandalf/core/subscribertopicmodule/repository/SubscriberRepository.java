package com.orness.gandalf.core.subscribertopicmodule.repository;

import com.orness.gandalf.core.subscribertopicmodule.domain.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {
}
