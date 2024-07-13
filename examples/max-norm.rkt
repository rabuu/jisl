; compute the maximum norm of a 3D vector

(require "../stdlib.rkt")

(define-struct 3d-point (x y z))

(define (max-norm p)
  (local
    [(define x (abs (3d-point-x p)))
     (define y (abs (3d-point-y p)))
     (define z (abs (3d-point-z p)))]
    (max x y z)))

(define p (make-3d-point -15 4 3))
(max-norm p)
