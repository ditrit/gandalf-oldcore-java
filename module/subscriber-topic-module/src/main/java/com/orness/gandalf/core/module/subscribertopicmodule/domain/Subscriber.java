package com.orness.gandalf.core.module.subscribertopicmodule.domain;

import com.orness.gandalf.core.module.zeromqmodule.subscriber.SubscriberZeroMQ;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Subscriber {

    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String name;

    private SubscriberZeroMQ subscriberZeroMQ;

    @ManyToOne
    @JoinColumn(name="topic_id", nullable=false)
    private Topic topic;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

   /*public SubscriberZeroMQ getSubscriberZeroMQ() {
        return subscriberZeroMQ;
    }

    public void setSubscriberZeroMQ(SubscriberZeroMQ subscriberZeroMQ) {
        this.subscriberZeroMQ = subscriberZeroMQ;
    }*/

   public void startSubscriberZeroMQ() {

   }

   public void stopSubscriberZeroMQ() {

   }

    public Subscriber() { //JPA
    }

    public Subscriber(String name) {
        this.name = name;
    }

    public Subscriber(String name, Topic topic) {
        this.name = name;
        this.topic = topic;
    }

    public Subscriber(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Subscriber{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
