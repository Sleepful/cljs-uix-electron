(ns app.routes
  (:require
   [clojure.string :refer [blank?]]
   [reitit.coercion.spec :as rss]
   [reitit.core :as r]
   [reitit.frontend :as rf]
   [reitit.frontend.easy :as rfe]
   [uix.core :as uix :refer [$ defhook defui]]))

(def router-context (uix/create-context))

(defui route-provider
  "Creates router instance for given routes
  and shares it via React context"
  [{:keys [routes children]}]
  (let [router (uix/use-memo
                #(rf/router routes {:data {:coercion rss/coercion}})
                [routes])
        [route set-route] (uix/use-state
                           #(r/match-by-path
                             router
                             ; NOTE: location.hash is key for routes to work within Electron
                             ; using "path" returns a filepath in the filesystem, which is not useful
                             (or (not-empty js/location.hash)  "/")))]
    (uix/use-effect
     #(do
        (rfe/start! router set-route
                    ; NOTE: use-fragment is key for routes to work wihin Electron,
                    ; as electron routing only works with the anchor/hash links
                    {:use-fragment true}))

     [router])
    ($ router-context {:value (:data route)}
       children)))

(defhook use-route
  "Returns current route's data"
  []
  (uix/use-context router-context))

