(ns user
  (:require [clojure.tools.namespace.repl :refer [refresh]]
            [clojure.repl :refer :all]
            [coming-postal.service.japanpost :as jp]
            [coming-postal.service.sagawa :as sg]))


(defn init []
  (alter-var-root #'*print-level* (constantly 4))
  (alter-var-root #'*print-length* (constantly 40)))


(defn reset []
  (init)
  (refresh))


(init)
