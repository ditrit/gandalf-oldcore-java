package com.orness.core.workflowtopicmodule.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Workflow {

    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String name;
    @Column
    private int index = 0;

    @ManyToMany(mappedBy = "workflows")
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

    public Workflow() { //JPA
    }

    public Workflow(String name) {
        this.id = id;
        this.name = name;
    }

    public Workflow(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Workflow{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", index=" + index +
                '}';
    }
}
