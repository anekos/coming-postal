(ns user
  (:require [clojure.tools.namespace.repl :refer [refresh]]
            [clojure.repl :refer :all]
            [net.cgrand.enlive-html :as eh :refer [select text attr=]]
            [clojure.pprint :refer [pp pprint cl-format]]
            [clj-http.client :as client]
            [coming-postal.core :refer :all]))


(defn init []
  (set! *print-level* 4)
  (set! *print-length* 40))


(defn reset []
  (init)
  (refresh))


(init)



(defmacro selectext [html & selector]
  `(apply
    str
    (map
     text
     (select ~html ~@selector))))

(defn make-url [code]
  (str
    "https://trackings.post.japanpost.jp/services/srv/search/direct?reqCodeNo1="
    code
    "&searchKind=S002&locale=ja"))

(defn get-state-html [code]
  (-> code
      make-url
      client/get
      :body
      eh/html-snippet))

(defn extract-log-entries [html]
  (select html [:body [:table.tableType01 (attr= :summary "履歴情報")] :tr]))

(defn parse-log-entry [e]
  {:at
   (selectext e [:td.w_120])
   :state
   (selectext e [:td.w_150])
   :detail
   (selectext e [:td.w_180])})

(defn valid-entry? [e]
  (not
   (or (empty? (:at e))
       (empty? (:state e)))))


(defn get-state [code]
  (->>
   (-> code
       get-state-html
       extract-log-entries)
   (map parse-log-entry)
   (filter valid-entry?)))
