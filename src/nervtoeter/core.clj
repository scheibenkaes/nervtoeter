(ns nervtoeter.core
  (:use [jayq.core :only [$]]))

(def $jplayer ($ "#jplayer"))

(defn ^:export play-sound 
  ""
  [sound]
  (js/alert sound))

