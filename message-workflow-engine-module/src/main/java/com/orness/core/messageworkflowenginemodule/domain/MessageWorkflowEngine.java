package com.orness.core.messageworkflowenginemodule.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
public class MessageWorkflowEngine {

    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String workflow_id_sender;
    @Column
    private String workflow_name;
    @Column
    private String workflow_topic;
    @Column
    private Timestamp workflow_expiration_time;
    @Column
    private Date workflow_creation_date;
    @Column
    private String workflow_content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWorkflow_id_sender() {
        return workflow_id_sender;
    }

    public void setWorkflow_id_sender(String workflow_id_sender) {
        this.workflow_id_sender = workflow_id_sender;
    }

    public String getWorkflow_name() {
        return workflow_name;
    }

    public void setWorkflow_name(String workflow_name) {
        this.workflow_name = workflow_name;
    }

    public String getWorkflow_topic() {
        return workflow_topic;
    }

    public void setWorkflow_topic(String workflow_topic) {
        this.workflow_topic = workflow_topic;
    }

    public Timestamp getWorkflow_expiration_time() {
        return workflow_expiration_time;
    }

    public void setWorkflow_expiration_time(Timestamp workflow_expiration_time) {
        this.workflow_expiration_time = workflow_expiration_time;
    }

    public Date getWorkflow_creation_date() {
        return workflow_creation_date;
    }

    public void setWorkflow_creation_date(Date workflow_creation_date) {
        this.workflow_creation_date = workflow_creation_date;
    }

    public String getWorkflow_content() {
        return workflow_content;
    }

    public void setWorkflow_content(String workflow_content) {
        this.workflow_content = workflow_content;
    }

    public MessageWorkflowEngine() { //JPA
    }

    public MessageWorkflowEngine(String workflow_id_sender, String workflow_name, String workflow_topic, Timestamp workflow_expiration_time, Date workflow_creation_date, String workflow_content) {
        this.workflow_id_sender = workflow_id_sender;
        this.workflow_name = workflow_name;
        this.workflow_topic = workflow_topic;
        this.workflow_expiration_time = workflow_expiration_time;
        this.workflow_creation_date = workflow_creation_date;
        this.workflow_content = workflow_content;
    }

    public MessageWorkflowEngine(String workflow_id_sender, String workflow_name, String workflow_topic, String workflow_expiration_time, String workflow_creation_date, String workflow_content) {
        this.workflow_id_sender = workflow_id_sender;
        this.workflow_name = workflow_name;
        this.workflow_topic = workflow_topic;
        this.workflow_expiration_time = Timestamp.valueOf(workflow_expiration_time);
        this.workflow_creation_date = Date.valueOf(workflow_creation_date);
        this.workflow_content = workflow_content;
    }

    public MessageWorkflowEngine(Long id, String workflow_id_sender, String workflow_name, String workflow_topic, Timestamp workflow_expiration_time, Date workflow_creation_date, String workflow_content) {
        this.id = id;
        this.workflow_id_sender = workflow_id_sender;
        this.workflow_name = workflow_name;
        this.workflow_topic = workflow_topic;
        this.workflow_expiration_time = workflow_expiration_time;
        this.workflow_creation_date = workflow_creation_date;
        this.workflow_content = workflow_content;
    }

    @Override
    public String toString() {
        return "MessageWorkflowEngine{" +
                "id=" + id +
                ", workflow_id_sender='" + workflow_id_sender + '\'' +
                ", workflow_name='" + workflow_name + '\'' +
                ", workflow_topic='" + workflow_topic + '\'' +
                ", workflow_expiration_time=" + workflow_expiration_time +
                ", workflow_creation_date=" + workflow_creation_date +
                ", workflow_content='" + workflow_content + '\'' +
                '}';
    }
}
