(require "../stdlib.rkt")

; keep lists where the maximum is even
(define lst-1 (list (list 3 1 7 8) (list 2 3) (list 1 8 99)))
(filter (compose even? (lambda (lst) (apply max lst))) lst-1)
