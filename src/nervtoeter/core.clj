(ns nervtoeter.core
  (:use [jayq.core :only [$ append]]))

(def sounds
  (sorted-map
   "Anfang"
   [["Cowboy" "sound/Cowboy_Theme-Pavak-1711860633.mp3"]]
   "Gesammelt"
   [
    ["Applaus" "sound/5_Sec_Crowd_Cheer-Mike_Koenig-1562033255.mp3"]
    ["Horn" "sound/Air Horn-SoundBible.com-964603082.mp3"]
    ["Buzzer" "sound/Buzzer-SoundBible.com-188422102.mp3"]
    ["Kasse" "sound/Cha_Ching_Register-Muska666-173262285.mp3"]
    ["No" "sound/Hl2_Rebel-Ragdoll485-573931361.mp3"]
    ["Babylachen" "sound/Kid_Laugh-Mike_Koenig-1673908713.mp3"]
    ["Voegel" "sound/Laughing_kookaburra_birds-Christopher-718473240.mp3"]
    ["Lachen" "sound/Laughter-Mike_Koenig-360558723.mp3"]
    ["Ovationen" "sound/Ovation-Mike_Koenig-1061486511.mp3"]
    ["Hahn" "sound/Rudy_rooster_crowing-Shelley-1948282641.mp3"]
    ["Sad Trombone" "sound/Sad_Trombone-Joe_Lamb-665429450.mp3"]
    ["Slap" "sound/Slap-SoundMaster13-49669815.mp3"]
    ["Schleim" "sound/Slime-SoundBible.com-803762203.mp3"]
    ["Punch" "sound/Strong_Punch-Mike_Koenig-574430706.mp3"]
    ["Truthahn" "sound/Turkey Gobble-SoundBible.com-1935194723.mp3"]
    ["Flyby" "sound/flyby-Conor-1500306612.mp3"]
    ["Hahaha" "sound/hahaha-Peter_De_Lang-1639076107.mp3"]
    ["Klingen" "sound/rage_of_blades-Blaga_Saun-1763516257.mp3"]
    ["Donner" "sound/thunder_strike_1-Mike_Koenig-739781745.mp3"]
    ]))

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

