package com.orness.core.messagebusmodule.domain;

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
    private String workflowIdSender;
    @Column
    private String workflowName;
    @Column
    private String workflowTopic;
    @Column
    private Timestamp workflowExpirationTime;
    @Column
    private Date workflowCreationDate;
    @Column
    private String workflowContent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWorkflowIdSender() {
        return workflowIdSender;
    }

    public void setWorkflowIdSender(String workflowIdSender) {
        this.workflowIdSender = workflowIdSender;
    }

    public String getWorkflowName() {
        return workflowName;
    }

    public void setWorkflowName(String workflowName) {
        this.workflowName = workflowName;
    }

    public String getWorkflowTopic() {
        return workflowTopic;
    }

    public void setWorkflowTopic(String workflowTopic) {
        this.workflowTopic = workflowTopic;
    }

    public Timestamp getWorkflowExpirationTime() {
        return workflowExpirationTime;
    }

    public void setWorkflowExpirationTime(Timestamp workflowExpirationTime) {
        this.workflowExpirationTime = workflowExpirationTime;
    }

    public Date getWorkflowCreationDate() {
        return workflowCreationDate;
    }

    public void setWorkflowCreationDate(Date workflowCreationDate) {
        this.workflowCreationDate = workflowCreationDate;
    }

    public String getWorkflowContent() {
        return workflowContent;
    }

    public void setWorkflowContent(String workflowContent) {
        this.workflowContent = workflowContent;
    }

    public MessageBus() { //JPA
    }

    public MessageBus(Long id, String workflowIdSender, String workflowName, String workflowTopic, Timestamp workflowExpirationTime, Date workflowCreationDate, String workflowContent) {
        this.id = id;
        this.workflowIdSender = workflowIdSender;
        this.workflowName = workflowName;
        this.workflowTopic = workflowTopic;
        this.workflowExpirationTime = workflowExpirationTime;
        this.workflowCreationDate = workflowCreationDate;
        this.workflowContent = workflowContent;
    }

    public MessageBus(String workflowIdSender, String workflowName, String workflowTopic, Timestamp workflowExpirationTime, Date workflowCreationDate, String workflowContent) {
        this.workflowIdSender = workflowIdSender;
        this.workflowName = workflowName;
        this.workflowTopic = workflowTopic;
        this.workflowExpirationTime = workflowExpirationTime;
        this.workflowCreationDate = workflowCreationDate;
        this.workflowContent = workflowContent;
    }

    public MessageBus(String workflowIdSender, String workflowName, String workflowTopic, String workflowExpirationTime, String workflowCreationDate, String workflowContent) {
        this.workflowIdSender = workflowIdSender;
        this.workflowName = workflowName;
        this.workflowTopic = workflowTopic;
        this.workflowExpirationTime =  Timestamp.valueOf(workflowExpirationTime);
        this.workflowCreationDate =  Date.valueOf(workflowCreationDate);
        this.workflowContent = workflowContent;
    }

    @Override
    public String toString() {
        return "MessageBus{" +
                "id=" + id +
                ", workflowIdSender='" + workflowIdSender + '\'' +
                ", workflowName='" + workflowName + '\'' +
                ", workflowTopic='" + workflowTopic + '\'' +
                ", workflowExpirationTime=" + workflowExpirationTime +
                ", workflowCreationDate=" + workflowCreationDate +
                ", workflowContent='" + workflowContent + '\'' +
                '}';
    }
}
