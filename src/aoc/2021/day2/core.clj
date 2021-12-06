(ns aoc.2021.day2.core
  (:require
    [clojure.string :as str]))

(defn get-map [movement amount]
  {(keyword movement) (Integer/parseInt amount)})

(defn parse-lines [lines]
  (map #(apply get-map (str/split % #" ")) lines))

(defn get-movements []
  (-> (slurp "./src/aoc/2021/day2/input.txt")
      (str/split-lines)
      (parse-lines)))

(defn move [pos {forward :forward down :down up :up}]
  (cond
    (some? forward) (update pos :x (partial + forward))
    (some? down) (update pos :y (partial + down))
    (some? up) (update pos :y #(- % up))))

(def initial-pos {:x 0 :y 0})

(defn calc-final-pos [moves]
  (reduce move initial-pos moves))

(def test-input [
                 {:forward 5}
                 {:down 5}
                 {:forward 8}
                 {:up 3}
                 {:down 8}
                 {:forward 2}])

(defn calc-value [{x :x y :y}]
  (* x y))

(defn solve-part-one []
  (-> (get-movements)
      (calc-final-pos)
      (calc-value)))

(solve-part-one) ; 1692075

(def initial-pos-part-two {:x 0 :y 0 :aim 0})

(defn move-part-two [pos {forward :forward down :down up :up}]
  (cond
    (some? down) (update pos :aim (partial + down))
    (some? up) (update pos :aim #(- % up))
    (some? forward) (-> pos
                        (update :x (partial + forward))
                        (update :y (partial + (* (:aim pos) forward))))))

(defn calc-final-pos-part-two [moves]
  (reduce move-part-two initial-pos-part-two moves))

(defn solve-part-two []
  (-> (get-movements)
      (calc-final-pos-part-two)
      (calc-value)))

(solve-part-two) ; 1749524700
