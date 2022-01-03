(ns aoc.2021.day12.parse
  (:require
   [clojure.string :as str]))

(defn parse-input [input]
  (assoc
   (->> input
        (str/split-lines)
        (reduce (fn [acc x]
                  (let [[k v] (str/split x #"-")]
                    (-> acc
                        (update k #(conj % v))
                        (update v #(conj % k))))) {}))
   "end" []))

