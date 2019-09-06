# Gandalf Core Architecture 

## Architecture
![Schéma architecture gandalf](/picture/architecture_gandalf.png)

## Communication 
![Schéma architecture connecteur](/picture/architecture_communication.png)

## Configuration
Configuration
### configuration-service (DEV)
Service gérant les configurations des différents services.
### Consul KV Store (PROD)

## Discovery
Services discovery
### eureka-service (DEV)
Service discovery Eureka
### Consul service discovery (PROD)
Consul service discovery


## Connector
Connecteurs Gandalf et outils

![Schéma architecture connecteur](/picture/architecture_connector.png)

### connector-gandalf-service
Service central Gandalf
- gandalf-java-client
- gandalf-module

### connector-bus-service
Service permettant la communication avec un bus via Gandalf.
- gandalf-java-client
- gandalf-module
- kafka-module, etc...
 
### connector-workflow-engine-service
Service permettant la communication avec un workflow-engine via Gandalf.
- gandalf-java-client
- gandalf-module
- zeebe-module, etc... 

### connector-database-service
Service permettant la communication avec une base de donnée via Gandalf.
- gandalf-java-client
- gandalf-module
- h2-module, etc... 

### connector-version-control-service
Service permettant la communication avec un système de versionning via Gandalf.
- gandalf-java-client
- gandalf-module
- gitlab-module, etc...
 
### connector-artifact-service
Service permettant la communication avec un système de gestion d'artifact via Gandalf.
- gandalf-java-client
- gandalf-module
- nexus-module, etc... 

### connector-orchestrator-service
Service permettant la communication avec un orchestrator via Gandalf.
- gandalf-java-client
- gandalf-module
- jenkins-module, etc... 

## Library
Librairies Gandalf
### gandalf-java-client
Client Gandalf en Java.
- gandalf-module
- zeromq-module


## Module
Modules Gandalf, connecteurs, etc...
### zeromq-module
Module abstrait des communications ZeroMQ
- message-module

### gandalf-module
Module commun Gandalf
- zeromq-module

### message-module
Module commun des messages

### orchestrator-module
Module commun abstrait orchestrator
### artifact-module
Module commun abstrait artifact
### bus-module
Module commun abstrait bus
### workflow-engine-module
Module commun abstrait workflow
### version-control-module
Module commun abstrait versionning
### database-module (ToDo)
Module commun abstrait databse
### job-module (ToDo)
Module commun abstrait job

### custom-artifact-module
Module implémentation commun/specific artifact pour un artifact custom
- artifact-module
- zeromq-module

### custom-orchestrator-module
Module implémentation commun/specific orchestrator pour un orchestrator custom
- orchestrator-module
- zeromq-module

### kafka-module
Module implémentation commun/specific bus pour kafka
- kafka-module
- zeromq-module

### zeebe-module
Module implémentation commun/specific workflow-engine pour zeebe
- kafka-module
- zeromq-module

### nexus-module (ToDo)
### git-module (ToDo)
### gitlab-module (ToDo)
### h2-module (ToDo)
### jenkins-module (ToDo)
### job-zeebe-module (ToDo)

## Service
Services Gandalf


## Test
Tests

