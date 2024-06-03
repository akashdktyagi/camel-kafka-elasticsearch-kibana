Camel Java Router Project
=========================

=== How to build

To build this project use

    mvn install

=== How to run

You can run this example using

    mvn camel:run

=== More information

You can find more information about Apache Camel at the website: http://camel.apache.org/

Run this to publish the message to a Kafka Cluster every 1 second:

[MainAppSendMessageEvery1Second.java](src%2Fmain%2Fjava%2Forg%2Fapache%2Fcamel%2Flearn%2FMainAppSendMessageEvery1Second.java)


```java

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

```