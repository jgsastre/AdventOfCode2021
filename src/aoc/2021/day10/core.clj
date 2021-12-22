(ns aoc.2021.day10.core
  (:require
   [aoc.2021.day10.input :as input]))

(defn- analyze
  [rf]
  (let [state_ (volatile! {::opened "" ::col 0})]
    (fn
      ([] (rf))
      ([result] (let [rt (if (empty? (::opened @state_))
                           result (let [last-error (-> @state_
                                                       (assoc ::unfinished (::col @state_)))]
                                    (unreduced (rf result last-error))))]
                  (rf rt)))
      ([result input]
       (let [opening {\( \) \[ \] \{ \} \< \>}
             closing  {\) \( \] \[ \} \{ \> \<}
             state @state_]
         (cond
           (opening input)
           (let [new-val
                 (vreset! state_ (-> state
                                     (update ::col inc)
                                     (update ::opened str input)))]
             (rf result new-val))
           (closing input)
           (let [[x & xs] (reverse (::opened state))
                 expected (opening x)]
             ;(println "This:" input "closes:" x "and matches:" (opening x))
             (cond
               (not= expected input) (let [error-state (-> state
                                                           (assoc ::corrupted {::expected expected ::received input}))]
                                       (vreset! state_ (assoc state ::opened ""))
                                       (reduced (rf result error-state)))
               (nil? x) (let [error-state (-> state (assoc ::unexpected-close input))]
                          (vreset! state_ (assoc state ::opened ""))
                          (reduced (rf result error-state)))
               :else (let [new-val (vreset! state_ (-> state
                                                       (update ::col inc)
                                                       (assoc ::opened (apply str (reverse xs)))))]
                       (rf result new-val))))))))))

(defn- get-points [results]
  (let [missing-parenthesis (count (filter (fn [x] (= \) (get-in x [::corrupted ::received]))) results))
        missing-bracket (count (filter (fn [x] (= \] (get-in x [::corrupted ::received]))) results))
        missing-brace (count (filter (fn [x] (= \} (get-in x [::corrupted ::received]))) results))
        missing-angle (count (filter (fn [x] (= \> (get-in x [::corrupted ::received]))) results))
        unexpected-close (count (filter (fn [x] (get-in x [::unexpected-close])) results))]
    {::missing-parenthesis missing-parenthesis
     ::missing-bracket missing-bracket
     ::missing-brace missing-brace
     ::missing-angle missing-angle
     ::unexpected-close unexpected-close
     :score (+ (* 3 missing-parenthesis) (* 57 missing-bracket) (* 1197 missing-brace) (* 25137 missing-angle))}))

(defn- index-and-value [cols]
  (map (fn index-and-value-iterate [x] {::line x ::value (nth cols x)}) (range (count cols))))

(def solution-test-part-one (get-points (->> input/test-input
                                             (map (partial transduce analyze (fn ([_ x] x) ([x] x)) {}))
                                             (filter (fn [x] (get-in x [::corrupted]))))))

(def solution-part-one (get-points (->> input/puzzle-input
                                        (map (partial transduce analyze (fn ([_ x] x) ([x] x)) {}))
                                        ;(index-and-value)
                                        (filter (fn [x] (get-in x [::corrupted]))))))

(def solution-part-one (get-points (->> input/puzzle-input
                                        (map (partial transduce analyze (fn ([_ x] x) ([x] x)) {}))
                                        (index-and-value)
                                        (filter (fn [x] (= \) (get-in x [::value ::corrupted ::received]))))
                                        (map ::line))))

(defn- calc-score [{opened ::opened}]
  (reduce (fn calc-score-fn [acc x]
            (let [score (cond
                          (= \( x) 1
                          (= \[ x) 2
                          (= \{ x) 3
                          (= \< x) 4)] (+ score (* 5 acc)))) 0 (reverse opened)))
(defn- take-middle [cols]
  (first (drop (int (/ (count cols) 2)) cols)))

(defn- solve-part-two [x] (->> x
                               (map (partial transduce analyze (fn ([_ x] x) ([x] x)) {}))
                               (filter (fn [x] (get-in x [::unfinished])))
                               (map calc-score)
                               (sort >)
                               take-middle))

(def solution-test-part-two (solve-part-two input/test-input))

(def solution-part-two (solve-part-two input/puzzle-input))

