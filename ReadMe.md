# Health Remain App

![example workflow](https://img.shields.io/badge/microservice-darkgrey)
![example workflow](https://img.shields.io/badge/Java-ff964f)
![example workflow](https://img.shields.io/badge/Spring-green)
![example workflow](https://img.shields.io/badge/MongoDB-60a05b)
![example workflow](https://img.shields.io/badge/Docker-blue)

___
#### _motivation_
This personal project is a CRM web application designed to support businesses in the healthcare industry.
It has been developed using a microservice architecture. 
The goal of the project is to apply acquired skills
and gain new ones, particularly in terms of tool configuration.
The application is currently in the development phase.

___


## Description

A microservices application designed to support medical businesses,
it serves as a CRM web platform for small healthcare facilities.
The application enables the management of physician staff, patient registration, and the efficient scheduling of medical appointments.
Users can seamlessly oversee medical visit appointments, including administrator scheduling and patient reservations.
Additionally, the application facilitates the retrieval of essential information about patients, doctors, and appointment availability,
providing a centralized solution for streamlined healthcare service coordination and data access

Business logic microservices include the Physician service, Patient service, and Visit service.
They are supplemented with other components such as api-gateway and Eureka server for routing and load balancing,
and Zipkin for providing visual log representation. The system also includes a MongoDB container as the database.
<br/>

### key features:
 
- CRUD operations for Physician employees
- CRUD operations for Patients
- Physician Visit appointment system

---

## Documentation
An interactive documentation is provided on top of OpenAPI. 

When running the application locally, Swagger UI endpoints are accessible for interactive documentation. 
Additionally, static YAML files are available within the project files.

| service   | interactive documentation | yaml             | 
|-----------|---------------------------|------------------|
| Physician | [physician-doc]           | [physician-yaml] |
| Patient   | [patient-doc]             | [patient-yaml]   | 
| Visit     | [visit-doc]               | [visit-yaml]     | 


<br/>

---

## Project's Technology Stack so far
- Springboot
- MongoDB
- Spock & Groovy
- Testcontainers
- Micrometer & Zipkin
- Jib
- Docker

<br/>

---

## Running the Application

- running Docker environment is required
- directory containing files: 

  - docker-compose.yml
  - .env 


example of '.env' file below:

```code
MONGO_DB_USERNAME=typeUserNameHere
MONGO_DB_PASSWORD=typeUserPasswordHere
MONGO_INITDB_ROOT_USERNAME=typeUserNameHere
MONGO_INITDB_ROOT_PASSWORD=typeUserPasswordHere
```

in created directory execute:

```sh
docker compose --env-file .env up -d
```
to stop application:

```sh
docker compose down
```

<br/>

## Instructions for using the application

Entry point for all endpoints is accessible at the following address:

| Service             | endpoint address  | 
|---------------------|-------------------|
| Application gateway | [ApiGateWay-ep]   |

<br/>
Backend services supporting application:

| service                     | name          | endpoint address  | 
|-----------------------------|---------------|-------------------|
| database interface          | Mongo Express | [MongoExpress-ep] | 
| service discovery dashboard | Eureka Server | [EurekaServer-ep] | 
| tracing interface           | Zipkin        | [Zipkin-ep]       | 

>Business logic endpoints are listed under the documentation section.
> <br/>
>Endpoints can be tested directly using Swagger UI or any tool of your choice such as Postman.


<br/>

---

## further development ideas

### planned features & functionalities

- control module for physicians and other employees
- registering and logging for Patient user
- job that checks and updates Visits status 
- introduce container orchestration
- centralize openApi documentation to be available as single document
- billing module for preparing reports
- deployment in the cloud service


### technologies to implement

- Kubernetes
- Kafka
- JWT authorization, Keycloak
- convert logging to Elasticsearch
- job scheduling e.g. Quartz
- keycloak


### to be fixed

- establish microservice mocks for integration test e.g. Wiremock

<br/>

---

---

[//]: # 
[physician-doc]: <http://localhost:8083/apidoc>
[patient-doc]: <http://localhost:8086/apidoc>
[visit-doc]: <http://localhost:8084/apidoc>

[physician-yaml]: <http://localhost:8083/apidoc>
[patient-yaml]: <http://localhost:8086/apidoc>
[visit-yaml]: <http://localhost:8084/apidoc>

[ApiGateWay-ep]: <http://localhost:8082>   
[MongoExpress-ep]: <http://localhost:8081> 
[EurekaServer-ep]: <http://localhost:8761> 
[Zipkin-ep]: <http://localhost:9411>       