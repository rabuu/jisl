(define-struct 3d-point (x y z))

(define (abs x) (cond
                  [(>= x 0) x]
                  [else (- 0 x)]))

(define (max-norm p)
  (local [(define x (abs (3d-point-x p))) (define y (abs (3d-point-y p))) (define z (abs (3d-point-z p)))]
    (cond
      [(and (>= x y) (>= x z)) x]
      [(>= y z) y]
      [else z])))

(define p (make-3d-point -15 4 3))

(max-norm p)
