package com.orness.gandalf.core.module.subscribertopicmodule.domain;

import com.orness.gandalf.core.module.messagebusmodule.domain.MessageBus;

import javax.persistence.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

@Entity
public class Topic {

    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String name;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "topic_subscriber", joinColumns = @JoinColumn(name = "topic_id"), inverseJoinColumns = @JoinColumn(name = "subscriber_id"))
    private Set<Subscriber> subscribers = new HashSet<>();

    //BUFFER
    private LinkedList<MessageBus> messageBusLinkedList = new LinkedList<>();

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

    public Set<Subscriber> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(Set<Subscriber> subscribers) {
        this.subscribers = subscribers;
    }

    public LinkedList<MessageBus> getMessageBusLinkedList() {
        return messageBusLinkedList;
    }

    public void setMessageBusLinkedList(LinkedList<MessageBus> messageBusLinkedList) {
        this.messageBusLinkedList = messageBusLinkedList;
    }

    public Topic() { //JPA
    }

    public Topic(String name) {
        this.id = id;
        this.name = name;
    }

    public Topic(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Topic{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
