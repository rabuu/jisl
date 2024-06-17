(require "lib.rkt")

(define x 99)
(local [(define x 1) (define y 2)] (+ x x))
x
