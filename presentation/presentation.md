# JISL: Java interpreter for ISL+
## Was ist ein Interpreter?
- Implementierung einer Programmiersprache
    - Programm, was Programm Code ausführt
- (vs. Compiler?)

## Wie ist ein Interpreter aufgebaut?
- Vom Source Code zum Ergebnis
- Lexing
    - keine Korrektheit
    - `jisl inspect-lexing test.rkt`
- Parsing
    - Syntax
    - `jisl inspect-parsing test.rkt`
- Evaluating
    - ISL+ Semantik
    - `jisl test.rkt`

## Showcase im REPL
- REPL show off
- Values: Primitives, Lambdas (bzw. Builtins)
- Expressions: Values, S-expressions, `cond`, `local`
- Structures
- `require`
- Examples
