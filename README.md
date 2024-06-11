# JISL
A **J**ava interpreter for the [**I**ntermediate **S**tudent **L**anguage (with lambda abstraction)](https://docs.racket-lang.org/htdp-langs/intermediate-lam.html).

## Build instructions
This project is built with [Maven](https://maven.apache.org/).
```sh
mvn clean compile exec:java # to run the project
mvn test                    # to run the test suite
mvn clean package           # to generate a JAR executable
```
