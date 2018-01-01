(ns bitbops.web.client
  (:require [chord.client :refer [ws-ch]]
            [cljs.core.async :refer [<! close! alts! put! timeout]]
            [reagent.core :as r])
  (:require-macros [cljs.core.async.macros :refer [go]]))


(def game (r/atom {}))


(defn entity [ent]
  (let [x (+ 250 (:x ent))
        y (+ 250 (:y ent))]
    [:div {:key (:name ent) :style {:top x, :left y} :class "entity"}
     [:span (:name ent)]]))

(defn gameboard []
  [:div
   (map #(entity %) (:entities @game))])

(defn menuboard [ch]
  [:div [:div {:style {:background-color "grey"} :onClick #(put! ch {:quit true})} "Quit"]])

(defn mainscreen [ch]
  [:div [gameboard][:p][menuboard ch]])

(defn drawgame [ch]
  (r/render [mainscreen ch] (.getElementById js/document "game")))


(defonce run-once
  (go
    (let [{:keys [ws-channel]} (<! (ws-ch "ws://localhost:8080/ws"))]
      (drawgame ws-channel)
      (loop
          []
        (let [resp (<! ws-channel)]
          (if (:message resp)
            (do
              (reset! game (:message resp))
              (recur))
            (close! ws-channel)
            ))))))
