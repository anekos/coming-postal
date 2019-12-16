(ns coming-postal.html
  (:require [net.cgrand.enlive-html :refer [select text]]))


(defmacro selectext [html & selector]
  `(apply
    str
    (map
     text
     (select ~html ~@selector))))
