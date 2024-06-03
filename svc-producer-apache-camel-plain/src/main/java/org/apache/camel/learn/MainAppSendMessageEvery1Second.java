package org.apache.camel.learn;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.component.ComponentsBuilderFactory;
import org.apache.camel.impl.DefaultCamelContext;

/**
 * A Camel Application
 */
public class MainAppSendMessageEvery1Second {

    /**
     * A main() so we can easily run these routing rules in our IDE
     */
    public static void main(String... args) throws Exception {
        CamelContext camelContext = new DefaultCamelContext();
        ComponentsBuilderFactory.kafka().register(camelContext, "kafka");
        camelContext.addRoutes(new RouteBuilder() {
            @Override
            public void configure() {
                from("timer:foo?period=1000")  // Generate a message every second
                        .setBody().simple("Test message at ${header.firedTime}")
                .to("kafka:uk-topic?brokers=localhost:9092");  // Publish to Kafka topic
            }
        });

        camelContext.start();
        Thread.sleep(1000000000);
        camelContext.stop();

    }

}

