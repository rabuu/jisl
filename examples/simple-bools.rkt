(define-struct person (age number-of-eyes studies-cs? number-of-partners number-of-children))

(define (in-range? num minimum maximum)
  (and (>= maximum num) (>= num minimum)))

(define (allowed-to-play-lego? p) (in-range? (person-age p) 3 99))

(define (allowed-to-drink? p) (>= (person-age p) 18))

(define (stable-relationship-status? p)
  (in-range? (person-number-of-partners p) 0 1))

(define (cyclops? p) (= (person-number-of-eyes p) 1))

(define (parent? p) (>= (person-number-of-children p) 1))

(define (lives-happy-life? p)
  (and (allowed-to-play-lego? p)
       (allowed-to-drink? p)
       (if (parent? p) (stable-relationship-status? p) #true)
       (not (person-studies-cs? p))))

(define susanne (make-person 21 2 #false 0 0))
(define waldemar (make-person 17 2 #true 1 0))
(define captn-blaubaer (make-person 60 1 #false 10 10))
(lives-happy-life? susanne)
(lives-happy-life? waldemar)
(lives-happy-life? captn-blaubaer)
