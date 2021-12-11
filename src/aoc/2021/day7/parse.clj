(ns aoc.2021.day7.parse
  (:require
   [clojure.string :as str]))

(defn parse-input [s]
  (map #(Integer/parseInt %) (str/split s #",")))
