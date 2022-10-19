[TOC]

# Hard QRoyal backend

Main backend application of Hard QRoyal system.

## 1. Technologies used

- [Java 8](https://www.oracle.com/java/technologies/downloads/#java8)
- [Spring Boot 2.7.4](https://spring.io/projects/spring-boot)
- [Docker](https://docs.docker.com/get-docker/)
- ...

## 2. Infrastructures

Most of the infrastructures are being deployed as docker services. Follow the commands below to start them:

```bash
$ cd ./docker

# dev
$ docker-compose build
$ docker-compose up -d

# unit test & application test
$ docker-compose -f ./docker-compose-test.yml build
$ docker-compose -f ./docker-compose-test.yml up -d

```

## 3. Modules

- `hard-qroyal-domain`: entities and repositories
- `hard-qroyal-infrastructure`: repositories
- `hard-qroyal-integration`: controllers
- `hard-qroyal`: main application

## 4. Maven Settings

Add this `server` to your maven settings.xml file

```xml

<server>
    <id>hardQroyal</id>
    <username>admin</username>
    <password></password>
</server>
``` 

## 5. Development

Building the project is dependent on the environment you want to build for. The default environment is "dev".

- Build: `mvn clean package` or `mvn clean package -DskipTests` if you want to skip unit tests for faster build.
  However, it's not recommended skipping the tests
- Running the application: `mvn spring-boot:run` to start spring boot application on embedded server (dev env). The
  command has to be executed in the `hard-qroyal` package.

## 6. Testing

- Build: `mvn clean install -Papp-test-dev -DskipTests`
- Unit tests: `mvn test`
- Application
  tests: `mvn spring-boot:start verify spring-boot:stop -Papp-test-dev -Dspring-boot.run.profiles=app-test-dev`. The
  commands have to be executed in the `derfet-erp-api` module.

## 7. Dependency Check Maven

```bash
$ mvn verify -DskipTests -DskipITs -Ddependency-check.skip=false
```
