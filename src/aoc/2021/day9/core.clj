(ns aoc.2021.day9.core
  (:require
   [aoc.2021.day9.input :as input]
   [clojure.set :as s]))

(defn- get-neighbors [rows cols row col]
  (filter (fn valid-coords? [[x y]]
            (and (>= x 0) (>= y 0) (< x rows) (< y cols)))
          [[row (- col 1)] [row (+ col 1)] [(- row 1) col] [(+ row 1) col]]))

(defn- is-min? [rows cols board row col]
  (->> (get-neighbors rows cols row col)
       (map (partial get-in board))
       (every? (partial < (get-in board [row col])))))

(defn- get-mins [board]
  (let [rows (count board)
        cols (count (first board))]
    (println rows cols)
    (for [x (range rows)
          y (range cols)
          :when (is-min? rows cols board x y)]
      [x y])))

(defn- sum-of-risk [board]
  (->> (get-mins board)
       (reduce (fn [acc x] (+ acc 1 (get-in board x))) 0)))

(def solution-test-parte-one (sum-of-risk input/test-input))

(def solution-parte-one (sum-of-risk input/puzzle-input))

(defn- get-basin [board [x y]]
  (let [rows (count board)
        cols (count (first board))]
    (loop [basin (set [x y])
           boundary-candidate (map (fn get-neighbors rows cols  #(= 9 %) (get-neighbors rows cols x y))]
      (let [boundary (remove #(= 9 %) (get-neighbors rows cols x y))]
      (if? (empty? boundary) basin
           (recur
            (s/union
             (filter (fn increase-basin? [[x y]]
                       (some (partial > x) (s/intersection (set (get-neighbors rows cols x y)) basin)))
                     boundary)
             basin)
            new-boundary)))))


(set [[0 1] [1 1]])
(vec (s/intersection #{[0 0] [1 1] [0 1]} #{[1 1]}))
(s/union #{[0 0] [1 1] [0 1]} #{[1 1]})
    ;(if? (empty? bounday) basin
    ;           ;(filter (fn in-basin [x] (every? (and (partial > (get-in board rows cols x y)) (not= 9)) (map #(apply get-in board %)))))
    ;     (recur
    ;       (conj basin (filter ((get-neighbors rows cols)))
    ;       (

(print input/puzzle-input)

(print input/test-input)
(> 9 2 1 2 3 4)
(get-in [[1 2 3] [4 5 6]] [1 1])


