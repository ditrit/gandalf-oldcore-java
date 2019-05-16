package com.orness.core.messagekafkamodule.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class MessageKafka {

    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String workflow_id;
    @Column
    private String workflow_name;
    @Column
    private String workflow_topic;
    @Column
    private String workflow_content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWorkflow_id() {
        return workflow_id;
    }

    public void setWorkflow_id(String workflow_id) {
        this.workflow_id = workflow_id;
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

    public String getWorkflow_content() {
        return workflow_content;
    }

    public void setWorkflow_content(String workflow_content) {
        this.workflow_content = workflow_content;
    }

    public MessageKafka() { //JPA
    }

    public MessageKafka(String workflow_id, String workflow_name, String workflow_topic, String workflow_content) {
        this.workflow_id = workflow_id;
        this.workflow_name = workflow_name;
        this.workflow_topic = workflow_topic;
        this.workflow_content = workflow_content;
    }

    @Override
    public String toString() {
        return "MessageKafka{" +
                "id=" + id +
                ", workflow_id='" + workflow_id + '\'' +
                ", workflow_name='" + workflow_name + '\'' +
                ", workflow_topic='" + workflow_topic + '\'' +
                ", workflow_content='" + workflow_content + '\'' +
                '}';
    }
}
