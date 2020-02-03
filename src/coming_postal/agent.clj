(ns coming-postal.agent
  (:require [clojure.core.cache :as cache]
            [clojure.edn :as edn]
            [clj-http.client :as http-client]
            [clojure.java.io :refer [file]]
            [me.raynes.fs :as fs]
            [cachify.core :refer [cachify]])
  (:refer-clojure :exclude [get]))


(def get (cachify :get (comp :body http-client/get)))
(def post (cachify :post (comp :body http-client/post)))
