package com.orness.gandalf.core.module.subscribertopicmodule.domain;

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
    @Column
    private int index = 0;

    @ManyToMany(mappedBy = "subscribers")
    private Set<Topic> topics = new HashSet<>();

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

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Set<Topic> getTopics() {
        return topics;
    }

    public void setTopics(Set<Topic> topics) {
        this.topics = topics;
    }

    public Subscriber() { //JPA
    }

    public Subscriber(String name) {
        this.name = name;
    }

    public Subscriber(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void IncrementIndex() {
        this.setIndex(this.getIndex()+1);
    }

    @Override
    public String toString() {
        return "subscriber{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", index=" + index +
                '}';
    }
}
