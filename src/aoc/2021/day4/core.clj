(ns aoc.2021.day4.core
  (:require
   [aoc.2021.day4.input :as input]))

(defn- add-chosen-number [n ticket_]
  (loop [numbers (:numbers ticket_)
         ticket ticket_
         row-idx 0]
    (if (empty? numbers) ticket
        (let [row (first numbers)
              col-idx (.indexOf row n)]
          (if (> col-idx -1)
            (update-in
             (update-in
              (assoc-in ticket [:chosen row-idx col-idx] 1)
              [:rows row-idx] inc)
             [:cols col-idx] inc)
            (recur (rest numbers) ticket (+ row-idx 1)))))))

(defn- has-prize? [ticket]
  (let [size (count (:numbers ticket))
        col-prize? (> (.indexOf (:cols ticket) size) -1)
        row-prize? (> (.indexOf (:rows ticket) size) -1)]
    (if (or col-prize? row-prize?) ticket false)))

(defn- calc-sum [ticket]
  (let [chosen (:chosen ticket)
        numbers (:numbers ticket)
        size (count chosen)]
    (loop [row-idx 0
           acc 0]
      (if (= row-idx size) acc
          (recur (+ row-idx 1) (+ acc (reduce
                                       (fn dot-prod [acc col-idx]
                                         (if (= (get-in chosen [row-idx col-idx]) 0) (+ acc (get-in numbers [row-idx col-idx])) acc))
                                       0 (range size))))))))

(defn- find-winner [{numbers_ :chosen-numbers tickets_ :tickets}]
  (loop [numbers numbers_
         tickets tickets_]
    (if (empty? numbers) "Not winner found!"
        (let [n (first numbers)
              updated-tickets (map #(add-chosen-number n %) tickets)
              prized-ticket? (some has-prize? updated-tickets)]
          (if prized-ticket?
            (let [ticket-sum (calc-sum prized-ticket?)]
              {:number n :ticket-number ticket-sum :result (* n ticket-sum)})
            (recur (rest numbers) updated-tickets))))))

;; Solve example
(find-winner input/test-input)

;; Solve part one
(find-winner input/puzzle-input)

(defn- find-looser [{numbers_ :chosen-numbers tickets_ :tickets}]
  (loop [numbers numbers_
         tickets tickets_]
    (if (empty? numbers) "Not winner found!"
        (let [n (first numbers)
              updated-tickets (map #(add-chosen-number n %) tickets)
              filtered-tickets (remove has-prize? updated-tickets)]
          (if (empty? filtered-tickets)
            (let [ticket-sum (calc-sum (first updated-tickets))]
              {:number n :ticket-number ticket-sum :result (* n ticket-sum)})
            (recur (rest numbers) filtered-tickets))))))

;; Solve example
(find-looser input/test-input)

;; Solve part two
(find-looser input/puzzle-input)

(some #(> % 1) [0 1 2])
(assoc-in {:foo [1 2 3]} [:foo 1] 17)

(add-chosen-number 18 (get-in input/puzzle-input [:tickets 0]))

(has-prize? (assoc-in (get-in input/test-input [:tickets 0]) [:cols] [0 1 2 3 5]))
