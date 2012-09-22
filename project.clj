(defproject nervtoeter "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :plugins [[lein-cljsbuild "0.2.4"]]
  :dependencies [[org.clojure/clojure "1.4.0"]]
  :cljsbuild {:crossovers [nervtoeter]
              :crossover-path ".bin"
              :builds [{
                        :compiler {:output-to "resources/public/app.js"
                                   :output-dir "resources/public/"
                                   :pretty-print true
                                   :optimizations :simple}
                        :source-path "bin"}]})
