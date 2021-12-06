(ns aoc.2021.day3.core
  (:require
    [clojure.string :as str]))

(def raw-test-input [
                 "00100"
                 "11110"
                 "10110"
                 "10111"
                 "10101"
                 "01111"
                 "00111"
                 "11100"
                 "10000"
                 "11001"
                 "00010"
                 "01010"])

(defn transform-input [input]
  (let
    [to-int (fn [x] (Character/digit x 10))
     string-to-array-of-int (fn [x] (vec (map to-int x)))]
    (vec (map (comp string-to-array-of-int char-array) input))))

(def puzzle-input
  (-> (slurp "./src/aoc/2021/day3/input.txt")
      (str/split-lines)
      transform-input))

(def test-input (transform-input raw-test-input))

(defn sum-bits [input]
  (let [size (count input)
        zeros (repeat 0)
        sum (fn [x y] (map + x y))
        sum-vector (reduce sum zeros input)]
        (map (fn [x]
               (cond
                 (> x (/ size 2)) 1
                 :else 0)) sum-vector)))

(defn invert [x]
  (map (fn [x]
         (cond
           (> x 0) 0
           :else 1)) x))

(defn to-decimal [xs]
  (loop [x xs
         m (java.lang.Math/pow 2 (- (count xs) 1))
         value 0]
    (if (empty? x) value
      (recur
        (rest x)
        (/ m 2)
        (+ value (* (first x) m))))))

(defn calc-power-consumption [input]
  (let [gamma-rate-bin (sum-bits input)
        gamma-rate (to-decimal gamma-rate-bin)
        epsilon-rate (to-decimal (invert gamma-rate-bin))]
        (* gamma-rate epsilon-rate)))

(calc-power-consumption puzzle-input) ; 3277364.0

(defn get-bits [idx byte-stream]
  (map #(nth % idx) byte-stream))

(defn freq [idx measures]
  (let [n (count measures)
        bits (get-bits idx measures)
        sum (reduce + bits)]
    {:1 sum :0 (- n sum)}))

(defn mayority-filter [idx freqs]
  (let [boundary (if (> (:0 freqs) (:1 freqs)) 0 1)]
  (fn [sample]
      (= (nth sample idx) boundary))))

(defn minority-filter [idx freqs]
  (let [boundary (if (< (:1 freqs) (:0 freqs)) 1 0)]
  (fn [sample] (= (nth sample idx) boundary))))

(defn get-meassure [f measures]
  (loop [xs measures
        idx 0]
   (if (= (count xs) 1) (first xs)
     (recur (filter (f idx (freq idx xs)) xs) (+ idx 1)))))

(defn calc-generator-ratings [input]
  (let [oxigen-generator-rating (get-meassure mayority-filter input)
        co2-scrubber-rating (get-meassure minority-filter input)]
    {:oxigen-generator-rating oxigen-generator-rating
     :co2-scrubber-rating  co2-scrubber-rating
     :life-support-rating (* (to-decimal oxigen-generator-rating) (to-decimal co2-scrubber-rating))}))

; Check test input
(calc-generator-ratings test-input)
; {:oxigen-generator-rating [1 0 1 1 1],
;  :co2-scrubber-rating [0 1 0 1 0],
;  :life-support-rating 230.0}

;; solve-part-two
(calc-generator-ratings puzzle-input) ; #'aoc.2021.day3.core/solve-part-two
; {:oxigen-generator-rating [1 1 0 1 1 1 1 1 1 1 1 1],
;  :co2-scrubber-rating [0 1 1 0 0 1 0 0 0 0 0 1],
;  :life-support-rating 5736383.0}





