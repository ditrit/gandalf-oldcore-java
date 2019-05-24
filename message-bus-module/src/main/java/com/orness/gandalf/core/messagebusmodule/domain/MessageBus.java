package com.orness.gandalf.core.messagebusmodule.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
public class MessageBus {

    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String topic;
    @Column
    private String sender;
    @Column
    private Timestamp expirationTime;
    @Column
    private Date creationDate;
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

    public Timestamp getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Timestamp expirationTime) {
        this.expirationTime = expirationTime;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MessageBus() { //JPA
    }

    public MessageBus(Long id, String topic, String sender, Timestamp expirationTime, Date creationDate, String content) {
        this.id = id;
        this.topic = topic;
        this.sender = sender;
        this.expirationTime = expirationTime;
        this.creationDate = creationDate;
        this.content = content;
    }

    public MessageBus(String topic, String sender, Timestamp expirationTime, Date creationDate, String content) {
        this.id = id;
        this.topic = topic;
        this.sender = sender;
        this.expirationTime = expirationTime;
        this.creationDate = creationDate;
        this.content = content;
    }

    public MessageBus(String topic, String sender, String expirationTime, String creationDate, String content) {
        this.id = id;
        this.topic = topic;
        this.sender = sender;
        this.expirationTime = Timestamp.valueOf(expirationTime);
        this.creationDate = Date.valueOf(creationDate);
        this.content = content;
    }

    @Override
    public String toString() {
        return "MessageBus{" +
                "id=" + id +
                ", topic='" + topic + '\'' +
                ", sender='" + sender + '\'' +
                ", expirationTime=" + expirationTime +
                ", creationDate=" + creationDate +
                ", content='" + content + '\'' +
                '}';
    }
}
