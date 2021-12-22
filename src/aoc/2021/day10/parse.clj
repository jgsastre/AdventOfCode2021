(ns aoc.2021.day10.parse
  (:require
   [clojure.string :as str]))

(defn parse-input [input]
  (->> input
       (str/split-lines)
       (map str/trim)))
