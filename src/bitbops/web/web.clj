(ns bitbops.web.web
  (:require [compojure.core :refer [defroutes, GET]]
            [compojure.route :refer [resources]]
            [chord.http-kit :refer [with-channel]]
            [bitbops.game.game :refer [gloop]])
  )



(defn ws-handler [req]
  (with-channel req ws-chan
    (gloop ws-chan)))

(defroutes app
  (GET "/ws" [] ws-handler)
  (GET "/" [] "<h1>Hello</h1>")
  (resources "/"))
