# JISL
A **J**ava interpreter for the [**I**ntermediate **S**tudent **L**anguage (with lambda abstraction)](https://docs.racket-lang.org/htdp-langs/intermediate-lam.html).

## Build instructions
This project is built with [Maven](https://maven.apache.org/).
```sh
./jisl-run FILENAME  # to run the project
mvn test             # to run the test suite
mvn clean package    # to generate a JAR executable
```

## Todo
- [x] Lexer
- [x] Parser
- [x] Basic Evaluation
- [x] REPL
- [x] Simple library support
- [ ] Symbols/Atoms
- [ ] Structures
- [ ] All important builtins
- [ ] Good [standard library](stdlib/)
- [ ] Nested libraries

## Not Todo
- There will be no support for images.
- Also probably we will not implement signatures.

## More resources
- [K. Ostermann, Lehrskript Praktische Informatik 1](https://ps-tuebingen.github.io/informatik-1-skript/)
