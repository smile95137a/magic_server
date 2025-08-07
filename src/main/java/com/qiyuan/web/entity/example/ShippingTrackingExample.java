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

        public Criteria andIdEqualTo(String value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("id like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("id not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
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

        public Criteria andLogisticsTypeIsNull() {
            addCriterion("logistics_type is null");
            return (Criteria) this;
        }

        public Criteria andLogisticsTypeIsNotNull() {
            addCriterion("logistics_type is not null");
            return (Criteria) this;
        }

        public Criteria andLogisticsTypeEqualTo(String value) {
            addCriterion("logistics_type =", value, "logisticsType");
            return (Criteria) this;
        }

        public Criteria andLogisticsTypeNotEqualTo(String value) {
            addCriterion("logistics_type <>", value, "logisticsType");
            return (Criteria) this;
        }

        public Criteria andLogisticsTypeGreaterThan(String value) {
            addCriterion("logistics_type >", value, "logisticsType");
            return (Criteria) this;
        }

        public Criteria andLogisticsTypeGreaterThanOrEqualTo(String value) {
            addCriterion("logistics_type >=", value, "logisticsType");
            return (Criteria) this;
        }

        public Criteria andLogisticsTypeLessThan(String value) {
            addCriterion("logistics_type <", value, "logisticsType");
            return (Criteria) this;
        }

        public Criteria andLogisticsTypeLessThanOrEqualTo(String value) {
            addCriterion("logistics_type <=", value, "logisticsType");
            return (Criteria) this;
        }

        public Criteria andLogisticsTypeLike(String value) {
            addCriterion("logistics_type like", value, "logisticsType");
            return (Criteria) this;
        }

        public Criteria andLogisticsTypeNotLike(String value) {
            addCriterion("logistics_type not like", value, "logisticsType");
            return (Criteria) this;
        }

        public Criteria andLogisticsTypeIn(List<String> values) {
            addCriterion("logistics_type in", values, "logisticsType");
            return (Criteria) this;
        }

        public Criteria andLogisticsTypeNotIn(List<String> values) {
            addCriterion("logistics_type not in", values, "logisticsType");
            return (Criteria) this;
        }

        public Criteria andLogisticsTypeBetween(String value1, String value2) {
            addCriterion("logistics_type between", value1, value2, "logisticsType");
            return (Criteria) this;
        }

        public Criteria andLogisticsTypeNotBetween(String value1, String value2) {
            addCriterion("logistics_type not between", value1, value2, "logisticsType");
            return (Criteria) this;
        }

        public Criteria andLogisticsVendorIsNull() {
            addCriterion("logistics_vendor is null");
            return (Criteria) this;
        }

        public Criteria andLogisticsVendorIsNotNull() {
            addCriterion("logistics_vendor is not null");
            return (Criteria) this;
        }

        public Criteria andLogisticsVendorEqualTo(String value) {
            addCriterion("logistics_vendor =", value, "logisticsVendor");
            return (Criteria) this;
        }

        public Criteria andLogisticsVendorNotEqualTo(String value) {
            addCriterion("logistics_vendor <>", value, "logisticsVendor");
            return (Criteria) this;
        }

        public Criteria andLogisticsVendorGreaterThan(String value) {
            addCriterion("logistics_vendor >", value, "logisticsVendor");
            return (Criteria) this;
        }

        public Criteria andLogisticsVendorGreaterThanOrEqualTo(String value) {
            addCriterion("logistics_vendor >=", value, "logisticsVendor");
            return (Criteria) this;
        }

        public Criteria andLogisticsVendorLessThan(String value) {
            addCriterion("logistics_vendor <", value, "logisticsVendor");
            return (Criteria) this;
        }

        public Criteria andLogisticsVendorLessThanOrEqualTo(String value) {
            addCriterion("logistics_vendor <=", value, "logisticsVendor");
            return (Criteria) this;
        }

        public Criteria andLogisticsVendorLike(String value) {
            addCriterion("logistics_vendor like", value, "logisticsVendor");
            return (Criteria) this;
        }

        public Criteria andLogisticsVendorNotLike(String value) {
            addCriterion("logistics_vendor not like", value, "logisticsVendor");
            return (Criteria) this;
        }

        public Criteria andLogisticsVendorIn(List<String> values) {
            addCriterion("logistics_vendor in", values, "logisticsVendor");
            return (Criteria) this;
        }

        public Criteria andLogisticsVendorNotIn(List<String> values) {
            addCriterion("logistics_vendor not in", values, "logisticsVendor");
            return (Criteria) this;
        }

        public Criteria andLogisticsVendorBetween(String value1, String value2) {
            addCriterion("logistics_vendor between", value1, value2, "logisticsVendor");
            return (Criteria) this;
        }

        public Criteria andLogisticsVendorNotBetween(String value1, String value2) {
            addCriterion("logistics_vendor not between", value1, value2, "logisticsVendor");
            return (Criteria) this;
        }

        public Criteria andWaybillNoIsNull() {
            addCriterion("waybill_no is null");
            return (Criteria) this;
        }

        public Criteria andWaybillNoIsNotNull() {
            addCriterion("waybill_no is not null");
            return (Criteria) this;
        }

        public Criteria andWaybillNoEqualTo(String value) {
            addCriterion("waybill_no =", value, "waybillNo");
            return (Criteria) this;
        }

        public Criteria andWaybillNoNotEqualTo(String value) {
            addCriterion("waybill_no <>", value, "waybillNo");
            return (Criteria) this;
        }

        public Criteria andWaybillNoGreaterThan(String value) {
            addCriterion("waybill_no >", value, "waybillNo");
            return (Criteria) this;
        }

        public Criteria andWaybillNoGreaterThanOrEqualTo(String value) {
            addCriterion("waybill_no >=", value, "waybillNo");
            return (Criteria) this;
        }

        public Criteria andWaybillNoLessThan(String value) {
            addCriterion("waybill_no <", value, "waybillNo");
            return (Criteria) this;
        }

        public Criteria andWaybillNoLessThanOrEqualTo(String value) {
            addCriterion("waybill_no <=", value, "waybillNo");
            return (Criteria) this;
        }

        public Criteria andWaybillNoLike(String value) {
            addCriterion("waybill_no like", value, "waybillNo");
            return (Criteria) this;
        }

        public Criteria andWaybillNoNotLike(String value) {
            addCriterion("waybill_no not like", value, "waybillNo");
            return (Criteria) this;
        }

        public Criteria andWaybillNoIn(List<String> values) {
            addCriterion("waybill_no in", values, "waybillNo");
            return (Criteria) this;
        }

        public Criteria andWaybillNoNotIn(List<String> values) {
            addCriterion("waybill_no not in", values, "waybillNo");
            return (Criteria) this;
        }

        public Criteria andWaybillNoBetween(String value1, String value2) {
            addCriterion("waybill_no between", value1, value2, "waybillNo");
            return (Criteria) this;
        }

        public Criteria andWaybillNoNotBetween(String value1, String value2) {
            addCriterion("waybill_no not between", value1, value2, "waybillNo");
            return (Criteria) this;
        }

        public Criteria andVendororderIsNull() {
            addCriterion("vendororder is null");
            return (Criteria) this;
        }

        public Criteria andVendororderIsNotNull() {
            addCriterion("vendororder is not null");
            return (Criteria) this;
        }

        public Criteria andVendororderEqualTo(String value) {
            addCriterion("vendororder =", value, "vendororder");
            return (Criteria) this;
        }

        public Criteria andVendororderNotEqualTo(String value) {
            addCriterion("vendororder <>", value, "vendororder");
            return (Criteria) this;
        }

        public Criteria andVendororderGreaterThan(String value) {
            addCriterion("vendororder >", value, "vendororder");
            return (Criteria) this;
        }

        public Criteria andVendororderGreaterThanOrEqualTo(String value) {
            addCriterion("vendororder >=", value, "vendororder");
            return (Criteria) this;
        }

        public Criteria andVendororderLessThan(String value) {
            addCriterion("vendororder <", value, "vendororder");
            return (Criteria) this;
        }

        public Criteria andVendororderLessThanOrEqualTo(String value) {
            addCriterion("vendororder <=", value, "vendororder");
            return (Criteria) this;
        }

        public Criteria andVendororderLike(String value) {
            addCriterion("vendororder like", value, "vendororder");
            return (Criteria) this;
        }

        public Criteria andVendororderNotLike(String value) {
            addCriterion("vendororder not like", value, "vendororder");
            return (Criteria) this;
        }

        public Criteria andVendororderIn(List<String> values) {
            addCriterion("vendororder in", values, "vendororder");
            return (Criteria) this;
        }

        public Criteria andVendororderNotIn(List<String> values) {
            addCriterion("vendororder not in", values, "vendororder");
            return (Criteria) this;
        }

        public Criteria andVendororderBetween(String value1, String value2) {
            addCriterion("vendororder between", value1, value2, "vendororder");
            return (Criteria) this;
        }

        public Criteria andVendororderNotBetween(String value1, String value2) {
            addCriterion("vendororder not between", value1, value2, "vendororder");
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

        public Criteria andRequestPayloadIsNull() {
            addCriterion("request_payload is null");
            return (Criteria) this;
        }

        public Criteria andRequestPayloadIsNotNull() {
            addCriterion("request_payload is not null");
            return (Criteria) this;
        }

        public Criteria andRequestPayloadEqualTo(String value) {
            addCriterion("request_payload =", value, "requestPayload");
            return (Criteria) this;
        }

        public Criteria andRequestPayloadNotEqualTo(String value) {
            addCriterion("request_payload <>", value, "requestPayload");
            return (Criteria) this;
        }

        public Criteria andRequestPayloadGreaterThan(String value) {
            addCriterion("request_payload >", value, "requestPayload");
            return (Criteria) this;
        }

        public Criteria andRequestPayloadGreaterThanOrEqualTo(String value) {
            addCriterion("request_payload >=", value, "requestPayload");
            return (Criteria) this;
        }

        public Criteria andRequestPayloadLessThan(String value) {
            addCriterion("request_payload <", value, "requestPayload");
            return (Criteria) this;
        }

        public Criteria andRequestPayloadLessThanOrEqualTo(String value) {
            addCriterion("request_payload <=", value, "requestPayload");
            return (Criteria) this;
        }

        public Criteria andRequestPayloadLike(String value) {
            addCriterion("request_payload like", value, "requestPayload");
            return (Criteria) this;
        }

        public Criteria andRequestPayloadNotLike(String value) {
            addCriterion("request_payload not like", value, "requestPayload");
            return (Criteria) this;
        }

        public Criteria andRequestPayloadIn(List<String> values) {
            addCriterion("request_payload in", values, "requestPayload");
            return (Criteria) this;
        }

        public Criteria andRequestPayloadNotIn(List<String> values) {
            addCriterion("request_payload not in", values, "requestPayload");
            return (Criteria) this;
        }

        public Criteria andRequestPayloadBetween(String value1, String value2) {
            addCriterion("request_payload between", value1, value2, "requestPayload");
            return (Criteria) this;
        }

        public Criteria andRequestPayloadNotBetween(String value1, String value2) {
            addCriterion("request_payload not between", value1, value2, "requestPayload");
            return (Criteria) this;
        }

        public Criteria andResponsePayloadIsNull() {
            addCriterion("response_payload is null");
            return (Criteria) this;
        }

        public Criteria andResponsePayloadIsNotNull() {
            addCriterion("response_payload is not null");
            return (Criteria) this;
        }

        public Criteria andResponsePayloadEqualTo(String value) {
            addCriterion("response_payload =", value, "responsePayload");
            return (Criteria) this;
        }

        public Criteria andResponsePayloadNotEqualTo(String value) {
            addCriterion("response_payload <>", value, "responsePayload");
            return (Criteria) this;
        }

        public Criteria andResponsePayloadGreaterThan(String value) {
            addCriterion("response_payload >", value, "responsePayload");
            return (Criteria) this;
        }

        public Criteria andResponsePayloadGreaterThanOrEqualTo(String value) {
            addCriterion("response_payload >=", value, "responsePayload");
            return (Criteria) this;
        }

        public Criteria andResponsePayloadLessThan(String value) {
            addCriterion("response_payload <", value, "responsePayload");
            return (Criteria) this;
        }

        public Criteria andResponsePayloadLessThanOrEqualTo(String value) {
            addCriterion("response_payload <=", value, "responsePayload");
            return (Criteria) this;
        }

        public Criteria andResponsePayloadLike(String value) {
            addCriterion("response_payload like", value, "responsePayload");
            return (Criteria) this;
        }

        public Criteria andResponsePayloadNotLike(String value) {
            addCriterion("response_payload not like", value, "responsePayload");
            return (Criteria) this;
        }

        public Criteria andResponsePayloadIn(List<String> values) {
            addCriterion("response_payload in", values, "responsePayload");
            return (Criteria) this;
        }

        public Criteria andResponsePayloadNotIn(List<String> values) {
            addCriterion("response_payload not in", values, "responsePayload");
            return (Criteria) this;
        }

        public Criteria andResponsePayloadBetween(String value1, String value2) {
            addCriterion("response_payload between", value1, value2, "responsePayload");
            return (Criteria) this;
        }

        public Criteria andResponsePayloadNotBetween(String value1, String value2) {
            addCriterion("response_payload not between", value1, value2, "responsePayload");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
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