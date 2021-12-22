(ns aoc.2021.day11.parse
  (:require
   [clojure.string :as str]))

(defn parse-input [input]
  (let [board (vec (->> input
       (str/split-lines)
       (mapv (comp (partial mapv #(Character/digit % 10)) str/trim))))]
    {:board board :rows (count board) :cols (count (first board))}))
