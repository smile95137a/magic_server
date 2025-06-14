package com.qiyuan.web.model;

import java.util.ArrayList;
import java.util.List;

public class GodExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public GodExample() {
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

        public Criteria andImageUrlIsNull() {
            addCriterion("image_url is null");
            return (Criteria) this;
        }

        public Criteria andImageUrlIsNotNull() {
            addCriterion("image_url is not null");
            return (Criteria) this;
        }

        public Criteria andImageUrlEqualTo(String value) {
            addCriterion("image_url =", value, "imageUrl");
            return (Criteria) this;
        }

        public Criteria andImageUrlNotEqualTo(String value) {
            addCriterion("image_url <>", value, "imageUrl");
            return (Criteria) this;
        }

        public Criteria andImageUrlGreaterThan(String value) {
            addCriterion("image_url >", value, "imageUrl");
            return (Criteria) this;
        }

        public Criteria andImageUrlGreaterThanOrEqualTo(String value) {
            addCriterion("image_url >=", value, "imageUrl");
            return (Criteria) this;
        }

        public Criteria andImageUrlLessThan(String value) {
            addCriterion("image_url <", value, "imageUrl");
            return (Criteria) this;
        }

        public Criteria andImageUrlLessThanOrEqualTo(String value) {
            addCriterion("image_url <=", value, "imageUrl");
            return (Criteria) this;
        }

        public Criteria andImageUrlLike(String value) {
            addCriterion("image_url like", value, "imageUrl");
            return (Criteria) this;
        }

        public Criteria andImageUrlNotLike(String value) {
            addCriterion("image_url not like", value, "imageUrl");
            return (Criteria) this;
        }

        public Criteria andImageUrlIn(List<String> values) {
            addCriterion("image_url in", values, "imageUrl");
            return (Criteria) this;
        }

        public Criteria andImageUrlNotIn(List<String> values) {
            addCriterion("image_url not in", values, "imageUrl");
            return (Criteria) this;
        }

        public Criteria andImageUrlBetween(String value1, String value2) {
            addCriterion("image_url between", value1, value2, "imageUrl");
            return (Criteria) this;
        }

        public Criteria andImageUrlNotBetween(String value1, String value2) {
            addCriterion("image_url not between", value1, value2, "imageUrl");
            return (Criteria) this;
        }

        public Criteria andTextTopIsNull() {
            addCriterion("text_top is null");
            return (Criteria) this;
        }

        public Criteria andTextTopIsNotNull() {
            addCriterion("text_top is not null");
            return (Criteria) this;
        }

        public Criteria andTextTopEqualTo(String value) {
            addCriterion("text_top =", value, "textTop");
            return (Criteria) this;
        }

        public Criteria andTextTopNotEqualTo(String value) {
            addCriterion("text_top <>", value, "textTop");
            return (Criteria) this;
        }

        public Criteria andTextTopGreaterThan(String value) {
            addCriterion("text_top >", value, "textTop");
            return (Criteria) this;
        }

        public Criteria andTextTopGreaterThanOrEqualTo(String value) {
            addCriterion("text_top >=", value, "textTop");
            return (Criteria) this;
        }

        public Criteria andTextTopLessThan(String value) {
            addCriterion("text_top <", value, "textTop");
            return (Criteria) this;
        }

        public Criteria andTextTopLessThanOrEqualTo(String value) {
            addCriterion("text_top <=", value, "textTop");
            return (Criteria) this;
        }

        public Criteria andTextTopLike(String value) {
            addCriterion("text_top like", value, "textTop");
            return (Criteria) this;
        }

        public Criteria andTextTopNotLike(String value) {
            addCriterion("text_top not like", value, "textTop");
            return (Criteria) this;
        }

        public Criteria andTextTopIn(List<String> values) {
            addCriterion("text_top in", values, "textTop");
            return (Criteria) this;
        }

        public Criteria andTextTopNotIn(List<String> values) {
            addCriterion("text_top not in", values, "textTop");
            return (Criteria) this;
        }

        public Criteria andTextTopBetween(String value1, String value2) {
            addCriterion("text_top between", value1, value2, "textTop");
            return (Criteria) this;
        }

        public Criteria andTextTopNotBetween(String value1, String value2) {
            addCriterion("text_top not between", value1, value2, "textTop");
            return (Criteria) this;
        }

        public Criteria andTextBottomIsNull() {
            addCriterion("text_bottom is null");
            return (Criteria) this;
        }

        public Criteria andTextBottomIsNotNull() {
            addCriterion("text_bottom is not null");
            return (Criteria) this;
        }

        public Criteria andTextBottomEqualTo(String value) {
            addCriterion("text_bottom =", value, "textBottom");
            return (Criteria) this;
        }

        public Criteria andTextBottomNotEqualTo(String value) {
            addCriterion("text_bottom <>", value, "textBottom");
            return (Criteria) this;
        }

        public Criteria andTextBottomGreaterThan(String value) {
            addCriterion("text_bottom >", value, "textBottom");
            return (Criteria) this;
        }

        public Criteria andTextBottomGreaterThanOrEqualTo(String value) {
            addCriterion("text_bottom >=", value, "textBottom");
            return (Criteria) this;
        }

        public Criteria andTextBottomLessThan(String value) {
            addCriterion("text_bottom <", value, "textBottom");
            return (Criteria) this;
        }

        public Criteria andTextBottomLessThanOrEqualTo(String value) {
            addCriterion("text_bottom <=", value, "textBottom");
            return (Criteria) this;
        }

        public Criteria andTextBottomLike(String value) {
            addCriterion("text_bottom like", value, "textBottom");
            return (Criteria) this;
        }

        public Criteria andTextBottomNotLike(String value) {
            addCriterion("text_bottom not like", value, "textBottom");
            return (Criteria) this;
        }

        public Criteria andTextBottomIn(List<String> values) {
            addCriterion("text_bottom in", values, "textBottom");
            return (Criteria) this;
        }

        public Criteria andTextBottomNotIn(List<String> values) {
            addCriterion("text_bottom not in", values, "textBottom");
            return (Criteria) this;
        }

        public Criteria andTextBottomBetween(String value1, String value2) {
            addCriterion("text_bottom between", value1, value2, "textBottom");
            return (Criteria) this;
        }

        public Criteria andTextBottomNotBetween(String value1, String value2) {
            addCriterion("text_bottom not between", value1, value2, "textBottom");
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