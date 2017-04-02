(defproject cljs-pure-web "0.1.0-SNAPSHOT"
  :dependencies [[re-frame "0.9.0"]
                 [figwheel "0.5.6"]
                 [com.cemerick/piggieback "0.2.1"]
                 [re-com "0.9.0"]
                 [org.clojure/clojure        "1.8.0"]
                 [org.clojure/clojurescript  "1.9.227"]
                 [org.clojure/core.incubator "0.1.4"]
                 [reagent                    "0.6.0"]]

  :plugins [[lein-cljsbuild "1.1.4"]
            [lein-figwheel "0.5.6"]]

  :hooks [leiningen.cljsbuild]

  :source-paths ["cljs" "devsrc"]

  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"]

  :profiles {:dev {:cljsbuild
                   {:builds {:client {:source-paths ["devsrc"]}}}}}


  :cljsbuild {:builds
              {:client {:source-paths ["cljs"]

                        :compiler     {:main                 webchat.dev
                                       :asset-path           "js/compiled/out"
                                       :output-to            "resources/public/js/compiled/cljs_webchat.js"
                                       :output-dir           "resources/public/js/compiled/out"
                                       :optimizations        :none
                                       :source-map-timestamp true}}}}


  :figwheel {:nrepl-port 7888
             :css-dirs   ["resources/public/css"]})
