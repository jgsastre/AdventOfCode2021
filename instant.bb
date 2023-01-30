#!/usr/bin/env bb
(ns instant
  (:require [clojure.tools.cli :refer [parse-opts]])
  (:import [java.time Instant]
           [java.time.format DateTimeFormatter]))

(let [cli-options [["-r" "--reverse" :default nil]]
      {:keys [options arguments]} (parse-opts *command-line-args* cli-options)
      arg (first arguments)]
  (if (:reverse options)
    (if arg
      (let [formatter (DateTimeFormatter/ofPattern "yyyy-MM-dd'T'HH:mm:ssX")
            date (.parse formatter arg)]
        (.toEpochMilli (Instant/from date)))
      (.toEpochMilli (Instant/now)))
    (.toString (Instant/ofEpochMilli (Long/parseLong arg)))))

