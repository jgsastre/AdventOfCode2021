(ns aoc.2021.day9.input
  (:require [aoc.2021.day9.parse :as parse]))

(def test-input (parse/parse-input "2199943210
  3987894921
  9856789892
  8767896789
  9899965678"))

(def puzzle-input
  (-> (slurp "./src/aoc/2021/day9/input.txt")
      (parse/parse-input)))
