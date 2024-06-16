(define foo (lambda (x y) ((lambda (x z) (+ z x)) x y)))
(foo 1 2)
