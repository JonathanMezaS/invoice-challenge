(ns invoice-challenge.core
  (:require [clojure.data.json :as json]
            [invoice_spec :as spec]
            [clojure.spec.alpha :as s])

  (:import (java.util Date)))

(defn -main
  "Transform and validate a Invoice form a Json"
  [& args]

  (defn load-json-file [filename]
    (let [json-str (slurp filename)]
      (json/read-str json-str)))

  (def invoice (load-json-file "invoice.json"))

  (defn format-invoice [json-map]
    {  :invoice/issue-date (Date. (get-in json-map ["invoice" "issue_date"]))
               :invoice/customer {:customer/name (get-in json-map ["invoice" "customer" "company_name"])
                                  :customer/email (get-in json-map ["invoice" "customer" "email"])}
               :invoice/items (into [] (map (fn [item]
                             {:invoice-item/price (get item "price")
                              :invoice-item/quantity (get item "quantity")
                              :invoice-item/sku (get item "sku")
                              :invoice-item/taxes (into [] (map (fn [tax]
                                                          {:tax/category (keyword "iva")
                                                           :tax/rate (double (get tax "tax_rate"))}) (get item "taxes")))})
                           (get-in json-map ["invoice" "items"])))})
  (def invoice-formatted (format-invoice invoice))
  (println invoice-formatted)
  (println (s/valid? ::spec/invoice invoice-formatted))
)
