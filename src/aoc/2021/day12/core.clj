(ns aoc.2021.day12.core
  (:require
   [aoc.2021.day12.input :as input]
   [clj-async-profiler.core :as prof]))

(defn- important? [^String cave]
  (every? #(Character/isUpperCase ^char %) cave))

(defn- visited?
  "given vector representing a path returns wether a cave
  was already visited or not"
  [^clojure.lang.PersistentVector path ^String cave]
  (> (.indexOf path cave) -1))

(defn- destinies
  "returns a vector of possible destinies given a map and
  a path so far"
  [filters m p]
  (let [last-step (last p)
        possible-next-step (get m last-step)]
    (filter (fn filter-destinies [x] (every? #(% p x) filters)) possible-next-step)))

(defn- add-destinies
  "given a map and a path returns a vector of paths with new destinies added that are compliant with filters"
  [filters m p]
  (let [new-destinies (destinies filters m p)]
    (if (empty? new-destinies)
      [p]
      (map #(conj p %) new-destinies))))

(defn- caves-count [path_vector]
  (reduce (fn [acc x] (+ acc (count x))) 0 path_vector))

(defn- get-ways [m filters]
  (loop [ways [["start"]]
         i 0]
    (let [new-ways (mapcat #(add-destinies filters m %) ways)]
      (if (= (caves-count new-ways) (caves-count ways))
        ways
        (recur new-ways (inc i))))))

(defn- solve-part-one [m]
  (let [filters [(fn important-or-not-visited [p x] (or (not (visited? p x)) (important? x)))]
        ways (get-ways m filters)
        ends-correctly? #(= (last %) "end")
        ending-ways (filter ends-correctly? ways)]
    {:count (count ending-ways)}))

(comment

  (def solution-tiny-input-part-one (solve-part-one input/tiny-input))

  (def solution-test-input-part-one (solve-part-one input/test-input))

  (def solution-larger-test-input-part-one (solve-part-one input/larger-test-input))

  (def solution-part-one (prof/profile (solve-part-one input/puzzle-input)))
  
  (prof/serve-files 8080))

(defn- non-important-cave-visitable? [p x]
  (let [times-already-visited (transduce (comp (filter #{x}) (map (fn [_] 1))) + p)]
    (or
     (< times-already-visited 1)
     (and (< times-already-visited 2)
          (let
           [non-important-caves-visited (sequence (filter #(not (important? %))) p)
            some-non-important-cave-visited-twice? (< (count (set non-important-caves-visited)) (count non-important-caves-visited))]
            (not some-non-important-cave-visited-twice?))))))

(defn- solve-part-two [m]
  (let [filters [(fn important-or-visited-twice-at-most [p x] (or (non-important-cave-visitable? p x) (important? x)))
                 (fn not-returning-to-start [_ x] (not= x "start"))]
        ways (get-ways m filters)
        ends-correctly? #(= (last %) "end")
        ending-ways (filter ends-correctly? ways)]
    {:count (count ending-ways)}))

(comment
  (def solution-tiny-input-part-two (solve-part-two input/tiny-input))

  (def solution-test-input-part-two (solve-part-two input/test-input))

  (def solution-larger-test-input-part-two (solve-part-two input/larger-test-input))

  (def solution-part-two (time (solve-part-two input/puzzle-input))))

