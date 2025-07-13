package com.qiyuan.web.entity.example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShippingTrackingExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ShippingTrackingExample() {
        oredCriteria = new ArrayList<>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andOrderIdIsNull() {
            addCriterion("order_id is null");
            return (Criteria) this;
        }

        public Criteria andOrderIdIsNotNull() {
            addCriterion("order_id is not null");
            return (Criteria) this;
        }

        public Criteria andOrderIdEqualTo(String value) {
            addCriterion("order_id =", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotEqualTo(String value) {
            addCriterion("order_id <>", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThan(String value) {
            addCriterion("order_id >", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThanOrEqualTo(String value) {
            addCriterion("order_id >=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThan(String value) {
            addCriterion("order_id <", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThanOrEqualTo(String value) {
            addCriterion("order_id <=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLike(String value) {
            addCriterion("order_id like", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotLike(String value) {
            addCriterion("order_id not like", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdIn(List<String> values) {
            addCriterion("order_id in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotIn(List<String> values) {
            addCriterion("order_id not in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdBetween(String value1, String value2) {
            addCriterion("order_id between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotBetween(String value1, String value2) {
            addCriterion("order_id not between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andExternalOrderNoIsNull() {
            addCriterion("external_order_no is null");
            return (Criteria) this;
        }

        public Criteria andExternalOrderNoIsNotNull() {
            addCriterion("external_order_no is not null");
            return (Criteria) this;
        }

        public Criteria andExternalOrderNoEqualTo(String value) {
            addCriterion("external_order_no =", value, "externalOrderNo");
            return (Criteria) this;
        }

        public Criteria andExternalOrderNoNotEqualTo(String value) {
            addCriterion("external_order_no <>", value, "externalOrderNo");
            return (Criteria) this;
        }

        public Criteria andExternalOrderNoGreaterThan(String value) {
            addCriterion("external_order_no >", value, "externalOrderNo");
            return (Criteria) this;
        }

        public Criteria andExternalOrderNoGreaterThanOrEqualTo(String value) {
            addCriterion("external_order_no >=", value, "externalOrderNo");
            return (Criteria) this;
        }

        public Criteria andExternalOrderNoLessThan(String value) {
            addCriterion("external_order_no <", value, "externalOrderNo");
            return (Criteria) this;
        }

        public Criteria andExternalOrderNoLessThanOrEqualTo(String value) {
            addCriterion("external_order_no <=", value, "externalOrderNo");
            return (Criteria) this;
        }

        public Criteria andExternalOrderNoLike(String value) {
            addCriterion("external_order_no like", value, "externalOrderNo");
            return (Criteria) this;
        }

        public Criteria andExternalOrderNoNotLike(String value) {
            addCriterion("external_order_no not like", value, "externalOrderNo");
            return (Criteria) this;
        }

        public Criteria andExternalOrderNoIn(List<String> values) {
            addCriterion("external_order_no in", values, "externalOrderNo");
            return (Criteria) this;
        }

        public Criteria andExternalOrderNoNotIn(List<String> values) {
            addCriterion("external_order_no not in", values, "externalOrderNo");
            return (Criteria) this;
        }

        public Criteria andExternalOrderNoBetween(String value1, String value2) {
            addCriterion("external_order_no between", value1, value2, "externalOrderNo");
            return (Criteria) this;
        }

        public Criteria andExternalOrderNoNotBetween(String value1, String value2) {
            addCriterion("external_order_no not between", value1, value2, "externalOrderNo");
            return (Criteria) this;
        }

        public Criteria andShippingOrderNoIsNull() {
            addCriterion("shipping_order_no is null");
            return (Criteria) this;
        }

        public Criteria andShippingOrderNoIsNotNull() {
            addCriterion("shipping_order_no is not null");
            return (Criteria) this;
        }

        public Criteria andShippingOrderNoEqualTo(String value) {
            addCriterion("shipping_order_no =", value, "shippingOrderNo");
            return (Criteria) this;
        }

        public Criteria andShippingOrderNoNotEqualTo(String value) {
            addCriterion("shipping_order_no <>", value, "shippingOrderNo");
            return (Criteria) this;
        }

        public Criteria andShippingOrderNoGreaterThan(String value) {
            addCriterion("shipping_order_no >", value, "shippingOrderNo");
            return (Criteria) this;
        }

        public Criteria andShippingOrderNoGreaterThanOrEqualTo(String value) {
            addCriterion("shipping_order_no >=", value, "shippingOrderNo");
            return (Criteria) this;
        }

        public Criteria andShippingOrderNoLessThan(String value) {
            addCriterion("shipping_order_no <", value, "shippingOrderNo");
            return (Criteria) this;
        }

        public Criteria andShippingOrderNoLessThanOrEqualTo(String value) {
            addCriterion("shipping_order_no <=", value, "shippingOrderNo");
            return (Criteria) this;
        }

        public Criteria andShippingOrderNoLike(String value) {
            addCriterion("shipping_order_no like", value, "shippingOrderNo");
            return (Criteria) this;
        }

        public Criteria andShippingOrderNoNotLike(String value) {
            addCriterion("shipping_order_no not like", value, "shippingOrderNo");
            return (Criteria) this;
        }

        public Criteria andShippingOrderNoIn(List<String> values) {
            addCriterion("shipping_order_no in", values, "shippingOrderNo");
            return (Criteria) this;
        }

        public Criteria andShippingOrderNoNotIn(List<String> values) {
            addCriterion("shipping_order_no not in", values, "shippingOrderNo");
            return (Criteria) this;
        }

        public Criteria andShippingOrderNoBetween(String value1, String value2) {
            addCriterion("shipping_order_no between", value1, value2, "shippingOrderNo");
            return (Criteria) this;
        }

        public Criteria andShippingOrderNoNotBetween(String value1, String value2) {
            addCriterion("shipping_order_no not between", value1, value2, "shippingOrderNo");
            return (Criteria) this;
        }

        public Criteria andShippingProviderIsNull() {
            addCriterion("shipping_provider is null");
            return (Criteria) this;
        }

        public Criteria andShippingProviderIsNotNull() {
            addCriterion("shipping_provider is not null");
            return (Criteria) this;
        }

        public Criteria andShippingProviderEqualTo(String value) {
            addCriterion("shipping_provider =", value, "shippingProvider");
            return (Criteria) this;
        }

        public Criteria andShippingProviderNotEqualTo(String value) {
            addCriterion("shipping_provider <>", value, "shippingProvider");
            return (Criteria) this;
        }

        public Criteria andShippingProviderGreaterThan(String value) {
            addCriterion("shipping_provider >", value, "shippingProvider");
            return (Criteria) this;
        }

        public Criteria andShippingProviderGreaterThanOrEqualTo(String value) {
            addCriterion("shipping_provider >=", value, "shippingProvider");
            return (Criteria) this;
        }

        public Criteria andShippingProviderLessThan(String value) {
            addCriterion("shipping_provider <", value, "shippingProvider");
            return (Criteria) this;
        }

        public Criteria andShippingProviderLessThanOrEqualTo(String value) {
            addCriterion("shipping_provider <=", value, "shippingProvider");
            return (Criteria) this;
        }

        public Criteria andShippingProviderLike(String value) {
            addCriterion("shipping_provider like", value, "shippingProvider");
            return (Criteria) this;
        }

        public Criteria andShippingProviderNotLike(String value) {
            addCriterion("shipping_provider not like", value, "shippingProvider");
            return (Criteria) this;
        }

        public Criteria andShippingProviderIn(List<String> values) {
            addCriterion("shipping_provider in", values, "shippingProvider");
            return (Criteria) this;
        }

        public Criteria andShippingProviderNotIn(List<String> values) {
            addCriterion("shipping_provider not in", values, "shippingProvider");
            return (Criteria) this;
        }

        public Criteria andShippingProviderBetween(String value1, String value2) {
            addCriterion("shipping_provider between", value1, value2, "shippingProvider");
            return (Criteria) this;
        }

        public Criteria andShippingProviderNotBetween(String value1, String value2) {
            addCriterion("shipping_provider not between", value1, value2, "shippingProvider");
            return (Criteria) this;
        }

        public Criteria andModeIsNull() {
            addCriterion("mode is null");
            return (Criteria) this;
        }

        public Criteria andModeIsNotNull() {
            addCriterion("mode is not null");
            return (Criteria) this;
        }

        public Criteria andModeEqualTo(String value) {
            addCriterion("mode =", value, "mode");
            return (Criteria) this;
        }

        public Criteria andModeNotEqualTo(String value) {
            addCriterion("mode <>", value, "mode");
            return (Criteria) this;
        }

        public Criteria andModeGreaterThan(String value) {
            addCriterion("mode >", value, "mode");
            return (Criteria) this;
        }

        public Criteria andModeGreaterThanOrEqualTo(String value) {
            addCriterion("mode >=", value, "mode");
            return (Criteria) this;
        }

        public Criteria andModeLessThan(String value) {
            addCriterion("mode <", value, "mode");
            return (Criteria) this;
        }

        public Criteria andModeLessThanOrEqualTo(String value) {
            addCriterion("mode <=", value, "mode");
            return (Criteria) this;
        }

        public Criteria andModeLike(String value) {
            addCriterion("mode like", value, "mode");
            return (Criteria) this;
        }

        public Criteria andModeNotLike(String value) {
            addCriterion("mode not like", value, "mode");
            return (Criteria) this;
        }

        public Criteria andModeIn(List<String> values) {
            addCriterion("mode in", values, "mode");
            return (Criteria) this;
        }

        public Criteria andModeNotIn(List<String> values) {
            addCriterion("mode not in", values, "mode");
            return (Criteria) this;
        }

        public Criteria andModeBetween(String value1, String value2) {
            addCriterion("mode between", value1, value2, "mode");
            return (Criteria) this;
        }

        public Criteria andModeNotBetween(String value1, String value2) {
            addCriterion("mode not between", value1, value2, "mode");
            return (Criteria) this;
        }

        public Criteria andStoreIdIsNull() {
            addCriterion("store_id is null");
            return (Criteria) this;
        }

        public Criteria andStoreIdIsNotNull() {
            addCriterion("store_id is not null");
            return (Criteria) this;
        }

        public Criteria andStoreIdEqualTo(String value) {
            addCriterion("store_id =", value, "storeId");
            return (Criteria) this;
        }

        public Criteria andStoreIdNotEqualTo(String value) {
            addCriterion("store_id <>", value, "storeId");
            return (Criteria) this;
        }

        public Criteria andStoreIdGreaterThan(String value) {
            addCriterion("store_id >", value, "storeId");
            return (Criteria) this;
        }

        public Criteria andStoreIdGreaterThanOrEqualTo(String value) {
            addCriterion("store_id >=", value, "storeId");
            return (Criteria) this;
        }

        public Criteria andStoreIdLessThan(String value) {
            addCriterion("store_id <", value, "storeId");
            return (Criteria) this;
        }

        public Criteria andStoreIdLessThanOrEqualTo(String value) {
            addCriterion("store_id <=", value, "storeId");
            return (Criteria) this;
        }

        public Criteria andStoreIdLike(String value) {
            addCriterion("store_id like", value, "storeId");
            return (Criteria) this;
        }

        public Criteria andStoreIdNotLike(String value) {
            addCriterion("store_id not like", value, "storeId");
            return (Criteria) this;
        }

        public Criteria andStoreIdIn(List<String> values) {
            addCriterion("store_id in", values, "storeId");
            return (Criteria) this;
        }

        public Criteria andStoreIdNotIn(List<String> values) {
            addCriterion("store_id not in", values, "storeId");
            return (Criteria) this;
        }

        public Criteria andStoreIdBetween(String value1, String value2) {
            addCriterion("store_id between", value1, value2, "storeId");
            return (Criteria) this;
        }

        public Criteria andStoreIdNotBetween(String value1, String value2) {
            addCriterion("store_id not between", value1, value2, "storeId");
            return (Criteria) this;
        }

        public Criteria andOpmodeIsNull() {
            addCriterion("opmode is null");
            return (Criteria) this;
        }

        public Criteria andOpmodeIsNotNull() {
            addCriterion("opmode is not null");
            return (Criteria) this;
        }

        public Criteria andOpmodeEqualTo(String value) {
            addCriterion("opmode =", value, "opmode");
            return (Criteria) this;
        }

        public Criteria andOpmodeNotEqualTo(String value) {
            addCriterion("opmode <>", value, "opmode");
            return (Criteria) this;
        }

        public Criteria andOpmodeGreaterThan(String value) {
            addCriterion("opmode >", value, "opmode");
            return (Criteria) this;
        }

        public Criteria andOpmodeGreaterThanOrEqualTo(String value) {
            addCriterion("opmode >=", value, "opmode");
            return (Criteria) this;
        }

        public Criteria andOpmodeLessThan(String value) {
            addCriterion("opmode <", value, "opmode");
            return (Criteria) this;
        }

        public Criteria andOpmodeLessThanOrEqualTo(String value) {
            addCriterion("opmode <=", value, "opmode");
            return (Criteria) this;
        }

        public Criteria andOpmodeLike(String value) {
            addCriterion("opmode like", value, "opmode");
            return (Criteria) this;
        }

        public Criteria andOpmodeNotLike(String value) {
            addCriterion("opmode not like", value, "opmode");
            return (Criteria) this;
        }

        public Criteria andOpmodeIn(List<String> values) {
            addCriterion("opmode in", values, "opmode");
            return (Criteria) this;
        }

        public Criteria andOpmodeNotIn(List<String> values) {
            addCriterion("opmode not in", values, "opmode");
            return (Criteria) this;
        }

        public Criteria andOpmodeBetween(String value1, String value2) {
            addCriterion("opmode between", value1, value2, "opmode");
            return (Criteria) this;
        }

        public Criteria andOpmodeNotBetween(String value1, String value2) {
            addCriterion("opmode not between", value1, value2, "opmode");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(String value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(String value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(String value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(String value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(String value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(String value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLike(String value) {
            addCriterion("status like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotLike(String value) {
            addCriterion("status not like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<String> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<String> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(String value1, String value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(String value1, String value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andShipDateIsNull() {
            addCriterion("ship_date is null");
            return (Criteria) this;
        }

        public Criteria andShipDateIsNotNull() {
            addCriterion("ship_date is not null");
            return (Criteria) this;
        }

        public Criteria andShipDateEqualTo(Date value) {
            addCriterion("ship_date =", value, "shipDate");
            return (Criteria) this;
        }

        public Criteria andShipDateNotEqualTo(Date value) {
            addCriterion("ship_date <>", value, "shipDate");
            return (Criteria) this;
        }

        public Criteria andShipDateGreaterThan(Date value) {
            addCriterion("ship_date >", value, "shipDate");
            return (Criteria) this;
        }

        public Criteria andShipDateGreaterThanOrEqualTo(Date value) {
            addCriterion("ship_date >=", value, "shipDate");
            return (Criteria) this;
        }

        public Criteria andShipDateLessThan(Date value) {
            addCriterion("ship_date <", value, "shipDate");
            return (Criteria) this;
        }

        public Criteria andShipDateLessThanOrEqualTo(Date value) {
            addCriterion("ship_date <=", value, "shipDate");
            return (Criteria) this;
        }

        public Criteria andShipDateIn(List<Date> values) {
            addCriterion("ship_date in", values, "shipDate");
            return (Criteria) this;
        }

        public Criteria andShipDateNotIn(List<Date> values) {
            addCriterion("ship_date not in", values, "shipDate");
            return (Criteria) this;
        }

        public Criteria andShipDateBetween(Date value1, Date value2) {
            addCriterion("ship_date between", value1, value2, "shipDate");
            return (Criteria) this;
        }

        public Criteria andShipDateNotBetween(Date value1, Date value2) {
            addCriterion("ship_date not between", value1, value2, "shipDate");
            return (Criteria) this;
        }

        public Criteria andDeliveredDateIsNull() {
            addCriterion("delivered_date is null");
            return (Criteria) this;
        }

        public Criteria andDeliveredDateIsNotNull() {
            addCriterion("delivered_date is not null");
            return (Criteria) this;
        }

        public Criteria andDeliveredDateEqualTo(Date value) {
            addCriterion("delivered_date =", value, "deliveredDate");
            return (Criteria) this;
        }

        public Criteria andDeliveredDateNotEqualTo(Date value) {
            addCriterion("delivered_date <>", value, "deliveredDate");
            return (Criteria) this;
        }

        public Criteria andDeliveredDateGreaterThan(Date value) {
            addCriterion("delivered_date >", value, "deliveredDate");
            return (Criteria) this;
        }

        public Criteria andDeliveredDateGreaterThanOrEqualTo(Date value) {
            addCriterion("delivered_date >=", value, "deliveredDate");
            return (Criteria) this;
        }

        public Criteria andDeliveredDateLessThan(Date value) {
            addCriterion("delivered_date <", value, "deliveredDate");
            return (Criteria) this;
        }

        public Criteria andDeliveredDateLessThanOrEqualTo(Date value) {
            addCriterion("delivered_date <=", value, "deliveredDate");
            return (Criteria) this;
        }

        public Criteria andDeliveredDateIn(List<Date> values) {
            addCriterion("delivered_date in", values, "deliveredDate");
            return (Criteria) this;
        }

        public Criteria andDeliveredDateNotIn(List<Date> values) {
            addCriterion("delivered_date not in", values, "deliveredDate");
            return (Criteria) this;
        }

        public Criteria andDeliveredDateBetween(Date value1, Date value2) {
            addCriterion("delivered_date between", value1, value2, "deliveredDate");
            return (Criteria) this;
        }

        public Criteria andDeliveredDateNotBetween(Date value1, Date value2) {
            addCriterion("delivered_date not between", value1, value2, "deliveredDate");
            return (Criteria) this;
        }

        public Criteria andRawDataIsNull() {
            addCriterion("raw_data is null");
            return (Criteria) this;
        }

        public Criteria andRawDataIsNotNull() {
            addCriterion("raw_data is not null");
            return (Criteria) this;
        }

        public Criteria andRawDataEqualTo(String value) {
            addCriterion("raw_data =", value, "rawData");
            return (Criteria) this;
        }

        public Criteria andRawDataNotEqualTo(String value) {
            addCriterion("raw_data <>", value, "rawData");
            return (Criteria) this;
        }

        public Criteria andRawDataGreaterThan(String value) {
            addCriterion("raw_data >", value, "rawData");
            return (Criteria) this;
        }

        public Criteria andRawDataGreaterThanOrEqualTo(String value) {
            addCriterion("raw_data >=", value, "rawData");
            return (Criteria) this;
        }

        public Criteria andRawDataLessThan(String value) {
            addCriterion("raw_data <", value, "rawData");
            return (Criteria) this;
        }

        public Criteria andRawDataLessThanOrEqualTo(String value) {
            addCriterion("raw_data <=", value, "rawData");
            return (Criteria) this;
        }

        public Criteria andRawDataLike(String value) {
            addCriterion("raw_data like", value, "rawData");
            return (Criteria) this;
        }

        public Criteria andRawDataNotLike(String value) {
            addCriterion("raw_data not like", value, "rawData");
            return (Criteria) this;
        }

        public Criteria andRawDataIn(List<String> values) {
            addCriterion("raw_data in", values, "rawData");
            return (Criteria) this;
        }

        public Criteria andRawDataNotIn(List<String> values) {
            addCriterion("raw_data not in", values, "rawData");
            return (Criteria) this;
        }

        public Criteria andRawDataBetween(String value1, String value2) {
            addCriterion("raw_data between", value1, value2, "rawData");
            return (Criteria) this;
        }

        public Criteria andRawDataNotBetween(String value1, String value2) {
            addCriterion("raw_data not between", value1, value2, "rawData");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}