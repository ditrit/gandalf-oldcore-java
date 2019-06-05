package com.orness.gandalf.core.module.subscribertopicmodule.domain;

import com.orness.gandalf.core.module.messagemodule.domain.MessageGandalf;

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

    @OneToMany(mappedBy="topic")
    private Set<Subscriber> subscribers = new HashSet<>();

    //BUFFER
    private LinkedList<MessageGandalf> messageLinkedList = new LinkedList<>();

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

    public LinkedList<MessageGandalf> getMessageLinkedList() {
        return messageLinkedList;
    }

    public void setMessageLinkedList(LinkedList<MessageGandalf> messageLinkedList) {
        this.messageLinkedList = messageLinkedList;
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
