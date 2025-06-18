package com.qiyuan.web.entity.example;

import java.util.ArrayList;
import java.util.List;

public class LanternExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public LanternExample() {
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

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andSubtitleIsNull() {
            addCriterion("subtitle is null");
            return (Criteria) this;
        }

        public Criteria andSubtitleIsNotNull() {
            addCriterion("subtitle is not null");
            return (Criteria) this;
        }

        public Criteria andSubtitleEqualTo(String value) {
            addCriterion("subtitle =", value, "subtitle");
            return (Criteria) this;
        }

        public Criteria andSubtitleNotEqualTo(String value) {
            addCriterion("subtitle <>", value, "subtitle");
            return (Criteria) this;
        }

        public Criteria andSubtitleGreaterThan(String value) {
            addCriterion("subtitle >", value, "subtitle");
            return (Criteria) this;
        }

        public Criteria andSubtitleGreaterThanOrEqualTo(String value) {
            addCriterion("subtitle >=", value, "subtitle");
            return (Criteria) this;
        }

        public Criteria andSubtitleLessThan(String value) {
            addCriterion("subtitle <", value, "subtitle");
            return (Criteria) this;
        }

        public Criteria andSubtitleLessThanOrEqualTo(String value) {
            addCriterion("subtitle <=", value, "subtitle");
            return (Criteria) this;
        }

        public Criteria andSubtitleLike(String value) {
            addCriterion("subtitle like", value, "subtitle");
            return (Criteria) this;
        }

        public Criteria andSubtitleNotLike(String value) {
            addCriterion("subtitle not like", value, "subtitle");
            return (Criteria) this;
        }

        public Criteria andSubtitleIn(List<String> values) {
            addCriterion("subtitle in", values, "subtitle");
            return (Criteria) this;
        }

        public Criteria andSubtitleNotIn(List<String> values) {
            addCriterion("subtitle not in", values, "subtitle");
            return (Criteria) this;
        }

        public Criteria andSubtitleBetween(String value1, String value2) {
            addCriterion("subtitle between", value1, value2, "subtitle");
            return (Criteria) this;
        }

        public Criteria andSubtitleNotBetween(String value1, String value2) {
            addCriterion("subtitle not between", value1, value2, "subtitle");
            return (Criteria) this;
        }

        public Criteria andIconNameIsNull() {
            addCriterion("icon_name is null");
            return (Criteria) this;
        }

        public Criteria andIconNameIsNotNull() {
            addCriterion("icon_name is not null");
            return (Criteria) this;
        }

        public Criteria andIconNameEqualTo(String value) {
            addCriterion("icon_name =", value, "iconName");
            return (Criteria) this;
        }

        public Criteria andIconNameNotEqualTo(String value) {
            addCriterion("icon_name <>", value, "iconName");
            return (Criteria) this;
        }

        public Criteria andIconNameGreaterThan(String value) {
            addCriterion("icon_name >", value, "iconName");
            return (Criteria) this;
        }

        public Criteria andIconNameGreaterThanOrEqualTo(String value) {
            addCriterion("icon_name >=", value, "iconName");
            return (Criteria) this;
        }

        public Criteria andIconNameLessThan(String value) {
            addCriterion("icon_name <", value, "iconName");
            return (Criteria) this;
        }

        public Criteria andIconNameLessThanOrEqualTo(String value) {
            addCriterion("icon_name <=", value, "iconName");
            return (Criteria) this;
        }

        public Criteria andIconNameLike(String value) {
            addCriterion("icon_name like", value, "iconName");
            return (Criteria) this;
        }

        public Criteria andIconNameNotLike(String value) {
            addCriterion("icon_name not like", value, "iconName");
            return (Criteria) this;
        }

        public Criteria andIconNameIn(List<String> values) {
            addCriterion("icon_name in", values, "iconName");
            return (Criteria) this;
        }

        public Criteria andIconNameNotIn(List<String> values) {
            addCriterion("icon_name not in", values, "iconName");
            return (Criteria) this;
        }

        public Criteria andIconNameBetween(String value1, String value2) {
            addCriterion("icon_name between", value1, value2, "iconName");
            return (Criteria) this;
        }

        public Criteria andIconNameNotBetween(String value1, String value2) {
            addCriterion("icon_name not between", value1, value2, "iconName");
            return (Criteria) this;
        }

        public Criteria andLabelRightIsNull() {
            addCriterion("label_right is null");
            return (Criteria) this;
        }

        public Criteria andLabelRightIsNotNull() {
            addCriterion("label_right is not null");
            return (Criteria) this;
        }

        public Criteria andLabelRightEqualTo(String value) {
            addCriterion("label_right =", value, "labelRight");
            return (Criteria) this;
        }

        public Criteria andLabelRightNotEqualTo(String value) {
            addCriterion("label_right <>", value, "labelRight");
            return (Criteria) this;
        }

        public Criteria andLabelRightGreaterThan(String value) {
            addCriterion("label_right >", value, "labelRight");
            return (Criteria) this;
        }

        public Criteria andLabelRightGreaterThanOrEqualTo(String value) {
            addCriterion("label_right >=", value, "labelRight");
            return (Criteria) this;
        }

        public Criteria andLabelRightLessThan(String value) {
            addCriterion("label_right <", value, "labelRight");
            return (Criteria) this;
        }

        public Criteria andLabelRightLessThanOrEqualTo(String value) {
            addCriterion("label_right <=", value, "labelRight");
            return (Criteria) this;
        }

        public Criteria andLabelRightLike(String value) {
            addCriterion("label_right like", value, "labelRight");
            return (Criteria) this;
        }

        public Criteria andLabelRightNotLike(String value) {
            addCriterion("label_right not like", value, "labelRight");
            return (Criteria) this;
        }

        public Criteria andLabelRightIn(List<String> values) {
            addCriterion("label_right in", values, "labelRight");
            return (Criteria) this;
        }

        public Criteria andLabelRightNotIn(List<String> values) {
            addCriterion("label_right not in", values, "labelRight");
            return (Criteria) this;
        }

        public Criteria andLabelRightBetween(String value1, String value2) {
            addCriterion("label_right between", value1, value2, "labelRight");
            return (Criteria) this;
        }

        public Criteria andLabelRightNotBetween(String value1, String value2) {
            addCriterion("label_right not between", value1, value2, "labelRight");
            return (Criteria) this;
        }

        public Criteria andQaJsonIsNull() {
            addCriterion("qa_json is null");
            return (Criteria) this;
        }

        public Criteria andQaJsonIsNotNull() {
            addCriterion("qa_json is not null");
            return (Criteria) this;
        }

        public Criteria andQaJsonEqualTo(String value) {
            addCriterion("qa_json =", value, "qaJson");
            return (Criteria) this;
        }

        public Criteria andQaJsonNotEqualTo(String value) {
            addCriterion("qa_json <>", value, "qaJson");
            return (Criteria) this;
        }

        public Criteria andQaJsonGreaterThan(String value) {
            addCriterion("qa_json >", value, "qaJson");
            return (Criteria) this;
        }

        public Criteria andQaJsonGreaterThanOrEqualTo(String value) {
            addCriterion("qa_json >=", value, "qaJson");
            return (Criteria) this;
        }

        public Criteria andQaJsonLessThan(String value) {
            addCriterion("qa_json <", value, "qaJson");
            return (Criteria) this;
        }

        public Criteria andQaJsonLessThanOrEqualTo(String value) {
            addCriterion("qa_json <=", value, "qaJson");
            return (Criteria) this;
        }

        public Criteria andQaJsonLike(String value) {
            addCriterion("qa_json like", value, "qaJson");
            return (Criteria) this;
        }

        public Criteria andQaJsonNotLike(String value) {
            addCriterion("qa_json not like", value, "qaJson");
            return (Criteria) this;
        }

        public Criteria andQaJsonIn(List<String> values) {
            addCriterion("qa_json in", values, "qaJson");
            return (Criteria) this;
        }

        public Criteria andQaJsonNotIn(List<String> values) {
            addCriterion("qa_json not in", values, "qaJson");
            return (Criteria) this;
        }

        public Criteria andQaJsonBetween(String value1, String value2) {
            addCriterion("qa_json between", value1, value2, "qaJson");
            return (Criteria) this;
        }

        public Criteria andQaJsonNotBetween(String value1, String value2) {
            addCriterion("qa_json not between", value1, value2, "qaJson");
            return (Criteria) this;
        }

        public Criteria andSortIsNull() {
            addCriterion("sort is null");
            return (Criteria) this;
        }

        public Criteria andSortIsNotNull() {
            addCriterion("sort is not null");
            return (Criteria) this;
        }

        public Criteria andSortEqualTo(Byte value) {
            addCriterion("sort =", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotEqualTo(Byte value) {
            addCriterion("sort <>", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortGreaterThan(Byte value) {
            addCriterion("sort >", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortGreaterThanOrEqualTo(Byte value) {
            addCriterion("sort >=", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortLessThan(Byte value) {
            addCriterion("sort <", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortLessThanOrEqualTo(Byte value) {
            addCriterion("sort <=", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortIn(List<Byte> values) {
            addCriterion("sort in", values, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotIn(List<Byte> values) {
            addCriterion("sort not in", values, "sort");
            return (Criteria) this;
        }

        public Criteria andSortBetween(Byte value1, Byte value2) {
            addCriterion("sort between", value1, value2, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotBetween(Byte value1, Byte value2) {
            addCriterion("sort not between", value1, value2, "sort");
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