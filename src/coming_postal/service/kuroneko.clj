(ns coming-postal.service.kuroneko
  (:gen-class)
  (:require [clj-http.client :as client]
            [clojure.string :as string :refer [trim]]
            [coming-postal.service.core :refer [register]]
            [net.cgrand.enlive-html :refer [html-snippet select text]]))


(def url "http://link.kuronekoyamato.co.jp/link/send/receive/lneko")


(defn extract-log-entries [html]
  (-> html
      (select [:table.meisai])
      first
      (select [:tr])
      rest))

(defn parse-log-entry [html]
  (let [[_ state date time detail]
        (map (comp trim text) (select html [:td]))]
    {:at (str date " " time)
     :state state
     :detail detail}))

(defn get-log-html [code]
  (-> url
      (client/post
        {:headers
         {:User-Agent "Mozilla/5.0 (X11; Linux x86_64; rv:71.0) Gecko/20100101 Firefox/71.0"}
         :form-params
         {:number00 "1"
          :number01 code
          :userid "600121"
          :n01 code}
         :decode-body-headers true
         :as :auto})
      :body
      html-snippet))

(defn get-log [code]
  (->>
   (-> code
       get-log-html
       extract-log-entries)
   (map parse-log-entry)))

(register get-log
          :kuroneko
          :yamato
          :k
          :y)
