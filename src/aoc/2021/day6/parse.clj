(ns aoc.2021.day6.parse
  (:require
   [clojure.string :as str]))

(defn parse-input [s]
  (vec (map #(Integer/parseInt %) (str/split s #","))))

