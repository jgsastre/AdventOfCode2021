(ns aoc.2021.day11.core
  (:require
   [aoc.2021.day11.input :as input]
   [clojure.set :as s]))

(defn- get-flashing-octopuses
  "returns a vector of octopus to flash"
  [{:keys [cols rows]} board]
  (for [row (range rows)
        col (range cols)
        :when (< 9 (get-in board [row col]))]
    [row col]))

(defn- get-neighbors [{rows :rows cols :cols} [row col]]
  (let [valid-coords? (fn valid-coords? [[x y]]
                        (and (>= x 0) (>= y 0) (< x rows) (< y cols)))]
    (for [dx [-1 0 1]
          dy [-1 0 1]
          :let [new-coord [(+ row dx) (+ col dy)]]
          :when (valid-coords? new-coord)]
      new-coord)))

(defn- flash-octopus [input board p]
  (reduce (fn [acc x] (update-in acc x inc)) board (get-neighbors input p)))

(defn- increase-board [board]
  (mapv (partial mapv inc) board))

(defn- evolve
  "evolve the board one time step"
  [input]
  (loop [b (increase-board (:board input))
         flashed-octopuses #{}
         flash-count 0]
    (let [flashing-octopuses (s/difference (set (get-flashing-octopuses input b)) flashed-octopuses)]
      (if (empty? flashing-octopuses)
        ; We're done, just reset octopuses that flashed in this step
        (-> input
            (assoc :board (reduce (fn [acc x] (assoc-in acc x 0)) b flashed-octopuses))
            (assoc :flashing-count flash-count))
        ; Otherwise continue iterating
        (recur
         (reduce (fn [acc x] (flash-octopus input acc x)) b flashing-octopuses)
         (s/union flashing-octopuses flashed-octopuses)
         (+ flash-count (count flashing-octopuses)))))))

(defn- count-flash [input iters]
  (reduce (fn [acc x] (+ acc (:flashing-count x))) 0 (take (+ 1 iters) (iterate evolve (assoc input :flashing-count 0)))))

(def solution-test-part-one (count-flash input/test-input 100))

(def solution-part-one (count-flash input/puzzle-input 100))


(defn- solve-part-two [{:keys [rows cols] :as input}]
  (let [n (* rows cols)]
    (count (take-while #(< (:flashing-count %) n) (iterate evolve (assoc input :flashing-count 0))))))

(def solution-test-part-two (solve-part-two input/test-input))

(def solution-part-two (solve-part-two input/puzzle-input))



