(ns aoc.2021.day6.input
  (:require
   [aoc.2021.day6.parse :as parse]
   [clojure.string :as str]))

(def test-input (parse/parse-input "3,4,3,1,2"))

(def puzzle-input
  (-> (slurp "./src/aoc/2021/day6/input.txt")
      (str/split-lines)
      (first)
      (parse/parse-input)))
