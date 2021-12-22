(ns aoc.2021.day11.input
  (:require
   [aoc.2021.day11.parse :as parse]))

(def test-input (parse/parse-input "5483143223
2745854711
5264556173
6141336146
6357385478
4167524645
2176841721
6882881134
4846848554
5283751526"))

(def puzzle-input
  (-> (slurp "./src/aoc/2021/day11/input.txt")
      (parse/parse-input)))
