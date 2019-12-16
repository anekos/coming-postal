(ns coming-postal.service.core)


(defmulti get-log (fn [service _] service))

(defn get-log-by-tagged [code]
  (if-let [[_ name code] (re-find #"\A(\w+):(.+)" code)]
    (get-log (keyword name) code)
    (when-let [service (condp re-find code
                         #"\A[A-Z]{2}\d+[A-Z]{2}\z" :japan-post
                         nil)]
      (get-log service code))))
