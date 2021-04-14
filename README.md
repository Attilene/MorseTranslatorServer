<h1 align="center">Morse Translator Server</h1>

***
<h3 align="center"> |
    <a href="#Description"> Description </a> |
    <a href="#Run"> Run </a> |
    <a href="#Dependencies"> Dependencies </a> |
    <a href="#Built-With"> Built With </a> |
    <a href="#Author"> Author </a> |
    <a href="#License"> License </a> |
</h3> 

***
## Description:
The server for **Morse Translator [application](https://github.com/Attilene/MorseTranslatorClient)**

Allowed actions for the server:
* Log in existing user
* Sign up a new user
* Manage user's private cabinet
* Password recovery via email  
* Translate strings from natural languages (Russian, English) to Morse code and back

The server working on **Java Spring** framework
and linked to the **PostgreSQL** database.

***
## Run:
1. Download and build project
2. Run `ServerApplication.java`
3. Run **Morse Translator [app](https://github.com/Attilene/MorseTranslatorClient)**
4. Enjoy the application

***
## Dependencies:

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-context</artifactId>
        <version>5.3.3</version>
        <type>jar</type>
    </dependency>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-webmvc</artifactId>
        <version>5.3.3</version>
        <type>jar</type>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <scope>runtime</scope>
        <optional>true</optional>
    </dependency>
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
        <version>42.2.19</version>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>log4j</groupId>
        <artifactId>log4j</artifactId>
        <version>1.2.17</version>
    </dependency>
    <dependency>
        <groupId>javax.validation</groupId>
        <artifactId>validation-api</artifactId>
        <version>2.0.1.Final</version>
    </dependency>
    <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>javax.servlet-api</artifactId>
        <version>4.0.1</version>
        <scope>provided</scope>
    </dependency>
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.13.1</version>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-test</artifactId>
        <version>5.3.3</version>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-context-support</artifactId>
        <version>5.2.8.RELEASE</version>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-mail</artifactId>
        <version>2.3.4.RELEASE</version>
    </dependency>

***
## Built With:

* [Java 15](https://www.oracle.com/ru/java/) - Main programming language
* [PostgreSQL](https://www.postgresql.org/download/windows/) - Database management system
* [Java Spring](https://spring.io) - Universal development framework
* [Maven](https://maven.apache.org) - Dependency Management  
* [JUnit](https://junit.org/junit5/) - Testing
* [Log4j](https://logging.apache.org/log4j/1.2/download.html) - Logging

***
### Author:
**Bakanov Artem**

Student of Financial University

Faculty of Information Technology and Big Data Analysis

***
### License: 

This project is licensed under MIT License - see the [LICENSE](https://github.com/Attilene/MorseTranslatorServer/blob/master/LICENSE) 
file for details

#### Version: 1.0

###### 13.04.21
