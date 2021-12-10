(ns aoc.2021.day6.core
  (:require
   [aoc.2021.day6.input :as input]))

(defn- next-day [population]
  (let [day-passed (map dec population)
        created-count (count (filter #(< % 0) day-passed))
        regenerated (map #(if (< % 0) 6 %) day-passed)]
    (concat regenerated (repeat created-count 8))))

(defn- next-day [[ x & xs ]]
  (conj (update (vec xs) 6 + x) x))

(defn after-n-days [n input]
  (loop [y input
         i 0]
    (if (= i n) y (recur (next-day y) (+ i 1)))))

;(defn after-n-days [n input]
;  (nth (iterate next-day input) n))

(def example-part-one (apply + (after-n-days 80 input/test-input)))

(def solve-part-one (apply + (after-n-days 80 input/puzzle-input)))

(def example-part-two (apply + (after-n-days 256 input/test-input)))

(def example-part-two (apply + (after-n-days 256 input/puzzle-input))) ; 1710166656900

