(ns aoc.2021.day12.core
  (:require
   [aoc.2021.day12.input :as input]))

(defn- important? [cave]
  (every? #(Character/isUpperCase %) cave))

(defn- visited?
  "given vector representing a path returns wether a cave
  was already visited or not"
  [path cave]
  (> (.indexOf path cave) -1))

(defn- destinies
  "returns a vector of possible destinies given a map and
  a path so far"
  [m p]
  (let [last-step (last p)
        possible-next-step (get m last-step)]
    (filter (fn filter-destinies [x]
              (or (not (visited? p x)) (important? x))) possible-next-step)))

(defn- add-destinies
  "given a map and a path returns a vector of paths with new destinies added"
  [m p]
  (let [new-destinies (destinies m p)]
    (if (empty? new-destinies)
      [p]
      (map #(conj p %) new-destinies))))

(defn- get-small-caves [m]
  (filter #(not (important? %)) (keys m)))

(defn- caves-count [path_vector]
  (reduce (fn [acc x] (+ acc (count x))) 0 path_vector))

(defn- get-ways [m]
  (loop [ways [["start"]]
         i 0]
    (let [new-ways (mapcat #(add-destinies m %) ways)]
      (if (= (caves-count new-ways) (caves-count ways))
      ways
      (recur new-ways (inc i))))))

(defn- solve-part-one [m]
  (let [ways (get-ways m)
        ends-correctly? #(= (last %) "end")
    ending-ways (filter ends-correctly? ways)]
  {:count (count ending-ways)}))

(def solution-tiny-input-part-one (solve-part-one input/tiny-input))

(def solution-test-input-part-one (solve-part-one input/test-input))

(def solution-larger-test-input-part-one (solve-part-one input/larger-test-input))

(def solution-part-one (time (solve-part-one input/puzzle-input)))






(caves-count (reduce (fn [acc x] (concat acc (add-destinies input/tiny-input x))) []
'(["start" "b" "end"] ["start" "b" "d"] ["start" "b" "A" "end"] ["start" "b" "A" "c" "A" "end"] ["start" "A" "end"] ["start" "A" "b" "end"] ["start" "A" "b" "d"] ["start" "A" "b" "A" "end"] ["start" "A" "b" "A" "c" "A"] ["start" "A" "c" "A" "end"] ["start" "A" "c" "A" "b" "end"] ["start" "A" "c" "A" "b" "d"] ["start" "A" "c" "A" "b" "A"])))

(filter #(= (last %) "end") '(["start" "b" "end"] ["start" "b" "d"] ["start" "b" "A" "end"] ["start" "b" "A" "c" "A" "end"] ["start" "A" "end"] ["start" "A" "b" "end"] ["start" "A" "b" "d"] ["start" "A" "b" "A" "end"] ["start" "A" "b" "A" "c" "A"] ["start" "A" "c" "A" "end"] ["start" "A" "c" "A" "b" "end"] ["start" "A" "c" "A" "b" "d"] ["start" "A" "c" "A" "b" "A"]))

(caves-count '(["start" "b" "end"] ["start" "b" "d"] ["start" "b" "A" "end"] ["start" "b" "A" "c" "A" "end"] ["start" "A" "end"] ["start" "A" "b" "end"] ["start" "A" "b" "d"] ["start" "A" "b" "A" "end"] ["start" "A" "b" "A" "c" "A"] ["start" "A" "c" "A" "end"] ["start" "A" "c" "A" "b" "end"] ["start" "A" "c" "A" "b" "d"] ["start" "A" "c" "A" "b" "A"]))
(add-destinies input/tiny-input ["start" "A" "c" "A" "b"])
(conj [1 2 3 4] 1)
(destinies input/tiny-input ["start", "b", "end"])
(add-destinies input/tiny-input ["start", "b"])
(get-small-caves input/tiny-input)
;((contains-all-small-caves? input/tiny-input)["start" "b" "A" "end"])
(every? (fn [x] (some #{x} ["start" "b" "A" "end"])) ["b" "end" "start"])

(filter #(= (last %) "end") '(["start" "b" "end"] ["start" "b" d] ["start" "b" "A" "end"] ["start" "b" "A" "c" "A" "end"] ["start" "A" "end"] ["start" "A" "b" "end"] ["start" "A" "b" d] ["start" "A" "b" "A" "end"] ["start" "A" "b" "A" "c" "A" "end"] ["start" "A" "c" "A" "end"] ["start" "A" "c" "A" "b" "end"] ["start" "A" "c" "A" "b" d] ["start" "A" "c" "A" "b" "A" "end"]))
