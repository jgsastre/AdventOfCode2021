(ns scratch
  (:require [clojure.tools.cli :refer [parse-opts]])
  (:import [java.time Instant]
           [java.time.format DateTimeFormatter]))

(comment
  (defn comb [x]
    (print x))

  (defn comb [elems]
    (loop [x elems
           result [[]]]
      (let [[head & tail] x]
        (if (nil? head)
          result
          (recur tail (concat result (map #(conj % head) result)))))))

  (defn party [elems n]
    (filter #(= (count %) n) (comb elems)))

  (party [1 2 3] 2)

  (comb [1 2 3])
  (map #(conj % 2) [[] [2]])
  (concat [[]] [[2]])

;;(if (not= 1 *command-line-args*)
    ;;(println "Expected one param with timestamp"))

  (. Math PI)

  (def foo (.. (Instant/ofEpochSecond 1668011598.444) (toString)))

  (Instant/now)

  (. foo toString)
  (Integer/parseInt "2")

  (first [1 2 3 4])
  (.parse (DateTimeFormatter/ofPattern "yyyy-MM-dd'T'HH:mm:ssX") "2022-11-09T16:33:18Z"))

(let [cli-options [["-r" "--reverse" :default nil]]
      {:keys [options arguments]} (parse-opts *command-line-args* cli-options)
      arg (first arguments)]
  (if (:reverse options)
    (if arg
      (let [formatter (DateTimeFormatter/ofPattern "yyyy-MM-dd'T'HH:mm:ssX")
            date (.parse formatter arg)]
        (.. (Instant/from date) (toEpochMilli)))
      (.. (Instant/now) (toEpochMilli)))
    (.. (Instant/ofEpochSecond (Double/parseDouble arg)) (toString))))
