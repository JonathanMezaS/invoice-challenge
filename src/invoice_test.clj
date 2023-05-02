(ns invoice-test
  (:require [clojure.test :refer [deftest is]]  [invoice-item :as in-item]))

(deftest happy-path-test
  (def item
    {:invoice-item/precise-quantity 10.0
     :invoice-item/precise-price 25.0
     :invoice-item/discount-rate 5})
  (is (= 237.5 (in-item/subtotal item))))

(deftest test-with-default-value
  (def item
    {:invoice-item/precise-quantity 10.0
     :invoice-item/precise-price 25.0})
  (is (= 250.0 (in-item/subtotal item))))

(deftest error-nil-value-test
  (def item
    {
     :invoice-item/precise-price 25.0
     :invoice-item/discount-rate 5})
  (is (Exception (in-item/subtotal item))))

(deftest empty-input
  (def item
    {})
  (is (Exception (in-item/subtotal item))))

(deftest input-data-type-error
  (def item
    {:invoice-item/precise-quantity 10.0
     :invoice-item/precise-price "25.0"
     :invoice-item/discount-rate 5})
  (is (= 237.5 (in-item/subtotal item))))


