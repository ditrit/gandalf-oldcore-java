# Gandalf Core Architecture

## Configuration
### configuration-service
Service gérant les configurations des différents services.
### eureka-service
Service discovery Eureka

## Connector
### connector-bus-service
Service permettant la communication avec le bus via gRPC.
### connector-workflow-engine-service
Service permettant la communication avec le workflow-engine via gRPC.
### database-bus-service
Service permettant la sauvegarde des différents message du bus.

## Library
### grpc-message-bus-module
Module contenant le fichier .proto pour le connector-bus-service
### grpc-message-workflow-engine-module
Module contenant le fichier .proto pour le connector-workflow-engine-service
### message-module
### subscriber-topic-module
### workflow-uid-module

## Service
### workflow-service
Service permettant la communication avec le workflow-engine Zeebe
### workflow-uid-service

## Test
### java-kafka-1
Test tâche externe Java numéro 1 
### java-kafka-2
Test tâche externe Java numéro 2
### job-kafka-producer
Test job Zeebe de production de message dans Kafka
### job-print
Test job Zeebe d'affichage

# Gandalf Core Test

![Schéma test](/picture/Test.png)


- 1 Lancement de Zeebe/Zookeeper/Kafka
- 2 Execution de configuration-service et eureka-service
- 3 Execution de connector-bus-service/connector-workflow-engine-service/database-bus-service

- 4 Execution job-print/job-kafka-producer
- 5 Execution java-kafka-1/java-kafka-2
- 6 Execution workflow-service (CLR)
    - Deploiment des 3 workflows
    - Instanciation des 3 workflows

- 7 Enjoy
