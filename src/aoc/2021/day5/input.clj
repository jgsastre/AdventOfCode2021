(ns aoc.2021.day5.input
  (:require
    [aoc.2021.day5.parse :as parse]
    [clojure.string :as str]))

(def test-input-raw "0,9 -> 5,9
8,0 -> 0,8
9,4 -> 3,4
2,2 -> 2,1
7,0 -> 7,4
6,4 -> 2,0
0,9 -> 2,9
3,4 -> 1,4
0,0 -> 8,8
5,5 -> 8,2")

(def test-input (parse/parse (str/split-lines test-input-raw)))

(def puzzle-input
  (-> (slurp "./src/aoc/2021/day5/input.txt")
      (str/split-lines)
      parse/parse))
