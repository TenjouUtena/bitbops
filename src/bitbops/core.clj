(ns bitbops.core
  (:require [bitbops.web.web :refer [app]]
            [org.httpkit.server :refer [run-server]]))



(defn -main [& args]
  (run-server app {:port 8080})
  (println "It's up!"))
