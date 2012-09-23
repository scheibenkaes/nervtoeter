(ns nervtoeter.core
  (:use [jayq.core :only [$ append]]))

(def sounds
  {"Beginn" 
   [["ovation" "sound/ovation.mp3"]]})

(defn find-sound [group sound]
  (when-let [gr (get sounds group)]
    (first (for [s gr :when (= sound (first s))] (second s)))))

(def audio ($ :#player))

(def player (atom nil))

(defn ^:export init-player []
  (let [pl (js/Audio. "")]
    (append audio pl)
    (reset! player pl)))

($ init-player)

(defn ^:export play-sound 
  ""
  [group sound]
  (if-let [s (find-sound group sound)]
    (do
      (set! (.-src @player) s)
      (. @player (play)))
    (js/alert "Sound nicht gefunden")))

