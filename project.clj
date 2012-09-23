(defproject nervtoeter "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :plugins [[lein-cljsbuild "0.2.4"]]
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [jayq "0.1.0-alpha3"]]
  :cljsbuild {:crossovers [nervtoeter]
              :crossover-path ".bin"
              :builds [{
                        :compiler {:output-to "resources/public/app.js"
                                   :output-dir "resources/public/"
                                   :libs ["resources/public/extern/jquery-1.8.2.min.js"
                                          "resources/public/extern/jquery.mobile-1.1.1.min.js"]
                                   :pretty-print true
                                   :optimizations :simple}
                        :source-path ".bin"}]})
