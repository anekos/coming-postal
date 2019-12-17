(ns coming-postal.service.core)


(defmulti getter identity)

(defn extract-service [code]
  (if-let [[_ name code] (re-find #"\A(\w+):(.+)" code)]
    [(keyword name) code]
    (when-let [service (condp re-find code
                         #"\A[A-Z]{2}\d+[A-Z]{2}\z" :japan-post
                         nil)]
      [service code])))

(defn get-log [code]
  (when-let [[service code] (extract-service code)]
    (when-let [f (getter service)]
      (f code))))

(defmacro register-1 [f kw]
  `(defmethod coming-postal.service.core/getter ~kw [~'_] ~f))

(defmacro register [f & kws]
  `(do
     ~@(map
         (fn [kw]
           `(register-1 ~f ~kw))
         kws)))
