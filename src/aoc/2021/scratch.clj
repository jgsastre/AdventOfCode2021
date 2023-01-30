(ns aoc.2021.scratch)

(defn cmp-strings [x y]
             (if (not= (count x) (count y))
                 "No"
                 (let [freq-x (frequencies x)
                       freq-y (frequencies y)
                       diff (merge-with (fn merge-dict-fn [x y] (Math/abs (- x y))) freq-x freq-y)
                       diff-count (count (filter #(< 3 %) diff))]
                     (if (> diff-count 0)
                         "No"
                         "Yes"))))


(comment
    (cmp-strings "foo" "bar")


         )



