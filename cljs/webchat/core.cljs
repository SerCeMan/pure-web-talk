(ns webchat.core
  (:require-macros
    [reagent.ratom :refer [reaction]])
  (:require
    [reagent.core :as r]
    [re-frame.utils :refer [dissoc-in]]
    [re-com.core :refer [label v-box h-box button]]
    [re-frame.core :as rf :refer [reg-sub reg-event-db subscribe dispatch]]))

(enable-console-print!)

(reg-sub
  :by-path
  (fn [db [_ path]]
    (get-in db path)))

(reg-event-db
  :initialize
  (fn [db _]
    (if (= db {})
      {:todos {1 {:message "talk about fp"
                  :done    false}
               2 {:message "talk about clojure"
                  :done    false}
               3 {:message "exterminate imperative programming"
                  :done    false}}}
      db)))

(reg-event-db
  :delete-todo
  (fn [db [_ path]]
    (dissoc-in db path)))

(reg-event-db
  :swap-todo
  (fn [db [_ path]]
    (update-in db (conj path :done) not)))


(defn todo-element [{:keys [message done]} path]
  [:div
   {:style (if done {:text-decoration :line-through} {})}
   message])


(defn todo-row [todos path k]
  (let [todo (reaction (get @todos k))
        todo-path (conj path k)]
    (fn []
      [h-box
       :children [[button
                   :label (if (:done @todo) "Undone" "Done")
                   :on-click #(dispatch [:swap-todo todo-path])]
                  [todo-element @todo todo-path]
                  [button
                   :disabled? (not (:done @todo))
                   :label "Delete"
                   :on-click #(dispatch [:delete-todo todo-path])]]])))

(defn todo-list [todos path]
  (let [keys (reaction (keys @todos))]
    (fn []
      [v-box
       :children (for [k @keys]
                   ^{:key k} [todo-row todos path k])])))

(defn ui []
  (let [path [:todos]
        todos (subscribe [:by-path path])]
    [:div
     [label
      :label "TODO list"]
     [todo-list todos path]]))

;; -- Entry Point -------------------------------------------------------------

(defn ^:export run
  []
  (rf/dispatch [:initialize])
  (r/render [ui]
            (.getElementById js/document "app")))