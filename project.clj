(defproject bitbops "0.1.0-SNAPSHOT"
  :description "Maybe it's a game eventually?"
  :url "https://github.com/TenjouUtena/bitbops"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.946"]
                 [reagent "0.8.0-alpha2"]
                 [org.clojure/core.async "0.3.465"]
                 [http-kit "2.2.0"]
                 [compojure "1.6.0"]
                 [jarohen/chord "0.8.1"]
                 ]
  :plugins [[lein-cljsbuild "1.1.7"]]
  :cljsbuild {
                 :builds [{
                  :source-paths ["src"]
                  :compiler {
                     :output-to "target/classes/public/js/main.js"
                     :optimizations :whitespace
                     :pretty-print true}}]}
  :main bitbops.core
  :jvm-opts ["--add-modules" "java.xml.bind"]
  )
