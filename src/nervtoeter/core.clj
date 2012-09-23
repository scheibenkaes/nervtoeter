(ns nervtoeter.core
  (:use [jayq.core :only [$ append]]))

(def sounds
  {
   "Beginn" 
   [["Ovation" "sound/ovation.mp3"]]
   "Sieg"
   [["Ovation" "sound/ovation.mp3"]]})

(defn find-sound [group sound]
  (when-let [gr (get sounds group)]
    (first (for [s gr :when (= sound (first s))] (second s)))))

(def audio ($ :#player))

(def player (atom nil))

(defn ^:export init-player []
  (let [pl (js/Audio. "")]
    (append audio pl)
    (reset! player pl)))

(defn ^:export divider [txt]
  (str "<li data-role='list-divider'>" txt "</li>"))

(defn ^:export play-button [group txt sample]
  (str "<li><a onclick=\"nervtoeter.core.play_sound('" group "','" txt "')\">" txt "</a></li>"))

(defn create-buttons [group sounds]
  (for [s sounds] (play-button group (first s) (second s))))

(defn ^:export create-lis []
  (reduce (fn [acc [group snds]]
            (let [div (divider group)
                  btns (create-buttons group snds)] 
              (concat acc [div] btns))
            ) [] sounds))

(defn ^:export setup-ul []
  (let [lis (create-lis)
        ul ($ :#ul)]
    (doseq [li lis]
      (append ul li))
    (.listview ul "refresh")))

(defn ^:export init-app [& _]
  (init-player)
  (setup-ul))

($ init-app)

(defn ^:export play-sound 
  ""
  [group sound]
  (if-let [s (find-sound group sound)]
    (do
      (set! (.-src @player) s)
      (. @player (play)))
    (do
      (.log js/console (str "Gr: " group " S: " sound))
      (js/alert "Sound nicht gefunden"))))

