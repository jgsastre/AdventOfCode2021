(ns aoc.2021.day6.core
  (:require
   [aoc.2021.day6.input :as input]))

(defn- next-day [population]
  (let [day-passed (map dec population)
        created-count (count (filter #(< % 0) day-passed))
        regenerated (map #(if (< % 0) 6 %) day-passed)]
    (cons regenerated (repeat created-count 8))))

(next-day (input/test-input))
