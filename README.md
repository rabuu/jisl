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
- [ ] Important builtins + [stdlib](./stdlib.rkt)
- [ ] Samples

## Not Todo
- There will be no support for images.
- For now there are no plans to implement `let`, `let*` and `letrec`.
- Also probably we will not implement signatures.
- quoted/quasiquoted
- complex numbers
- inexact numbers

## Things to note

### Equality
The standard interpreter has three basic equality comparators:
`eq?`, `equal?` and `=`.
`eq?` matches the exact same object.
`equal?` matches structurally same objects.
`=` matches equal numbers.

This example illustrates the subtle difference between `eq?` and `equal?`:
```racket
(define A (list 1 2))
(eq? A A) ; evaluates to #true
(eq? (list 1 2) (list 1 2)) ; evaluates to #false
```

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

#### Lazy evaluation
There are builtin lazy "lambdas" like and `and`, `or` and `if`.
For example, this won't fail:
```racket
(and #false (modulo 0 0))
```
Interestingly, normal lambdas are not lazy,
this will in fact fail (in the standard interpreter too):
```racket
(define myAnd (lambda (x y) (and x y)))
(myAnd #false (modulo 0 0))
```

### Structs
```racket
(define-struct foo (a))
(foo? (local [(define-struct foo (b))] (make-foo 1)))
; evaluates to #true but arguably should be #false
```

### Builtins

#### Differences between standard and our implementation
There is syntax that we handle as normal builtin applicables/procedures
where the standard implementation does not.
```racket
(procedure? if) ; we return #true, standard interpreter fails
```

## More resources
- [K. Ostermann, Lehrskript Praktische Informatik 1](https://ps-tuebingen.github.io/informatik-1-skript/)
