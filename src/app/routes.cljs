(ns app.routes
  (:require [reitit.core :as r]
            [reitit.frontend :as rf]
            [reitit.frontend.easy :as rfe]
            [reitit.coercion.spec :as rss]
            [uix.core :as uix :refer [$ defui defhook]]))

(def router-context (uix/create-context))

(defui route-provider
  "Creates router instance for given routes
  and shares it via React context"
  [{:keys [routes children]}]
  (let [router (uix/use-memo #(rf/router routes {:data {:coercion rss/coercion}}) [routes])
        [route set-route] (uix/use-state #(r/match-by-path router js/location.pathname))]
    (uix/use-effect
     #(rfe/start! router set-route {:use-fragment false})
     [router])
    ($ router-context {:value (:data route)}
       children)))

(defhook use-route
  "Returns current route's data"
  []
  (uix/use-context router-context))

