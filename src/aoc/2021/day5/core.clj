(ns aoc.2021.day5.core
  (:require
   [aoc.2021.day5.input :as input]))

(Math/abs -2)

(defn- straight-line [[[x1 y1] [x2 y2]]]
  (or (= x1 x2) (= y1 y2)))

(defn- add-line [board [[x1 y1] [x2 y2]]]
  (let [n (max (Math/abs (- x1 x2)) (Math/abs (- y1 y2)))
        dx (cond (> x1 x2) -1 (> x2 x1) 1 :else 0)
        dy (cond (> y1 y2) -1 (> y2 y1) 1 :else 0)]
  (loop [b board
         i 0]
    (if (> i n) b
      (recur (update-in b [(+ y1 (* dy i)) (+ x1 (* dx i))] inc) (+ i 1))))))

(defn- count-danger [board]
  (loop [rows board
         acc 0]
    (if (empty? rows)
      acc
      (recur
        (rest rows)
        (+ acc (count (filter #(> % 1) (first rows))))))))

(defn- max-coord [[p1 p2]]
  (max (apply max p1) (apply max p2)))

(defn- create-board [f lines]
  (let [filtered-lines (filter f lines)
        size (+ (apply max (map max-coord lines)) 1)
        board (vec (repeat size (vec (repeat size 0))))]
    (loop [l filtered-lines
           b board]
      (if (empty? l)
        {:board b :danger (count-danger b)}
        (recur (rest l) (add-line b (first l)))))))


;; Solve test input part one
(def solution-test-input-part-one (create-board straight-line input/test-input))

;; Solve part one
(def solution-part-one (create-board straight-line input/puzzle-input))

(defn- also-diagonal [[[x1 y1] [x2 y2]]]
  (or (= x1 x2) (= y1 y2) (= (max (- y1 y2) (- y2 y1)) (max (- x1 x2) (- x2 x1)))))

;; Solve test input part two
(def solution-test-input-part-one (create-board also-diagonal input/test-input))

(create-board also-diagonal [[[0 0][5 5]] [[0 9][9 0]] [[9 9][0 0]]])

;; Solve part two
(def solution-part-one (create-board also-diagonal input/puzzle-input))
