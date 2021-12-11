(ns aoc.2021.day7.core
  (:require [aoc.2021.day7.input :as input]))

(defn- get-median [x]
  (nth (sort x) (int (/ (count x) 2))))

(defn- abs-distance [x]
  (fn d_x [y]
    (Math/abs (- x y))))

(defn- acc [cost_function]
  (fn d_x [acc y]
    (+ acc (cost_function y))))

(defn- calculate-min-cost [cost_function pos]
  (reduce (acc cost_function) 0 pos))

(defn solve-part-one [x]
  (calculate-min-cost (abs-distance (get-median x)) x))

(def part-one-test-solution (solve-part-one input/test-input))

(def part-one-solution (solve-part-one input/puzzle-input))

(defn inc-distance [x]
  (fn d_x [y]
    (reduce + (range (+ 1 (Math/abs (- x y)))))))

(calc-mean input/puzzle-input) ; 495

(defn calc-mean [x]
  (let [mean (float (/ (reduce +  x) (count x)))
        smaller-or-equal (filter #(<= % mean) x)
        greater-or-equal (filter #(>= % mean) x)]
    (if (> (count smaller-or-equal) (count greater-or-equal)) (Math/floor mean) (Math/ceil mean))))

(defn solve-part-two [x]
  (calculate-min-cost (inc-distance (calc-mean x)) x))

(def part-two-test-solution (solve-part-two input/test-input))

(def part-two-solution (solve-part-two input/puzzle-input))


