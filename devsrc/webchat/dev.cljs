(ns webchat.dev
  (:require [webchat.core :as webchat]
            [figwheel.client :as fw]))

(fw/start {:on-jsload webchat/run
           :websocket-url "ws://localhost:3449/figwheel-ws"})
