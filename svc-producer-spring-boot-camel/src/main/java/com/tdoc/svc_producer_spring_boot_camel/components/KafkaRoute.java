package com.tdoc.svc_producer_spring_boot_camel.components;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class KafkaRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("timer:foo?period=5000")
            .setBody().constant("Hello from Camel Kafka Producer")
            .to("log:bar")
            .log("Message sent to Kafka topic my-topic");
    }
}
