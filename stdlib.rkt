(define (positive? x)
  (> x 0))

(define (negative? x)
  (< x 0))

(define (zero? x)
  (= x 0))

(define (sgn x)
  (if (positive? x) 1 -1))

(define (abs x)
  (if (negative? x) (- x) x))

(define (sqr x)
  (* x x))

(define (add1 x)
  (+ x 1))

(define (sub1 x)
  (- x 1))

(define-struct posn (x y))
