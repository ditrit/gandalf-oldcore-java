package com.orness.gandalf.core.module.messagemodule.gandalf.domain;

import com.google.gson.Gson;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
//TODO USELESS ?
@Entity
public class GandalfMessage {

    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String topic;
    @Column
    private String sender;
    @Column
    private String content;

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

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public GandalfMessage() { //JPA
    }

    public GandalfMessage(Long id, String topic, String sender, String content) {
        this.id = id;
        this.topic = topic;
        this.sender = sender;
        this.content = content;
    }

    public GandalfMessage(String topic, String sender, String content) {
        this.id = id;
        this.topic = topic;
        this.sender = sender;
        this.content = content;
    }

    public String toJson() {
        Gson mapper = new Gson();
        return mapper.toJson(this);
    }

    @Override
    public String toString() {
        return "GandalfMessage{" +
                "id=" + id +
                ", topic='" + topic + '\'' +
                ", sender='" + sender + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
