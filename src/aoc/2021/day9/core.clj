(ns aoc.2021.day9.core
  (:require
   [aoc.2021.day9.input :as input]
   [clojure.set :as s]))

(defn- get-neighbors [rows cols [row col]]
  (filterv (fn valid-coords? [[x y]]
             (and (>= x 0) (>= y 0) (< x rows) (< y cols)))
           [[row (- col 1)] [row (+ col 1)] [(- row 1) col] [(+ row 1) col]]))

(defn- is-min? [rows cols board point]
  (->> (get-neighbors rows cols point)
       (map (partial get-in board))
       (every? (partial < (get-in board point)))))

(defn- get-mins [board]
  (let [rows (count board)
        cols (count (first board))]
    (for [x (range rows)
          y (range cols)
          :when (is-min? rows cols board [x y])]
      [x y])))

(defn- sum-of-risk [board]
  (->> (get-mins board)
       (reduce (fn [acc x] (+ acc 1 (get-in board x))) 0)))

(def solution-test-parte-one (sum-of-risk input/test-input))

(def solution-parte-one (sum-of-risk input/puzzle-input))

(let [x input/test-input
      rows (count x)
      cols (count (first x))]
  (->> x
       get-mins
       (mapcat (partial get-neighbors rows cols))))

(defn- get-boundary [board basin]
  (let [rows (count board)
        cols (count (first board))
        set-of-neighbors (fn [x] (set (get-neighbors rows cols x)))
        boundary-candidate (transduce (map set-of-neighbors) s/union basin)
        get-value (partial get-in board)]
    (set (filter (fn max-and-not-9 [x]
                   (and
                    (not= 9 (get-value x))
                    (some
                     (comp (partial > (get-value x)) get-value)
                     (s/intersection (set-of-neighbors x) basin))))
                 boundary-candidate))))

(defn- get-basin [board m]
  (loop [basin (set [m])
         i 0]
    (let [boundary (get-boundary board basin)]
      (if (or (empty? boundary) (>= i 1000)) basin
          (recur (s/union basin boundary) (+ i 1))))))

(defn- second-part-respone [board]
  (->> (get-mins board)
  (map (partial get-basin board))
  (map count)
  (sort >)
  (take 3)
  (reduce *)))

(def solution-test-parte-one (second-part-respone input/test-input))

(def solution-part-two (second-part-respone input/puzzle-input))

