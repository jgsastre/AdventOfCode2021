(ns aoc.2021.day5.parse
  (:require
   [clojure.string :as str]))

(defn- parse-point [point]
  (vec (map #(Integer/parseInt %) (str/split point #","))))

(defn- parse-line [line]
  (vec (map parse-point (str/split line #" -> "))))

(defn parse [lines]
  (map parse-line lines))


