(ns user
  (:require [clojure.tools.namespace.repl :refer [refresh]]
            [clojure.repl :refer :all]
            [net.cgrand.enlive-html :refer [html-snippet select text]]
            [coming-postal.service.core :refer :all]
            [coming-postal.service.japanpost :as jp]
            [coming-postal.service.sagawa :as sg]
            [coming-postal.service.kuroneko :as ku]))
