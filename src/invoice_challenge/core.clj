(ns invoice-challenge.core
  (:require [clojure.data.json :as json] [invoice_spec :as spec] [clojure.spec.alpha :as s]  [clojure.edn :as edn]) )

(defn -main
  "Input fot his prject"
  [& args]
  (def invoice (json/read-str (slurp "invoice.json")))
  (s/explain ::spec/invoice invoice)
  (println (s/valid? ::spec/invoice invoice))

  )
