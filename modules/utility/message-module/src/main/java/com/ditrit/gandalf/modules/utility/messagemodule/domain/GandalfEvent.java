package com.ditrit.gandalf.modules.utility.messagemodule.domain;

import com.google.gson.Gson;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class GandalfEvent {

    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String topic;
    @Column
    private String typeEvent;
    @Column
    private String event;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTypeEvent() {
        return typeEvent;
    }

    public void setTypeEvent(String typeEvent) {
        this.typeEvent = typeEvent;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public GandalfEvent() { //JPA
    }

    public GandalfEvent(String topic, String typeEvent, String event) {
        this.topic = topic;
        this.typeEvent = typeEvent;
        this.event = event;
    }

    public GandalfEvent(Long id, String topic, String typeEvent, String event) {
        this.id = id;
        this.topic = topic;
        this.typeEvent = typeEvent;
        this.event = event;
    }

    public String toJson() {
        Gson mapper = new Gson();
        return mapper.toJson(this);
    }


    @Override
    public String toString() {
        return "GandalfEvent{" +
                "id=" + id +
                ", topic='" + topic + '\'' +
                ", typeEvent='" + typeEvent + '\'' +
                ", event='" + event + '\'' +
                '}';
    }
}
