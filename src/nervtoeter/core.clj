(ns nervtoeter.core
  (:use [jayq.core :only [$ append]]))

(def sounds
  {"ovation" "sound/ovation.mp3"})

(def audio ($ :#player))

(def player (atom nil))

(defn ^:export init-player []
  (let [pl (js/Audio. "")]
    (append audio pl)
    (reset! player pl)))

(init-player)

(defn ^:export play-sound 
  ""
  [sound]
  (if-let [s (get sounds sound)]
    (do
      (set! (.-src @player) s)
      (.log js/console @player)
      (. @player (play)))
    (js/alert "Sound nicht gefunden")))

