(define-struct posn (x y))

(define e (exp 1))
(define pi 245850992/78256779)

(define (identity x) x)

(define (positive? x)
  (> x 0))

(define (negative? x)
  (< x 0))

(define (zero? x)
  (= x 0))

(define (even? x)
  (= (modulo x 2) 0))

(define (odd? x)
  (= (modulo x 2) 1))

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

(define (gcd x y)
  (if (= y 0)
    x
    (gcd y (modulo x y))))

(define (lcm x y)
    (/ (* x y) (gcd x y)))
