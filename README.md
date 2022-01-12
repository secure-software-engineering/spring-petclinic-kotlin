# Vulnerable Spring PetClinic in Kotlin 

This is a vulnerable version of the [spring-petclinic-kotlin](https://github.com/spring-petclinic/spring-petclinic-kotlin) application. 
This application contains security vulnerabilities (hibernate injection, cross-site scripting, use of MD5, etc.) and can be used as an example project to test/compare vulnerability detection tools, such as static and/or dynamic analysis security testing tools (SAST/DAST). 


## How did we create this vulnerable version?

We built the vulnerable version of the [spring-petclinic-kotlin](https://github.com/spring-petclinic/spring-petclinic-kotlin) similar to the vulnerable [spring-petclinic](https://github.com/contrast-community/spring-petclinic) Java project. For this purpose, we added three additional classes (CustomRepositoryFactory, CustomRepositoryFactoryBean, and OwnerRepositoryCustomImpl) to the existing [spring-petclinic-kotlin](https://github.com/spring-petclinic/spring-petclinic-kotlin) project. We followed the steps below to build the vulnerable spring-petclinic-kotlin benchmark project.

1. We first opened the existing [spring-petclinic-kotlin](https://github.com/spring-petclinic/spring-petclinic-kotlin) project in the IntelliJ IDEA IDE.
2. We created an empty Kotlin class named CustomRepositoryFactory.
3. We copied the Java source code of the class CustomRepositoryFactory from the vulnerable [spring-petclinic](https://github.com/contrast-community/spring-petclinic) Java project.
4. We pasted the copied Java source code to the Kotlin class CustomRepositoryFactory in our vulnerable spring-petclinic-kotlin project. Then, the IntelliJ IDEA IDE automatically translates the pasted Java source code to the Kotlin source code.
5. We repeated steps 2 to 4 for creating the classes CustomRepositoryFactoryBean, and OwnerRepositoryCustomImpl.
6. Finally, we added the annotation @NoRepositoryBean to the existing OwnerRepository class, similar to the vulnerable [spring-petclinic](https://github.com/contrast-community/spring-petclinic) Java project.

After the automatic translation from the Java code to the Kotlin code, 
we found an error in the CustomRepositoryFactory class. 
In the Java [spring-petclinic](https://github.com/contrast-community/spring-petclinic) 
vulnerable version, there is a method call to 
the getRepositoryInformation with the second parameter as null value. 
However, in the Kotlin version, the method getRepositoryInformation 
requires both parameters to be non-null values. 
Therefore, the Kotlin compiler gave an error "Null can not be a value
of a non-null type". We fixed this issue by passing the appropriate object
to the second parameter.

## Technologies used

* Language: Kotlin
* Core framework: Spring Boot 2 with Spring Framework 5 Kotlin support
* Server: Apache Tomcat
* Web framework: Spring MVC
* Templates: Thymeleaf and Bootstrap
* Persistence : Spring Data JPA
* Databases: H2 and MySQL both supported
* Build: Gradle Script with the Kotlin DSL
* Testing: Junit 5, Mockito and AssertJ

## Running petclinic locally

### With gradle command line

```
git clone https://github.com/spring-petclinic/spring-petclinic-kotlin.git
cd spring-petclinic-kotlin
./gradlew bootRun
```

### With Docker

```
docker run -p 8080:8080 springcommunity/spring-petclinic-kotlin
```


You can then access petclinic here: [http://localhost:8080/]()

<img width="1042" alt="petclinic-screenshot" src="https://user-images.githubusercontent.com/838318/29994372-7f85f6da-8fce-11e7-8896-b5aa075ac0d7.png">


## Database configuration

In its default configuration, Petclinic uses an in-memory database (H2) which gets populated at startup with data.
The h2 console is automatically exposed at `http://localhost:8080/h2-console`
and it is possible to inspect the content of the database using the `jdbc:h2:mem:{uuid}` url (the `uuid` param could be find in the startup logs).

A similar setup is provided for MySql in case a persistent database configuration is needed.
Note that whenever the database type is changed, the data-access.properties file needs to be updated and the mysql-connector-java artifact from the pom.xml needs to be uncommented.

You could start a MySql database with docker:

```
docker run -e MYSQL_USER=petclinic -e MYSQL_PASSWORD=petclinic -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=petclinic -p 3306:3306 mysql:5.7.8
```

Further documentation is provided [here](https://github.com/spring-projects/spring-petclinic/blob/master/src/main/resources/db/mysql/petclinic_db_setup_mysql.txt).


## Looking for something in particular?

| Features                           | Class, files or Java property files  |
|------------------------------------|---|
|The Main Class                      | [PetClinicApplication](https://github.com/spring-petclinic/spring-petclinic-kotlin/blob/master/src/main/kotlin/org/springframework/samples/petclinic/PetClinicApplication.kt) |
|Properties configuration file       | [application.properties](https://github.com/spring-petclinic/spring-petclinic-kotlin/blob/master/src/main/resources) |
|Gradle build script with Kotlin DSL | [build.gradle.kts](https://github.com/spring-petclinic/spring-petclinic-kotlin/blob/master/build.gradle.kts) |
|Caching Configuration               | [CacheConfig](https://github.com/spring-petclinic/spring-petclinic-kotlin/blob/master/src/main/kotlin/org/springframework/samples/petclinic/system/CacheConfig.kt) |


## Import and run the project in IntelliJ IDEA
   
* Make sure you have at least IntelliJ IDEA 2017.2 and IDEA Kotlin plugin 1.1.60+ (menu Tools -> Kotlin -> configure Kotlin Plugin Updates -> make sure "Stable" channel is selected -> check for updates now -> restart IDE after the update)
* Import it in IDEA as a Gradle project
  * Go to the menu "File -> New -> Project from Existing Sources... "
  * Select the spring-petclinic-kotlin directory then choose "Import the project from Gradle"
  * Select the "Use gradle wrapper task configuration" radio button
* In IntelliJ IDEA, right click on PetClinicApplication.kt then "Run..." or "Debug..."
* Open http://localhost:8080/ in your browser


## Documentation

* [Migrez une application Java Spring Boot vers kotlin](http://javaetmoi.com/2017/09/migrez-application-java-spring-boot-vers-kotlin/) (french)
* [Migration Spring Web MVC vers Spring WebFlux](http://javaetmoi.com/2017/12/migration-spring-web-mvc-vers-spring-webflux/) (french)


## Publishing a Docker image

This application uses [Google Jib]([https://github.com/GoogleContainerTools/jib) to build an optimized Docker image
into the [Docker Hub](https://cloud.docker.com/u/springcommunity/repository/docker/springcommunity/spring-petclinic-kotlin/)
repository.
The [build.gradle.kts](build.gradle.kts) has been configured to publish the image with a the `springcommunity/spring-petclinic-kotlin` image name.

Build and push the container image of Petclinic to the Docker Hub registry:
```
gradle jib -Djib.to.auth.username=<username> -Djib.to.auth.password=<password>
```


## Interesting Spring Petclinic forks

The Spring Petclinic master branch in the main [spring-projects](https://github.com/spring-projects/spring-petclinic)
GitHub org is the "canonical" implementation, currently based on Spring Boot and Thymeleaf.

This [spring-petclinic-kotlin][] project is one of the [several forks](https://spring-petclinic.github.io/docs/forks.html) 
hosted in a special GitHub org: [spring-petclinic](https://github.com/spring-petclinic).
If you have a special interest in a different technology stack
that could be used to implement the Pet Clinic then please join the community there.


## Interaction with other open source projects

One of the best parts about working on the Spring Petclinic application is that we have the opportunity to work in direct contact with many Open Source projects. We found some bugs/suggested improvements on various topics such as Spring, Spring Data, Bean Validation and even Eclipse! In many cases, they've been fixed/implemented in just a few days.
Here is a list of them:

| Name | Issue |
|------|-------|
| Spring JDBC: simplify usage of NamedParameterJdbcTemplate | [SPR-10256](https://jira.springsource.org/browse/SPR-10256) and [SPR-10257](https://jira.springsource.org/browse/SPR-10257) |
| Bean Validation / Hibernate Validator: simplify Maven dependencies and backward compatibility |[HV-790](https://hibernate.atlassian.net/browse/HV-790) and [HV-792](https://hibernate.atlassian.net/browse/HV-792) |
| Spring Data: provide more flexibility when working with JPQL queries | [DATAJPA-292](https://jira.springsource.org/browse/DATAJPA-292) |


# Contributing

Contributions with code that injects new vulnerabilities are welcomed. Please create a pull request for that purpuse. 



