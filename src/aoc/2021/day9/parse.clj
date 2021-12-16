(ns aoc.2021.day9.parse
  (:require
    [clojure.string :as str]))

(defn parse-input [input]
  (vec (map (comp vec (partial map #(Character/digit % 10)) vec str/trim) (str/split-lines input))))

