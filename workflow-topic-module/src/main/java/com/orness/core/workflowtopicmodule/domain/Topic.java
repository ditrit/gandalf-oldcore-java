package com.orness.core.workflowtopicmodule.domain;

import com.orness.core.messagebusmodule.domain.MessageBus;

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

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "topic_workflow", joinColumns = @JoinColumn(name = "topic_id"), inverseJoinColumns = @JoinColumn(name = "workflow_id"))
    private Set<Workflow> workflows = new HashSet<>();

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

    public Set<Workflow> getWorkflows() {
        return workflows;
    }

    public void setWorkflows(Set<Workflow> workflows) {
        this.workflows = workflows;
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
