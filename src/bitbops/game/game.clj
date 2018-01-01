(ns bitbops.game.game
  (:require [clojure.core.async :refer [>! go take!]]))


(defn newSampEntity [num]
  (let
      [vel (rand 25)
       face (rand (* Math/PI 2))
       name (str num)]
    {:x 0, :y 0, :vel vel, :face face, :name name}))

(defn move [ent]
  (let [
        x (:x ent)
        y (:y ent)
        vel (:vel ent)
        face (:face ent)
        newx (+ x (* (Math/sin face) vel))
        newy (+ y (* (Math/cos face) vel))
        ]
    (if (or
         (< newx -500)
         (< newy -500)
         (> newx 500)
         (> newy 500)
         )
      (assoc ent :face (rand (* Math/PI 2)))
      (-> ent
          (assoc :x newx)
          (assoc :y newy)))))

(defn tick [game]
  (let [ent (:entities game)]
    (assoc game :entities (map move ent))))

(def game (atom nil))

(defn doinput [data]
  (cond (get-in data [:message :quit])
    (reset! game (assoc @game :quit true))))

(defn gloop [chan]
  (do
    (reset! game (assoc {} :entities (map newSampEntity (range 6))))
    (loop [c 0]
      (let
          [f (future (tick @game))
           t (future (Thread/sleep 150))]
        (reset! game @f)
        (take! chan doinput)
        @t

        (go (>! chan @game))
        (if-not (:quit @game)
          (recur (inc c))
          @game)
        ))))
