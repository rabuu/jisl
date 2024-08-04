# JISL
A **J**ava interpreter for the [**I**ntermediate **S**tudent **L**anguage (with lambda abstraction)](https://docs.racket-lang.org/htdp-langs/intermediate-lam.html).

This is our final project for the course Praktische Informatik 2 (OOP)
at the University of Tübingen.

## Usage (end user)
There are four execution modes:
- Run a source file (default)
- Inspect the lexer output of a given source file
- Inspect the parser output of a given source file
- Run an interactive REPL

If you got a JAR executable you can use the following commands:
```sh
java -jar jisl-0.0.1.jar FILENAME # to run
java -jar jisl-0.0.1.jar inspect-lexing FILENAME
java -jar jisl-0.0.1.jar inspect-parsing FILENAME
java -jar jisl-0.0.1.jar repl
```

## Build instructions (developer)
This project is built with [Maven](https://maven.apache.org/).
There is a POSIX [wrapper script](./jisl-run.sh) to run the program directly.
Running the project with IntelliJ is also easily possible,
either by using the Maven functionality or by manually running `de.rbuurman.jisl.Main`.
```sh
./jisl-run ARGS   # to run the project
mvn test          # to run the test suite
mvn clean package # to generate a JAR executable
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
- [x] Write a comments & tests (always room for improvement)
- [x] Important builtins + [stdlib](./stdlib.rkt)
- [x] Samples

### Planned for 0.1.0
- Better REPL (using [JLine](https://github.com/jline/jline3))
- `let`, `let*` and `letrec`

## Not Todo
- images (never)
- signatures (maybe)
- quoted/quasiquoted (maybe)
- complex/inexact numbers (probably never)

## Things to note

### Equality
Our (as the standard) interpreter has three basic equality comparators:
`eq?`, `equal?` and `=`.
`eq?` matches the exact same object.
`equal?` matches structurally same objects.
`=` matches equal numbers.

This example illustrates the subtle difference between `eq?` and `equal?`:
```racket
(define A (list 1 2))
(eq? A A) ; evaluates to #true
(eq? (list 1 2) (list 1 2)) ; evaluates to #false
(equal? (list 1 2) (list 1 2)) ; evaluates to #true
```

### Lambdas
Two lambdas are equal if they have the same definition.
So they must share the same argument names and also the same expression in the exact same form to be equal.

#### Functions are Lambdas
We handle functions a little different than the default interpreter.
A function is just a name for a lambda.

Therefore, equality between functions may act a little weird.
```racket
(define (foo x y) (+ x y))
(define (bar x y) (+ x y))
(define (baz a b) (+ a b))

(equal? foo bar) ; evaluates to #true
(equal? foo baz) ; evaluates to #false
```

#### Short circuiting
There are builtin "lazy procedures" like and `and`, `or` and `if`.
For example, this won't fail although `(modulo 0 0)` on its own would:
```racket
(and #false (modulo 0 0))
```
Interestingly, normal lambdas do not evaluate lazily, this will in fact fail (like with the standard intepreter):
```racket
(define myAnd (lambda (x y) (and x y)))
(myAnd #false (modulo 0 0))
```

#### Our Lambda definitions are _very_ lazy
In our implementation a lambda/function definition is not semantically checked, at all.
```racket
(define (foo x y) (* BAR BAZ)) ; this does not throw an error
```
The standard implementation would throw an error because neither `BAR` nor `BAZ` are defined.

### Structs
At the time, we compare structs based on their names.
With local definitions (that allow shadowing) this leads to problems.
```racket
(define-struct foo (a b))
(foo? (local [(define-struct foo (c))] (make-foo 1)))
; evaluates to #true but arguably should be #false
```

### Builtins

#### Builtin procedure vs language syntax
There is syntax that we handle as normal builtin applicables/procedures
where the standard implementation does not.
```racket
(procedure? if) ; we return #true, standard interpreter #false
```

## More resources
- [K. Ostermann, Lehrskript Praktische Informatik 1](https://ps-tuebingen.github.io/informatik-1-skript/)
