(defproject coming-postal "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [clj-http "3.10.0"]
                 [enlive "1.1.6"]
                 [clojure.java-time "0.3.0"]
                 [net.snca/kunekune "0.1.6"]]
  :profiles {:dev
             {:dependencies [[org.clojure/tools.namespace "0.3.1"]]
              :source-paths ["src" "dev"]
              :aot []
              :repl-options {:init-ns user}}
             :uberjar
             {:aot :all
              :main coming-postal.core}})
