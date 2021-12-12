(ns aoc.2021.day8.core
  (:require
   [aoc.2021.day8.input :as input]
   [clojure.set :as s]))

(defn- get-only-1-4-7-8 [{digits :digits}]
  (filter #(some (partial = (count %)) [2 3 4 7]) digits))

(get-only-1-4-7-8 (first input/test-input))

(defn- count-1-4-7-8 [measures]
  (reduce (fn [acc x] (+ acc (count (get-only-1-4-7-8 x)))) 0 measures))

(def solution-test-input-part-one (count-1-4-7-8 input/test-input))

(def solution-input-part-one (count-1-4-7-8 input/puzzle-input))

(defn- get-segments [m & xs]
  (set (apply str (map (partial get m) xs))))

(def rules
  [[1 (fn is-1? [_ x] (= 2 (count x)))]
   [7 (fn is-7? [_ x] (= 3 (count x)))]
   [4 (fn is-4? [_ x] (= 4 (count x)))]
   [8 (fn is-8? [_ x] (= 7 (count x)))]
   [9 (fn is-9? [m x] (s/subset? (get-segments m 4 7) (set x)))]
   [0 (fn is-0? [m x] (and (s/subset? (get-segments m 7) (set x)) (= (count x) 6)))]
   [6 (fn is-6? [_ x] (= (count x) 6))]
   [5 (fn is-5? [m x] (s/subset? (set x) (get-segments m 6)))]
   [3 (fn is-3? [m x] (s/subset? (get-segments m 7) (set x)))]
   [2 (fn is-2? [_ x] (= (count x) 5))]])

(defn- get-mapping [{measures :measures}]
  (s/map-invert (first (reduce (fn add-mapping [[m x] [k f]]
            (let [pred (partial f m)
                  measure (first (filter pred x))
                  remaining-measures (remove pred x)]
              [(assoc m k measure) remaining-measures])) [{} (map (comp (partial apply str) sort) measures)] rules))))

(defn- translate-digits [mapping {digits :digits}]
  (map (comp (partial get mapping) (partial apply str) sort) digits))

(defn- digits-to-number [[& digits]]
  (reduce (fn [acc x] (+ (* 10 acc) x)) 0 digits))

(defn- get-number [line]
  (-> line
      get-mapping
      (translate-digits line)
      digits-to-number))

(defn- solve-part-two [lines]
  (reduce (fn acc-lines [acc l] (+ acc (get-number l))) 0 lines))

(def solution-test-input-part-two (solve-part-two input/test-input))

(def solution-part-two (solve-part-two input/puzzle-input))
