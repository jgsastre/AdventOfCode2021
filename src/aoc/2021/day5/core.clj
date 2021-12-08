(ns aoc.2021.day5.core
  (:require
   [aoc.2021.day5.input :as input]))

(defn- straight-line [[[x1 y1] [x2 y2]]]
  (or (= x1 x2) (= y1 y2)))

(defn- add-line [board [[x1 y1] [x2 y2]]]
  (loop [y_board board
         y (min y1 y2)]
    (if (> y (max y1 y2)) y_board
        (let [updated_col (loop [x_board y_board
                                 x (min x1 x2)]
                            (if (> x (max x1 x2)) x_board (recur (update-in x_board [y x] inc) (+ x 1))))]
          (recur updated_col (+ y 1))))))

(defn- count-danger [board]
  (loop [rows board
         acc 0]
    (if (empty? rows)
      acc
      (recur
        (rest rows)
        (+ acc (count (filter #(> % 1) (first rows))))))))

(defn- create-board [lines]
  (let [filtered-lines (filter straight-line lines)
        board (repeat (repeat 0))]
    (loop [l filtered-lines
           b board]
      (if (empty? l)
        {:board b :danger (count-danger b)}
        (recur (rest l) (add-line b (first l)))))))

;; Solve test input
(create-board input/test-input)

;; Solve part one
(create-board input/puzzle-input)


