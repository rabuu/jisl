(define A 2)
(define foo (lambda (x y) (+ x (- A 1) y)))

(foo A A)
