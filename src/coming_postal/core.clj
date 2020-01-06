(ns coming-postal.core
  (:gen-class)
  (:require [clojure.pprint :refer [cl-format]]
            [coming-postal.agent :as agent]
            [coming-postal.service.core :refer [get-log]]
            [coming-postal.service.sagawa]
            [coming-postal.service.japanpost]
            [coming-postal.service.kuroneko]))



(defn show [log]
  (if (empty? log)
    (cl-format *out* "Invalid~%")
    (doseq [{at :at state :state detail :detail} log]
      (cl-format *out* "~A - ~A - ~A~%" at state detail))))

(defn guess-service [code]
  (condp re-find code
    #"\A[A-Z]{2}\d+[A-Z]{2}\z" :japan-post
    #".*" [:sagawa :japan-post]))


(defn -main
  "I don't do a whole lot."
  ([]
   (cl-format *out* "Usage: coming-postal <CODE>"))
  ([& codes]
   (doseq [code codes]
     (cl-format *out* "[~A]~%" code)
     (-> code
         get-log
         show))
   (agent/store-cache)))
