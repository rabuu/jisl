;; Standard structs
(define-struct posn (x y))

;; Constants
(define e (exp 1))
(define pi 245850992/78256779)

;; arithmetic helpers
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

;; more arithmetic functions
(define (gcd x y)
  (if (= y 0)
    x
    (gcd y (modulo x y))))

(define (lcm x y)
    (/ (* x y) (gcd x y)))

;; list helpers

(define (caar lst)
  (car (car lst)))

(define (cdar lst)
  (cdr (car lst)))

(define (cadr lst)
  (car (cdr lst)))

(define (cddr lst)
  (cdr (cdr lst)))

(define (caaar lst)
  (car (caar lst)))

(define (cdaar lst)
  (cdr (caar lst)))

(define (cadar lst)
  (car (cdr (car lst))))

(define (caadr lst)
  (caar (cdr lst)))

(define (cddar lst)
  (cddr (car lst)))

(define (cdadr lst)
  (cdr (car (cdr lst))))

(define (cdddr lst)
  (cdr (cddr lst)))

(define (first lst)
  (car lst))

(define (second lst)
  ((lambda (x) (if (cons? x) (car x) x))
   (cdr lst)))

(define (third lst)
  ((lambda (x) (if (cons? x) (car x) x))
    (cddr lst)))

(define (fourth lst)
  ((lambda (x) (if (cons? x) (car x) x))
    (cdddr lst)))

(define (fifth lst)
  ((lambda (x) (if (cons? x) (car x) x))
    (cdddr (cdr lst))))

(define (sixth lst)
  ((lambda (x) (if (cons? x) (car x) x))
    (cdddr (cddr lst))))

(define (seventh lst)
  ((lambda (x) (if (cons? x) (car x) x))
    (cdddr (cdddr lst))))

(define (eighth lst)
  ((lambda (x) (if (cons? x) (car x) x))
    (cdddr (cdddr (cdr lst)))))

(define (ninth lst)
  ((lambda (x) (if (cons? x) (car x) x))
    (cdddr (cdddr (cddr lst)))))

(define (tenth lst)
  ((lambda (x) (if (cons? x) (car x) x))
    (cdddr (cdddr (cdddr lst)))))

(define (rest lst)
  (cdr lst))

;; higher-order functions

; Return x for every x
(define (identity x) x)

; Composes two functions into a single function
(define (compose g f)
  (lambda (x) (g (f x))))

; Constructs a list from all those items on a list for which p? holds
(define (filter p? lst)
  (if (empty? lst)
    empty
    (append
      (if (p? (first lst)) (list (first lst)) empty)
      (filter p? (rest lst)))))

; Determines whether p? holds for every item of lst
(define (andmap p? lst)
  (if (empty? lst)
    #true
    (and (p? (first lst)) (andmap p? (rest lst)))))

; Determines whether p? holds for at least one item of lst
(define (ormap p? lst)
  (if (empty? lst)
    #false
    (or (p? (first lst)) (ormap p? (rest lst)))))
