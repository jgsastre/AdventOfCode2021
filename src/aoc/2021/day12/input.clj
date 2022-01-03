(ns aoc.2021.day12.input
  (:require
    [aoc.2021.day12.parse :as parse]))

(def tiny-input (parse/parse-input "start-A
start-b
A-c
A-b
b-d
A-end
b-end"))

(def test-input (parse/parse-input "dc-end
HN-start
start-kj
dc-start
dc-HN
LN-dc
HN-end
kj-sa
kj-HN
kj-dc"))

(def larger-test-input (parse/parse-input "fs-end
he-DX
fs-he
start-DX
pj-DX
end-zg
zg-sl
zg-pj
pj-he
RW-he
fs-DX
pj-RW
zg-RW
start-pj
he-WI
zg-he
pj-fs
start-RW"))

(def puzzle-input
  (-> (slurp "./src/aoc/2021/day12/input.txt")
      (parse/parse-input)))
