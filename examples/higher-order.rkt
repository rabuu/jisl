(require "../stdlib.rkt")

; sum up a whole list
(define lst-1 (list 1 2 3 4 5 6))
(define (sum lst) (foldr + 0 lst))
(sum lst-1)

; square every element of a list
(define lst-2 (list 3 3 4 4 5 5))
(map sqr lst-2)

; keep lists where the maximum is even
(define lst-3 (list (list 3 1 7 8) (list 2 3) (list 1 8 99)))
(filter (compose even? (lambda (lst) (apply max lst))) lst-3)
