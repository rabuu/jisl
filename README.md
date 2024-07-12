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
- [x] Simple library support (+ nested)
- [x] Local definitions
- [x] Source position of ProgramElements
- [x] Lists
- [x] Symbols/Atoms
- [x] Structures
- [ ] Write tests & comments
- [ ] All important builtins + stdlib
- [ ] Samples

## Not Todo
- There will be no support for images.
- For now there are no plans to implement `let`, `let*` and `letrec`.
- Also probably we will not implement signatures.
- quoted/quasiquoted

## Things to note

### Lambdas
Two lambdas are equals if they have the same definition.
So they must share the same argument names and also the same expression in the exact same form to be equal.

#### Functions are Lambdas
We handle functions a little different than the default interpreter.
A function is just a name for a lambda.

Therefore, equality between functions may act a little weird.
```racket
(define (foo x y) (+ x y))
(define (bar x y) (+ x y))
(define (baz a b) (+ a b))

(eq? foo bar) ; evaluates to #true
(eq? foo baz) ; evaluates to #false
```

## Structs
```racket
(define-struct foo (a))
(foo? (local [(define-struct foo (b))] (make-foo 1)))
; evaluates to #true but arguably should be #false
```

## More resources
- [K. Ostermann, Lehrskript Praktische Informatik 1](https://ps-tuebingen.github.io/informatik-1-skript/)
