(ns coming-postal.service.japanpost
  (:require [net.cgrand.enlive-html :as eh :refer [select text attr=]]
            [clj-http.client :as client]
            [coming-postal.service.core :refer [get-log]]))



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

(defn get-log-html [code]
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


(defmethod get-log :japan-post [{code :code}]
  (->>
   (-> code
       get-log-html
       extract-log-entries)
   (map parse-log-entry)
   (filter valid-entry?)))
