package com.orness.gandalf.core.library.gandalfjavaclient.core;

import com.orness.gandalf.core.library.gandalfjavaclient.properties.GandalfClientProperties;
import com.orness.gandalf.core.module.zeromqcore.event.client.PublisherZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GandalfPublisherZeroMQ extends PublisherZeroMQ {

    private GandalfClientProperties gandalfClientProperties;

    @Autowired
    public GandalfPublisherZeroMQ(GandalfClientProperties gandalfClientProperties) {
        super();
        this.gandalfClientProperties = gandalfClientProperties;
        this.init(this.gandalfClientProperties.getName(), this.gandalfClientProperties.getPublisherConnection());
    }
}
