(ns coming-postal.service.sagawa
  (:require [clj-http.client :as client]
            [clojure.string :as string :refer [trim]]
            [coming-postal.service.core :refer [get-log]]
            [net.cgrand.enlive-html :refer [html-snippet select text]]
            [java-time :as time]))

(def url "https://k2k.sagawa-exp.co.jp/p/web/okurijosearch.do")

(defn get-log-html [code]
  (-> url
      (client/post
       {:form-params
        {:okurijoNo code
         :x 17
         :y 23}})
      :body
      html-snippet))

(defn extract-log-entries [html]
  (-> html
      (select [:table.table_basic.table_okurijo_detail2])
      second
      (select [:tr])
      rest))

(defn fix-at [s]
  ; FIXME should use 出荷日
  (str
    (time/format "yyyy" (time/zoned-date-time))
    " "
    s))

(defn fix-state [s]
  (string/replace s "↓" ""))

(defn parse-log-entry [html]
  (let [columns (select html [:td])
        [state at detail] (map (comp trim text) columns)]
    {:at (fix-at at)
     :state (fix-state state)
     :detail detail}))

(defmethod get-log :sagawa [_ code]
  (->>
   (-> code
       get-log-html
       extract-log-entries)
   (map parse-log-entry)))
