(ns aoc.2021.day8.parse
  (:require
   [clojure.string :as str]))

(defn- parse-words [s]
  (str/split s #" "))

(defn- parse-line [s]
  (let [[left right] (str/split s #" \| ")]
        {:measures (parse-words (str/trim left))
         :digits (parse-words (str/trim right))}))

(defn parse-input [lines]
  (map parse-line (str/split-lines lines)))
