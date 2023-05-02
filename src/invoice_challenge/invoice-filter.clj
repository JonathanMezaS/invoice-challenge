(def invoice (clojure.edn/read-string (slurp "invoice.edn")))
(defn item-filter [invoice]
  (
    ->> invoice
        :invoice/items
        (
          filter
          (
            fn [item]
            (
              let
              [
               iva19? (some #(and (= (:tax/category %) :iva)
                                  (= (:tax/rate %) 19))
                            (:taxable/taxes item))

               ret1? (some #(and (= (:retention/category %) :ret_fuente)
                                 (= (:retention/rate %) 1))
                           (:retentionable/retentions item))
               ]
              (println [iva19? ret1?] (count (filter true? [iva19? ret1?])))
              (= 1 (count (filter true? [iva19? ret1?])))
              )

            )
          )
        )
  )
(item-filter invoice)