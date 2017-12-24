(ns bitbops.game.game)


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
    (-> ent
       (assoc :x newx)
       (assoc :y newy))))

(defn tick [game]
  (let [ent (:entities game)]
    (assoc game :entities (map move ent))))

(def game (atom nil))

(defn gloop []
  (do
    (reset! game (assoc {} :entities (map newSampEntity (range 3))))
    (loop [c 0]
      (let
          [f (future (tick @game))
           t (future (Thread/sleep 200))]
        (reset! game @f)
        @t
        ;(println @game)
        (if (< c 10)
          (recur (inc c))
          @game)
        ))))
