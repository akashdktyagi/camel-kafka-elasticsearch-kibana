## Kafka and Docker compose Infra set Up

Docker Compose: () Due to some reason this was not wokring on my windows PC with WSl. On Mac it works)
* to use run ```docker-compose up```
* using the kafka setup from bitnami: https://bitnami.com/stack/kafka/containers
* using a kcat command line utility to test if the set up works: https://github.com/edenhill/kcat?ref=hackernoon.com

## For Windows:
Download Kafka from official place: https://kafka.apache.org/quickstart
* Run Zookeeper: .\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
* Run Kafka: bin/windows/kafka-server-start.bat config/server.properties
* Create Topic: bin/windows/kafka-topics.bat --create --topic quickstart-events1 --bootstrap-server localhost:9092
* Produce: bin/windows/kafka-console-producer.bat --topic quickstart-events --bootstrap-server localhost:9092
* UI Can be downloaded from here: https://github.com/obsidiandynamics/kafdrop
  * Clone and Build and run. See the instructions in the Readme file of the project. Use Java command line for executions.