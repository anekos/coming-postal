(ns coming-postal.core
  (:gen-class)
  (:require [clojure.pprint :refer [cl-format]]
            [cachify.core :refer [perm-on-exit]]
            [coming-postal.service.core :refer [get-log]]
            [coming-postal.service.sagawa]
            [coming-postal.service.japanpost]
            [coming-postal.service.kuroneko]))



(defn show [log]
  (if (empty? log)
    (cl-format *out* "Invalid~%")
    (doseq [{at :at state :state detail :detail country :country} log]
      (cl-format *out* "~A - ~A - ~A (~A)~%" at state detail (or country "")))))

(defn guess-service [code]
  (condp re-find code
    #"\A[A-Z]{2}\d+[A-Z]{2}\z" :japan-post
    #".*" [:sagawa :japan-post]))


(defn -main
  "I don't do a whole lot."
  ([]
   (cl-format *out* "Usage: coming-postal <CODE>"))
  ([& codes]
   (perm-on-exit)
   (doseq [code codes]
     (cl-format *out* "[~A]~%" code)
     (-> code
         get-log
         show))))
