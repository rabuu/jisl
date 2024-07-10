(define-struct person (age number-of-eyes studies-cs? number-of-partners number-of-children))

(define (in-range? num min max) (and (>= max num) (>= num min)))

(define (allowed-to-play-lego? p) (in-range? (person-age p) 3 99))

(define (allowed-to-drink? p) (>= (person-age p) 18))

(define (stable-relationship-status? p) (<= (person-number-of-partners p) 1))

(define (cyclops? p) (= (person-number-of-eyes p) 1))

(define (parent? p) (>= (person-number-of-children p) 1))

(define (lives-happy-life? p) (and (allowed-to-play-lego? p) (allowed-to-drink? p) (stable-relationship-status? p) (not (person-studies-cs? p))))

(define rasmus (make-person 18 2 #true 1 0))
(define philipp (make-person 18 2 #false 1 0))
(define captn-blaubaer (make-person 60 1 #false 10 10))
(lives-happy-life? rasmus)

