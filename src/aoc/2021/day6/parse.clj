(ns aoc.2021.day6.parse
  (:require
   [clojure.string :as str]))

(defn parse-input [s]
  (let [f (frequencies (map #(Integer/parseInt %) (str/split s #",")))]
    (reduce (fn [acc x] (conj acc (get f x 0))) [] (range 9))))

