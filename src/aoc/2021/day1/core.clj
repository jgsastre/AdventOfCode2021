(ns aoc.2021.day1.core
  (:require
    [clojure.string :as str]))

(defn get-numbers []
  (let [input (slurp "./src/aoc/2021/day1/input.txt")
        lines (str/split-lines input)]
    (map #(Integer/parseInt %) lines)))

(defn solve-part-one []
  (let [numbers (get-numbers)
        shift_numbers (rest numbers)
        increasing? (map < numbers shift_numbers)]
    (count (filter true? increasing?))))

(solve-part-one)

(def example [199 200 208 210 200 207 240 269 260 263])

(defn solve-part-two []
  (let [numbers (get-numbers)
        shift_numbers (rest numbers)
        shift_numbers_2 (rest shift_numbers)
        three-sum (map + numbers shift_numbers shift_numbers_2)
        increasing? (map < three-sum (rest three-sum))]
    (count (filter true? increasing?))))

(solve-part-two)
