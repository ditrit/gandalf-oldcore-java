package com.orness.gandalf.core.module.subscribertopicmodule.domain;

import com.google.gson.Gson;
import com.orness.gandalf.core.module.messagemodule.domain.MessageGandalf;
import com.orness.gandalf.core.module.zeromqmodule.event.subscriber.SubscriberZeroMQ;

import javax.persistence.*;

@Entity
public class Subscriber {

    private final String connection = "ipc://sub";
    //private final String connection = "tcp://*:11000";

    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String name;

    private SubscriberZeroMQ subscriberZeroMQ;
    //private ObjectMapper mapper;
    private Gson mapper;

    @ManyToOne
    @JoinColumn(name="topic_id", nullable=false)
    private Topic topic;

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

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

   /*public SubscriberZeroMQ getSubscriberZeroMQ() {
        return subscriberZeroMQ;
    }

    public void setSubscriberZeroMQ(SubscriberZeroMQ subscriberZeroMQ) {
        this.subscriberZeroMQ = subscriberZeroMQ;
    }*/

   public void startSubscriberZeroMQ() {
        this.subscriberZeroMQ.open(this.name);
   }

   public void stopSubscriberZeroMQ() {
        this.subscriberZeroMQ.close();
   }

   public MessageGandalf getSubscriberZeroMQMessage() {
       String header = this.subscriberZeroMQ.getZeroMQMessage();
       System.out.println("HEADER " + header);
       String content = this.subscriberZeroMQ.getZeroMQMessage();
       System.out.println("content " + content);
       if(header.equals(this.topic.getName())) {
           //mapper = new ObjectMapper();
           mapper = new Gson();

           MessageGandalf messageGandalf = null;
           //messageGandalf = mapper.readValue(content, MessageGandalf.class);
           messageGandalf = mapper.fromJson(content, MessageGandalf.class);

           return messageGandalf;
       }
       return null;
   }

    public Subscriber() { //JPA
    }

    public Subscriber(String name) {

       this.name = name;
    }

    public Subscriber(String name, Topic topic) {
        this.name = name;
        this.topic = topic;
        this.subscriberZeroMQ = new SubscriberZeroMQ(connection, topic.getName());
    }

    public Subscriber(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Subscriber{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
