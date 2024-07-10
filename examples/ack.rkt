(define (ack m n)
  (cond
    [(eq? m 0) (+ n 1)]
    [(eq? n 0) (ack (- m 1) 1)]
    [else (ack (- m 1) (ack m (- n 1)))]))
(ack 3 1)
