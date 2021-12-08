(ns aoc.2021.day4.parse
  (:require
   [clojure.string :as str]))

(defn- parse-numbers [line]
  (vec (map #(Integer/parseInt %) (str/split line #","))))

(defn- parse-ticket [lines]
  (let [size (count lines)]
    {:numbers (vec (map (fn parse-line [line]
                          (vec (map
                                #(Integer/parseInt %)
                                (str/split (str/trim line) #" +")))) lines))
     :chosen (vec (repeat size (vec (repeat size 0))))
     :cols (vec (repeat size 0))
     :rows (vec (repeat size 0))}))

(defn parse-games [lines]
  {:chosen-numbers (parse-numbers (first lines))
   :tickets (vec (loop [xs (drop 2 lines)
                        tickets []]
                   (if (empty? xs) tickets
                       (recur (drop 6 xs) (conj tickets (parse-ticket (take 5 xs)))))))})

