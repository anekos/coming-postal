(ns coming-postal.core
  (:gen-class)
  (:require [clojure.pprint :refer [cl-format]]
            [coming-postal.service.core :refer [get-log]]))



(defn show [log]
  (if (empty? log)
    (cl-format *out* "Invalid~%")
    (doseq [{at :at state :state detail :detail} log]
      (cl-format *out* "~A - ~A - ~A~%" at state detail))))

(defn guess [code]
  {:service :japan-post
   :code code})


(defn -main
  "I don't do a whole lot."
  [& codes]
  (doseq [raw-code codes]
    (cl-format *out* "[~A]~%" raw-code)
    (let [code (guess raw-code)
          log (get-log code)]
      (show log))))
