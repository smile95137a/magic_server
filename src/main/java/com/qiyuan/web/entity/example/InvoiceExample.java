package com.qiyuan.web.entity.example;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InvoiceExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public InvoiceExample() {
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

        public Criteria andInvoiceNumberIsNull() {
            addCriterion("invoice_number is null");
            return (Criteria) this;
        }

        public Criteria andInvoiceNumberIsNotNull() {
            addCriterion("invoice_number is not null");
            return (Criteria) this;
        }

        public Criteria andInvoiceNumberEqualTo(String value) {
            addCriterion("invoice_number =", value, "invoiceNumber");
            return (Criteria) this;
        }

        public Criteria andInvoiceNumberNotEqualTo(String value) {
            addCriterion("invoice_number <>", value, "invoiceNumber");
            return (Criteria) this;
        }

        public Criteria andInvoiceNumberGreaterThan(String value) {
            addCriterion("invoice_number >", value, "invoiceNumber");
            return (Criteria) this;
        }

        public Criteria andInvoiceNumberGreaterThanOrEqualTo(String value) {
            addCriterion("invoice_number >=", value, "invoiceNumber");
            return (Criteria) this;
        }

        public Criteria andInvoiceNumberLessThan(String value) {
            addCriterion("invoice_number <", value, "invoiceNumber");
            return (Criteria) this;
        }

        public Criteria andInvoiceNumberLessThanOrEqualTo(String value) {
            addCriterion("invoice_number <=", value, "invoiceNumber");
            return (Criteria) this;
        }

        public Criteria andInvoiceNumberLike(String value) {
            addCriterion("invoice_number like", value, "invoiceNumber");
            return (Criteria) this;
        }

        public Criteria andInvoiceNumberNotLike(String value) {
            addCriterion("invoice_number not like", value, "invoiceNumber");
            return (Criteria) this;
        }

        public Criteria andInvoiceNumberIn(List<String> values) {
            addCriterion("invoice_number in", values, "invoiceNumber");
            return (Criteria) this;
        }

        public Criteria andInvoiceNumberNotIn(List<String> values) {
            addCriterion("invoice_number not in", values, "invoiceNumber");
            return (Criteria) this;
        }

        public Criteria andInvoiceNumberBetween(String value1, String value2) {
            addCriterion("invoice_number between", value1, value2, "invoiceNumber");
            return (Criteria) this;
        }

        public Criteria andInvoiceNumberNotBetween(String value1, String value2) {
            addCriterion("invoice_number not between", value1, value2, "invoiceNumber");
            return (Criteria) this;
        }

        public Criteria andInvoiceDateIsNull() {
            addCriterion("invoice_date is null");
            return (Criteria) this;
        }

        public Criteria andInvoiceDateIsNotNull() {
            addCriterion("invoice_date is not null");
            return (Criteria) this;
        }

        public Criteria andInvoiceDateEqualTo(Date value) {
            addCriterion("invoice_date =", value, "invoiceDate");
            return (Criteria) this;
        }

        public Criteria andInvoiceDateNotEqualTo(Date value) {
            addCriterion("invoice_date <>", value, "invoiceDate");
            return (Criteria) this;
        }

        public Criteria andInvoiceDateGreaterThan(Date value) {
            addCriterion("invoice_date >", value, "invoiceDate");
            return (Criteria) this;
        }

        public Criteria andInvoiceDateGreaterThanOrEqualTo(Date value) {
            addCriterion("invoice_date >=", value, "invoiceDate");
            return (Criteria) this;
        }

        public Criteria andInvoiceDateLessThan(Date value) {
            addCriterion("invoice_date <", value, "invoiceDate");
            return (Criteria) this;
        }

        public Criteria andInvoiceDateLessThanOrEqualTo(Date value) {
            addCriterion("invoice_date <=", value, "invoiceDate");
            return (Criteria) this;
        }

        public Criteria andInvoiceDateIn(List<Date> values) {
            addCriterion("invoice_date in", values, "invoiceDate");
            return (Criteria) this;
        }

        public Criteria andInvoiceDateNotIn(List<Date> values) {
            addCriterion("invoice_date not in", values, "invoiceDate");
            return (Criteria) this;
        }

        public Criteria andInvoiceDateBetween(Date value1, Date value2) {
            addCriterion("invoice_date between", value1, value2, "invoiceDate");
            return (Criteria) this;
        }

        public Criteria andInvoiceDateNotBetween(Date value1, Date value2) {
            addCriterion("invoice_date not between", value1, value2, "invoiceDate");
            return (Criteria) this;
        }

        public Criteria andRandomNumberIsNull() {
            addCriterion("random_number is null");
            return (Criteria) this;
        }

        public Criteria andRandomNumberIsNotNull() {
            addCriterion("random_number is not null");
            return (Criteria) this;
        }

        public Criteria andRandomNumberEqualTo(String value) {
            addCriterion("random_number =", value, "randomNumber");
            return (Criteria) this;
        }

        public Criteria andRandomNumberNotEqualTo(String value) {
            addCriterion("random_number <>", value, "randomNumber");
            return (Criteria) this;
        }

        public Criteria andRandomNumberGreaterThan(String value) {
            addCriterion("random_number >", value, "randomNumber");
            return (Criteria) this;
        }

        public Criteria andRandomNumberGreaterThanOrEqualTo(String value) {
            addCriterion("random_number >=", value, "randomNumber");
            return (Criteria) this;
        }

        public Criteria andRandomNumberLessThan(String value) {
            addCriterion("random_number <", value, "randomNumber");
            return (Criteria) this;
        }

        public Criteria andRandomNumberLessThanOrEqualTo(String value) {
            addCriterion("random_number <=", value, "randomNumber");
            return (Criteria) this;
        }

        public Criteria andRandomNumberLike(String value) {
            addCriterion("random_number like", value, "randomNumber");
            return (Criteria) this;
        }

        public Criteria andRandomNumberNotLike(String value) {
            addCriterion("random_number not like", value, "randomNumber");
            return (Criteria) this;
        }

        public Criteria andRandomNumberIn(List<String> values) {
            addCriterion("random_number in", values, "randomNumber");
            return (Criteria) this;
        }

        public Criteria andRandomNumberNotIn(List<String> values) {
            addCriterion("random_number not in", values, "randomNumber");
            return (Criteria) this;
        }

        public Criteria andRandomNumberBetween(String value1, String value2) {
            addCriterion("random_number between", value1, value2, "randomNumber");
            return (Criteria) this;
        }

        public Criteria andRandomNumberNotBetween(String value1, String value2) {
            addCriterion("random_number not between", value1, value2, "randomNumber");
            return (Criteria) this;
        }

        public Criteria andBuyerIdentifierIsNull() {
            addCriterion("buyer_identifier is null");
            return (Criteria) this;
        }

        public Criteria andBuyerIdentifierIsNotNull() {
            addCriterion("buyer_identifier is not null");
            return (Criteria) this;
        }

        public Criteria andBuyerIdentifierEqualTo(String value) {
            addCriterion("buyer_identifier =", value, "buyerIdentifier");
            return (Criteria) this;
        }

        public Criteria andBuyerIdentifierNotEqualTo(String value) {
            addCriterion("buyer_identifier <>", value, "buyerIdentifier");
            return (Criteria) this;
        }

        public Criteria andBuyerIdentifierGreaterThan(String value) {
            addCriterion("buyer_identifier >", value, "buyerIdentifier");
            return (Criteria) this;
        }

        public Criteria andBuyerIdentifierGreaterThanOrEqualTo(String value) {
            addCriterion("buyer_identifier >=", value, "buyerIdentifier");
            return (Criteria) this;
        }

        public Criteria andBuyerIdentifierLessThan(String value) {
            addCriterion("buyer_identifier <", value, "buyerIdentifier");
            return (Criteria) this;
        }

        public Criteria andBuyerIdentifierLessThanOrEqualTo(String value) {
            addCriterion("buyer_identifier <=", value, "buyerIdentifier");
            return (Criteria) this;
        }

        public Criteria andBuyerIdentifierLike(String value) {
            addCriterion("buyer_identifier like", value, "buyerIdentifier");
            return (Criteria) this;
        }

        public Criteria andBuyerIdentifierNotLike(String value) {
            addCriterion("buyer_identifier not like", value, "buyerIdentifier");
            return (Criteria) this;
        }

        public Criteria andBuyerIdentifierIn(List<String> values) {
            addCriterion("buyer_identifier in", values, "buyerIdentifier");
            return (Criteria) this;
        }

        public Criteria andBuyerIdentifierNotIn(List<String> values) {
            addCriterion("buyer_identifier not in", values, "buyerIdentifier");
            return (Criteria) this;
        }

        public Criteria andBuyerIdentifierBetween(String value1, String value2) {
            addCriterion("buyer_identifier between", value1, value2, "buyerIdentifier");
            return (Criteria) this;
        }

        public Criteria andBuyerIdentifierNotBetween(String value1, String value2) {
            addCriterion("buyer_identifier not between", value1, value2, "buyerIdentifier");
            return (Criteria) this;
        }

        public Criteria andBuyerNameIsNull() {
            addCriterion("buyer_name is null");
            return (Criteria) this;
        }

        public Criteria andBuyerNameIsNotNull() {
            addCriterion("buyer_name is not null");
            return (Criteria) this;
        }

        public Criteria andBuyerNameEqualTo(String value) {
            addCriterion("buyer_name =", value, "buyerName");
            return (Criteria) this;
        }

        public Criteria andBuyerNameNotEqualTo(String value) {
            addCriterion("buyer_name <>", value, "buyerName");
            return (Criteria) this;
        }

        public Criteria andBuyerNameGreaterThan(String value) {
            addCriterion("buyer_name >", value, "buyerName");
            return (Criteria) this;
        }

        public Criteria andBuyerNameGreaterThanOrEqualTo(String value) {
            addCriterion("buyer_name >=", value, "buyerName");
            return (Criteria) this;
        }

        public Criteria andBuyerNameLessThan(String value) {
            addCriterion("buyer_name <", value, "buyerName");
            return (Criteria) this;
        }

        public Criteria andBuyerNameLessThanOrEqualTo(String value) {
            addCriterion("buyer_name <=", value, "buyerName");
            return (Criteria) this;
        }

        public Criteria andBuyerNameLike(String value) {
            addCriterion("buyer_name like", value, "buyerName");
            return (Criteria) this;
        }

        public Criteria andBuyerNameNotLike(String value) {
            addCriterion("buyer_name not like", value, "buyerName");
            return (Criteria) this;
        }

        public Criteria andBuyerNameIn(List<String> values) {
            addCriterion("buyer_name in", values, "buyerName");
            return (Criteria) this;
        }

        public Criteria andBuyerNameNotIn(List<String> values) {
            addCriterion("buyer_name not in", values, "buyerName");
            return (Criteria) this;
        }

        public Criteria andBuyerNameBetween(String value1, String value2) {
            addCriterion("buyer_name between", value1, value2, "buyerName");
            return (Criteria) this;
        }

        public Criteria andBuyerNameNotBetween(String value1, String value2) {
            addCriterion("buyer_name not between", value1, value2, "buyerName");
            return (Criteria) this;
        }

        public Criteria andCarrierTypeIsNull() {
            addCriterion("carrier_type is null");
            return (Criteria) this;
        }

        public Criteria andCarrierTypeIsNotNull() {
            addCriterion("carrier_type is not null");
            return (Criteria) this;
        }

        public Criteria andCarrierTypeEqualTo(String value) {
            addCriterion("carrier_type =", value, "carrierType");
            return (Criteria) this;
        }

        public Criteria andCarrierTypeNotEqualTo(String value) {
            addCriterion("carrier_type <>", value, "carrierType");
            return (Criteria) this;
        }

        public Criteria andCarrierTypeGreaterThan(String value) {
            addCriterion("carrier_type >", value, "carrierType");
            return (Criteria) this;
        }

        public Criteria andCarrierTypeGreaterThanOrEqualTo(String value) {
            addCriterion("carrier_type >=", value, "carrierType");
            return (Criteria) this;
        }

        public Criteria andCarrierTypeLessThan(String value) {
            addCriterion("carrier_type <", value, "carrierType");
            return (Criteria) this;
        }

        public Criteria andCarrierTypeLessThanOrEqualTo(String value) {
            addCriterion("carrier_type <=", value, "carrierType");
            return (Criteria) this;
        }

        public Criteria andCarrierTypeLike(String value) {
            addCriterion("carrier_type like", value, "carrierType");
            return (Criteria) this;
        }

        public Criteria andCarrierTypeNotLike(String value) {
            addCriterion("carrier_type not like", value, "carrierType");
            return (Criteria) this;
        }

        public Criteria andCarrierTypeIn(List<String> values) {
            addCriterion("carrier_type in", values, "carrierType");
            return (Criteria) this;
        }

        public Criteria andCarrierTypeNotIn(List<String> values) {
            addCriterion("carrier_type not in", values, "carrierType");
            return (Criteria) this;
        }

        public Criteria andCarrierTypeBetween(String value1, String value2) {
            addCriterion("carrier_type between", value1, value2, "carrierType");
            return (Criteria) this;
        }

        public Criteria andCarrierTypeNotBetween(String value1, String value2) {
            addCriterion("carrier_type not between", value1, value2, "carrierType");
            return (Criteria) this;
        }

        public Criteria andCarrierIdIsNull() {
            addCriterion("carrier_id is null");
            return (Criteria) this;
        }

        public Criteria andCarrierIdIsNotNull() {
            addCriterion("carrier_id is not null");
            return (Criteria) this;
        }

        public Criteria andCarrierIdEqualTo(String value) {
            addCriterion("carrier_id =", value, "carrierId");
            return (Criteria) this;
        }

        public Criteria andCarrierIdNotEqualTo(String value) {
            addCriterion("carrier_id <>", value, "carrierId");
            return (Criteria) this;
        }

        public Criteria andCarrierIdGreaterThan(String value) {
            addCriterion("carrier_id >", value, "carrierId");
            return (Criteria) this;
        }

        public Criteria andCarrierIdGreaterThanOrEqualTo(String value) {
            addCriterion("carrier_id >=", value, "carrierId");
            return (Criteria) this;
        }

        public Criteria andCarrierIdLessThan(String value) {
            addCriterion("carrier_id <", value, "carrierId");
            return (Criteria) this;
        }

        public Criteria andCarrierIdLessThanOrEqualTo(String value) {
            addCriterion("carrier_id <=", value, "carrierId");
            return (Criteria) this;
        }

        public Criteria andCarrierIdLike(String value) {
            addCriterion("carrier_id like", value, "carrierId");
            return (Criteria) this;
        }

        public Criteria andCarrierIdNotLike(String value) {
            addCriterion("carrier_id not like", value, "carrierId");
            return (Criteria) this;
        }

        public Criteria andCarrierIdIn(List<String> values) {
            addCriterion("carrier_id in", values, "carrierId");
            return (Criteria) this;
        }

        public Criteria andCarrierIdNotIn(List<String> values) {
            addCriterion("carrier_id not in", values, "carrierId");
            return (Criteria) this;
        }

        public Criteria andCarrierIdBetween(String value1, String value2) {
            addCriterion("carrier_id between", value1, value2, "carrierId");
            return (Criteria) this;
        }

        public Criteria andCarrierIdNotBetween(String value1, String value2) {
            addCriterion("carrier_id not between", value1, value2, "carrierId");
            return (Criteria) this;
        }

        public Criteria andNpobanIsNull() {
            addCriterion("npoban is null");
            return (Criteria) this;
        }

        public Criteria andNpobanIsNotNull() {
            addCriterion("npoban is not null");
            return (Criteria) this;
        }

        public Criteria andNpobanEqualTo(String value) {
            addCriterion("npoban =", value, "npoban");
            return (Criteria) this;
        }

        public Criteria andNpobanNotEqualTo(String value) {
            addCriterion("npoban <>", value, "npoban");
            return (Criteria) this;
        }

        public Criteria andNpobanGreaterThan(String value) {
            addCriterion("npoban >", value, "npoban");
            return (Criteria) this;
        }

        public Criteria andNpobanGreaterThanOrEqualTo(String value) {
            addCriterion("npoban >=", value, "npoban");
            return (Criteria) this;
        }

        public Criteria andNpobanLessThan(String value) {
            addCriterion("npoban <", value, "npoban");
            return (Criteria) this;
        }

        public Criteria andNpobanLessThanOrEqualTo(String value) {
            addCriterion("npoban <=", value, "npoban");
            return (Criteria) this;
        }

        public Criteria andNpobanLike(String value) {
            addCriterion("npoban like", value, "npoban");
            return (Criteria) this;
        }

        public Criteria andNpobanNotLike(String value) {
            addCriterion("npoban not like", value, "npoban");
            return (Criteria) this;
        }

        public Criteria andNpobanIn(List<String> values) {
            addCriterion("npoban in", values, "npoban");
            return (Criteria) this;
        }

        public Criteria andNpobanNotIn(List<String> values) {
            addCriterion("npoban not in", values, "npoban");
            return (Criteria) this;
        }

        public Criteria andNpobanBetween(String value1, String value2) {
            addCriterion("npoban between", value1, value2, "npoban");
            return (Criteria) this;
        }

        public Criteria andNpobanNotBetween(String value1, String value2) {
            addCriterion("npoban not between", value1, value2, "npoban");
            return (Criteria) this;
        }

        public Criteria andPrintMarkIsNull() {
            addCriterion("print_mark is null");
            return (Criteria) this;
        }

        public Criteria andPrintMarkIsNotNull() {
            addCriterion("print_mark is not null");
            return (Criteria) this;
        }

        public Criteria andPrintMarkEqualTo(String value) {
            addCriterion("print_mark =", value, "printMark");
            return (Criteria) this;
        }

        public Criteria andPrintMarkNotEqualTo(String value) {
            addCriterion("print_mark <>", value, "printMark");
            return (Criteria) this;
        }

        public Criteria andPrintMarkGreaterThan(String value) {
            addCriterion("print_mark >", value, "printMark");
            return (Criteria) this;
        }

        public Criteria andPrintMarkGreaterThanOrEqualTo(String value) {
            addCriterion("print_mark >=", value, "printMark");
            return (Criteria) this;
        }

        public Criteria andPrintMarkLessThan(String value) {
            addCriterion("print_mark <", value, "printMark");
            return (Criteria) this;
        }

        public Criteria andPrintMarkLessThanOrEqualTo(String value) {
            addCriterion("print_mark <=", value, "printMark");
            return (Criteria) this;
        }

        public Criteria andPrintMarkLike(String value) {
            addCriterion("print_mark like", value, "printMark");
            return (Criteria) this;
        }

        public Criteria andPrintMarkNotLike(String value) {
            addCriterion("print_mark not like", value, "printMark");
            return (Criteria) this;
        }

        public Criteria andPrintMarkIn(List<String> values) {
            addCriterion("print_mark in", values, "printMark");
            return (Criteria) this;
        }

        public Criteria andPrintMarkNotIn(List<String> values) {
            addCriterion("print_mark not in", values, "printMark");
            return (Criteria) this;
        }

        public Criteria andPrintMarkBetween(String value1, String value2) {
            addCriterion("print_mark between", value1, value2, "printMark");
            return (Criteria) this;
        }

        public Criteria andPrintMarkNotBetween(String value1, String value2) {
            addCriterion("print_mark not between", value1, value2, "printMark");
            return (Criteria) this;
        }

        public Criteria andAmountIsNull() {
            addCriterion("amount is null");
            return (Criteria) this;
        }

        public Criteria andAmountIsNotNull() {
            addCriterion("amount is not null");
            return (Criteria) this;
        }

        public Criteria andAmountEqualTo(BigDecimal value) {
            addCriterion("amount =", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotEqualTo(BigDecimal value) {
            addCriterion("amount <>", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThan(BigDecimal value) {
            addCriterion("amount >", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("amount >=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThan(BigDecimal value) {
            addCriterion("amount <", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("amount <=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountIn(List<BigDecimal> values) {
            addCriterion("amount in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotIn(List<BigDecimal> values) {
            addCriterion("amount not in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("amount between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("amount not between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andTaxIsNull() {
            addCriterion("tax is null");
            return (Criteria) this;
        }

        public Criteria andTaxIsNotNull() {
            addCriterion("tax is not null");
            return (Criteria) this;
        }

        public Criteria andTaxEqualTo(BigDecimal value) {
            addCriterion("tax =", value, "tax");
            return (Criteria) this;
        }

        public Criteria andTaxNotEqualTo(BigDecimal value) {
            addCriterion("tax <>", value, "tax");
            return (Criteria) this;
        }

        public Criteria andTaxGreaterThan(BigDecimal value) {
            addCriterion("tax >", value, "tax");
            return (Criteria) this;
        }

        public Criteria andTaxGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("tax >=", value, "tax");
            return (Criteria) this;
        }

        public Criteria andTaxLessThan(BigDecimal value) {
            addCriterion("tax <", value, "tax");
            return (Criteria) this;
        }

        public Criteria andTaxLessThanOrEqualTo(BigDecimal value) {
            addCriterion("tax <=", value, "tax");
            return (Criteria) this;
        }

        public Criteria andTaxIn(List<BigDecimal> values) {
            addCriterion("tax in", values, "tax");
            return (Criteria) this;
        }

        public Criteria andTaxNotIn(List<BigDecimal> values) {
            addCriterion("tax not in", values, "tax");
            return (Criteria) this;
        }

        public Criteria andTaxBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("tax between", value1, value2, "tax");
            return (Criteria) this;
        }

        public Criteria andTaxNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("tax not between", value1, value2, "tax");
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

        public Criteria andResponseMsgIsNull() {
            addCriterion("response_msg is null");
            return (Criteria) this;
        }

        public Criteria andResponseMsgIsNotNull() {
            addCriterion("response_msg is not null");
            return (Criteria) this;
        }

        public Criteria andResponseMsgEqualTo(String value) {
            addCriterion("response_msg =", value, "responseMsg");
            return (Criteria) this;
        }

        public Criteria andResponseMsgNotEqualTo(String value) {
            addCriterion("response_msg <>", value, "responseMsg");
            return (Criteria) this;
        }

        public Criteria andResponseMsgGreaterThan(String value) {
            addCriterion("response_msg >", value, "responseMsg");
            return (Criteria) this;
        }

        public Criteria andResponseMsgGreaterThanOrEqualTo(String value) {
            addCriterion("response_msg >=", value, "responseMsg");
            return (Criteria) this;
        }

        public Criteria andResponseMsgLessThan(String value) {
            addCriterion("response_msg <", value, "responseMsg");
            return (Criteria) this;
        }

        public Criteria andResponseMsgLessThanOrEqualTo(String value) {
            addCriterion("response_msg <=", value, "responseMsg");
            return (Criteria) this;
        }

        public Criteria andResponseMsgLike(String value) {
            addCriterion("response_msg like", value, "responseMsg");
            return (Criteria) this;
        }

        public Criteria andResponseMsgNotLike(String value) {
            addCriterion("response_msg not like", value, "responseMsg");
            return (Criteria) this;
        }

        public Criteria andResponseMsgIn(List<String> values) {
            addCriterion("response_msg in", values, "responseMsg");
            return (Criteria) this;
        }

        public Criteria andResponseMsgNotIn(List<String> values) {
            addCriterion("response_msg not in", values, "responseMsg");
            return (Criteria) this;
        }

        public Criteria andResponseMsgBetween(String value1, String value2) {
            addCriterion("response_msg between", value1, value2, "responseMsg");
            return (Criteria) this;
        }

        public Criteria andResponseMsgNotBetween(String value1, String value2) {
            addCriterion("response_msg not between", value1, value2, "responseMsg");
            return (Criteria) this;
        }

        public Criteria andRawResponseIsNull() {
            addCriterion("raw_response is null");
            return (Criteria) this;
        }

        public Criteria andRawResponseIsNotNull() {
            addCriterion("raw_response is not null");
            return (Criteria) this;
        }

        public Criteria andRawResponseEqualTo(String value) {
            addCriterion("raw_response =", value, "rawResponse");
            return (Criteria) this;
        }

        public Criteria andRawResponseNotEqualTo(String value) {
            addCriterion("raw_response <>", value, "rawResponse");
            return (Criteria) this;
        }

        public Criteria andRawResponseGreaterThan(String value) {
            addCriterion("raw_response >", value, "rawResponse");
            return (Criteria) this;
        }

        public Criteria andRawResponseGreaterThanOrEqualTo(String value) {
            addCriterion("raw_response >=", value, "rawResponse");
            return (Criteria) this;
        }

        public Criteria andRawResponseLessThan(String value) {
            addCriterion("raw_response <", value, "rawResponse");
            return (Criteria) this;
        }

        public Criteria andRawResponseLessThanOrEqualTo(String value) {
            addCriterion("raw_response <=", value, "rawResponse");
            return (Criteria) this;
        }

        public Criteria andRawResponseLike(String value) {
            addCriterion("raw_response like", value, "rawResponse");
            return (Criteria) this;
        }

        public Criteria andRawResponseNotLike(String value) {
            addCriterion("raw_response not like", value, "rawResponse");
            return (Criteria) this;
        }

        public Criteria andRawResponseIn(List<String> values) {
            addCriterion("raw_response in", values, "rawResponse");
            return (Criteria) this;
        }

        public Criteria andRawResponseNotIn(List<String> values) {
            addCriterion("raw_response not in", values, "rawResponse");
            return (Criteria) this;
        }

        public Criteria andRawResponseBetween(String value1, String value2) {
            addCriterion("raw_response between", value1, value2, "rawResponse");
            return (Criteria) this;
        }

        public Criteria andRawResponseNotBetween(String value1, String value2) {
            addCriterion("raw_response not between", value1, value2, "rawResponse");
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