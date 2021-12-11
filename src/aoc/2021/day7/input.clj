(ns aoc.2021.day7.input
  (:require
   [aoc.2021.day7.parse :as parse]
   [clojure.string :as str]))

(def test-input (parse/parse-input "16,1,2,0,4,2,7,1,2,14"))

(def puzzle-input
  (-> (slurp "./src/aoc/2021/day7/input.txt")
      (str/split-lines)
      (first)
      (parse/parse-input)))
