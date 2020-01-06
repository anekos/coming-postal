(ns coming-postal.agent
  (:require [clojure.core.cache :as cache]
            [clojure.edn :as edn]
            [clj-http.client :as http-client]
            [clojure.java.io :refer [file]]
            [me.raynes.fs :as fs])
  (:refer-clojure :exclude [get]))


(def ^:dynamic *cache-file* (file (fs/home) ".local/var/cache/coming-postal/http-agent"))

(def ^:dynamic *cache*
  (atom (cache/ttl-cache-factory
          (if (.exists *cache-file*)
            (-> *cache-file*
                slurp
                edn/read-string)
            {})
          :ttl (* 1000 60 60))))

(defn store-cache []
  (fs/mkdirs (.getParent *cache-file*))
  (spit *cache-file*
        @*cache*))

(defn with-cache [f]
  (fn [& args]
    (cache/lookup
      (swap!
       *cache*
       cache/through-cache
       args
       (fn [args]
         (apply f args)))
     args)))

(def get (with-cache (comp :body http-client/get)))
(def post (with-cache (comp :body http-client/post)))
