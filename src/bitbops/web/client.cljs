(ns bitbops.web.client
  (:require [chord.client :refer [ws-ch]]
            [cljs.core.async :refer [<! close! alts! timeout]]
            [reagent.core :as r])
  (:require-macros [cljs.core.async.macros :refer [go]]))


(def game (r/atom {}))


(defn entity [ent]
  (let [x (+ 500 (:x ent))
        y (+ 500 (:y ent))]
    [:div {:style {:top x, :left y} :class "entity"}
     [:span (:name ent)]]))

(defn gameboard []
  [:div
   (map #(entity %) (:entities @game))])

(defn drawgame []
  (r/render [gameboard] (.getElementById js/document "game")))


(defonce run-once
  (go
    (let [{:keys [ws-channel]} (<! (ws-ch "ws://localhost:8080/ws"))]
      (drawgame)
      (loop
          []
        (let [{:keys [message error]} (<! ws-channel)]
          (if error
            (close! ws-channel)
            (do
              (reset! game message)
              (recur))))))))
