package com.qiyuan.web.entity.example;

import java.util.ArrayList;
import java.util.List;

public class PoemExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PoemExample() {
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

        public Criteria andSequenceIsNull() {
            addCriterion("sequence is null");
            return (Criteria) this;
        }

        public Criteria andSequenceIsNotNull() {
            addCriterion("sequence is not null");
            return (Criteria) this;
        }

        public Criteria andSequenceEqualTo(String value) {
            addCriterion("sequence =", value, "sequence");
            return (Criteria) this;
        }

        public Criteria andSequenceNotEqualTo(String value) {
            addCriterion("sequence <>", value, "sequence");
            return (Criteria) this;
        }

        public Criteria andSequenceGreaterThan(String value) {
            addCriterion("sequence >", value, "sequence");
            return (Criteria) this;
        }

        public Criteria andSequenceGreaterThanOrEqualTo(String value) {
            addCriterion("sequence >=", value, "sequence");
            return (Criteria) this;
        }

        public Criteria andSequenceLessThan(String value) {
            addCriterion("sequence <", value, "sequence");
            return (Criteria) this;
        }

        public Criteria andSequenceLessThanOrEqualTo(String value) {
            addCriterion("sequence <=", value, "sequence");
            return (Criteria) this;
        }

        public Criteria andSequenceLike(String value) {
            addCriterion("sequence like", value, "sequence");
            return (Criteria) this;
        }

        public Criteria andSequenceNotLike(String value) {
            addCriterion("sequence not like", value, "sequence");
            return (Criteria) this;
        }

        public Criteria andSequenceIn(List<String> values) {
            addCriterion("sequence in", values, "sequence");
            return (Criteria) this;
        }

        public Criteria andSequenceNotIn(List<String> values) {
            addCriterion("sequence not in", values, "sequence");
            return (Criteria) this;
        }

        public Criteria andSequenceBetween(String value1, String value2) {
            addCriterion("sequence between", value1, value2, "sequence");
            return (Criteria) this;
        }

        public Criteria andSequenceNotBetween(String value1, String value2) {
            addCriterion("sequence not between", value1, value2, "sequence");
            return (Criteria) this;
        }

        public Criteria andTitleIsNull() {
            addCriterion("title is null");
            return (Criteria) this;
        }

        public Criteria andTitleIsNotNull() {
            addCriterion("title is not null");
            return (Criteria) this;
        }

        public Criteria andTitleEqualTo(String value) {
            addCriterion("title =", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotEqualTo(String value) {
            addCriterion("title <>", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThan(String value) {
            addCriterion("title >", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThanOrEqualTo(String value) {
            addCriterion("title >=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThan(String value) {
            addCriterion("title <", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThanOrEqualTo(String value) {
            addCriterion("title <=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLike(String value) {
            addCriterion("title like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotLike(String value) {
            addCriterion("title not like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleIn(List<String> values) {
            addCriterion("title in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotIn(List<String> values) {
            addCriterion("title not in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleBetween(String value1, String value2) {
            addCriterion("title between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotBetween(String value1, String value2) {
            addCriterion("title not between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andZodiacIsNull() {
            addCriterion("zodiac is null");
            return (Criteria) this;
        }

        public Criteria andZodiacIsNotNull() {
            addCriterion("zodiac is not null");
            return (Criteria) this;
        }

        public Criteria andZodiacEqualTo(String value) {
            addCriterion("zodiac =", value, "zodiac");
            return (Criteria) this;
        }

        public Criteria andZodiacNotEqualTo(String value) {
            addCriterion("zodiac <>", value, "zodiac");
            return (Criteria) this;
        }

        public Criteria andZodiacGreaterThan(String value) {
            addCriterion("zodiac >", value, "zodiac");
            return (Criteria) this;
        }

        public Criteria andZodiacGreaterThanOrEqualTo(String value) {
            addCriterion("zodiac >=", value, "zodiac");
            return (Criteria) this;
        }

        public Criteria andZodiacLessThan(String value) {
            addCriterion("zodiac <", value, "zodiac");
            return (Criteria) this;
        }

        public Criteria andZodiacLessThanOrEqualTo(String value) {
            addCriterion("zodiac <=", value, "zodiac");
            return (Criteria) this;
        }

        public Criteria andZodiacLike(String value) {
            addCriterion("zodiac like", value, "zodiac");
            return (Criteria) this;
        }

        public Criteria andZodiacNotLike(String value) {
            addCriterion("zodiac not like", value, "zodiac");
            return (Criteria) this;
        }

        public Criteria andZodiacIn(List<String> values) {
            addCriterion("zodiac in", values, "zodiac");
            return (Criteria) this;
        }

        public Criteria andZodiacNotIn(List<String> values) {
            addCriterion("zodiac not in", values, "zodiac");
            return (Criteria) this;
        }

        public Criteria andZodiacBetween(String value1, String value2) {
            addCriterion("zodiac between", value1, value2, "zodiac");
            return (Criteria) this;
        }

        public Criteria andZodiacNotBetween(String value1, String value2) {
            addCriterion("zodiac not between", value1, value2, "zodiac");
            return (Criteria) this;
        }

        public Criteria andFortuneIsNull() {
            addCriterion("fortune is null");
            return (Criteria) this;
        }

        public Criteria andFortuneIsNotNull() {
            addCriterion("fortune is not null");
            return (Criteria) this;
        }

        public Criteria andFortuneEqualTo(String value) {
            addCriterion("fortune =", value, "fortune");
            return (Criteria) this;
        }

        public Criteria andFortuneNotEqualTo(String value) {
            addCriterion("fortune <>", value, "fortune");
            return (Criteria) this;
        }

        public Criteria andFortuneGreaterThan(String value) {
            addCriterion("fortune >", value, "fortune");
            return (Criteria) this;
        }

        public Criteria andFortuneGreaterThanOrEqualTo(String value) {
            addCriterion("fortune >=", value, "fortune");
            return (Criteria) this;
        }

        public Criteria andFortuneLessThan(String value) {
            addCriterion("fortune <", value, "fortune");
            return (Criteria) this;
        }

        public Criteria andFortuneLessThanOrEqualTo(String value) {
            addCriterion("fortune <=", value, "fortune");
            return (Criteria) this;
        }

        public Criteria andFortuneLike(String value) {
            addCriterion("fortune like", value, "fortune");
            return (Criteria) this;
        }

        public Criteria andFortuneNotLike(String value) {
            addCriterion("fortune not like", value, "fortune");
            return (Criteria) this;
        }

        public Criteria andFortuneIn(List<String> values) {
            addCriterion("fortune in", values, "fortune");
            return (Criteria) this;
        }

        public Criteria andFortuneNotIn(List<String> values) {
            addCriterion("fortune not in", values, "fortune");
            return (Criteria) this;
        }

        public Criteria andFortuneBetween(String value1, String value2) {
            addCriterion("fortune between", value1, value2, "fortune");
            return (Criteria) this;
        }

        public Criteria andFortuneNotBetween(String value1, String value2) {
            addCriterion("fortune not between", value1, value2, "fortune");
            return (Criteria) this;
        }

        public Criteria andPoemIsNull() {
            addCriterion("poem is null");
            return (Criteria) this;
        }

        public Criteria andPoemIsNotNull() {
            addCriterion("poem is not null");
            return (Criteria) this;
        }

        public Criteria andPoemEqualTo(String value) {
            addCriterion("poem =", value, "poem");
            return (Criteria) this;
        }

        public Criteria andPoemNotEqualTo(String value) {
            addCriterion("poem <>", value, "poem");
            return (Criteria) this;
        }

        public Criteria andPoemGreaterThan(String value) {
            addCriterion("poem >", value, "poem");
            return (Criteria) this;
        }

        public Criteria andPoemGreaterThanOrEqualTo(String value) {
            addCriterion("poem >=", value, "poem");
            return (Criteria) this;
        }

        public Criteria andPoemLessThan(String value) {
            addCriterion("poem <", value, "poem");
            return (Criteria) this;
        }

        public Criteria andPoemLessThanOrEqualTo(String value) {
            addCriterion("poem <=", value, "poem");
            return (Criteria) this;
        }

        public Criteria andPoemLike(String value) {
            addCriterion("poem like", value, "poem");
            return (Criteria) this;
        }

        public Criteria andPoemNotLike(String value) {
            addCriterion("poem not like", value, "poem");
            return (Criteria) this;
        }

        public Criteria andPoemIn(List<String> values) {
            addCriterion("poem in", values, "poem");
            return (Criteria) this;
        }

        public Criteria andPoemNotIn(List<String> values) {
            addCriterion("poem not in", values, "poem");
            return (Criteria) this;
        }

        public Criteria andPoemBetween(String value1, String value2) {
            addCriterion("poem between", value1, value2, "poem");
            return (Criteria) this;
        }

        public Criteria andPoemNotBetween(String value1, String value2) {
            addCriterion("poem not between", value1, value2, "poem");
            return (Criteria) this;
        }

        public Criteria andMeaningIsNull() {
            addCriterion("meaning is null");
            return (Criteria) this;
        }

        public Criteria andMeaningIsNotNull() {
            addCriterion("meaning is not null");
            return (Criteria) this;
        }

        public Criteria andMeaningEqualTo(String value) {
            addCriterion("meaning =", value, "meaning");
            return (Criteria) this;
        }

        public Criteria andMeaningNotEqualTo(String value) {
            addCriterion("meaning <>", value, "meaning");
            return (Criteria) this;
        }

        public Criteria andMeaningGreaterThan(String value) {
            addCriterion("meaning >", value, "meaning");
            return (Criteria) this;
        }

        public Criteria andMeaningGreaterThanOrEqualTo(String value) {
            addCriterion("meaning >=", value, "meaning");
            return (Criteria) this;
        }

        public Criteria andMeaningLessThan(String value) {
            addCriterion("meaning <", value, "meaning");
            return (Criteria) this;
        }

        public Criteria andMeaningLessThanOrEqualTo(String value) {
            addCriterion("meaning <=", value, "meaning");
            return (Criteria) this;
        }

        public Criteria andMeaningLike(String value) {
            addCriterion("meaning like", value, "meaning");
            return (Criteria) this;
        }

        public Criteria andMeaningNotLike(String value) {
            addCriterion("meaning not like", value, "meaning");
            return (Criteria) this;
        }

        public Criteria andMeaningIn(List<String> values) {
            addCriterion("meaning in", values, "meaning");
            return (Criteria) this;
        }

        public Criteria andMeaningNotIn(List<String> values) {
            addCriterion("meaning not in", values, "meaning");
            return (Criteria) this;
        }

        public Criteria andMeaningBetween(String value1, String value2) {
            addCriterion("meaning between", value1, value2, "meaning");
            return (Criteria) this;
        }

        public Criteria andMeaningNotBetween(String value1, String value2) {
            addCriterion("meaning not between", value1, value2, "meaning");
            return (Criteria) this;
        }

        public Criteria andDongponoteIsNull() {
            addCriterion("dongponote is null");
            return (Criteria) this;
        }

        public Criteria andDongponoteIsNotNull() {
            addCriterion("dongponote is not null");
            return (Criteria) this;
        }

        public Criteria andDongponoteEqualTo(String value) {
            addCriterion("dongponote =", value, "dongponote");
            return (Criteria) this;
        }

        public Criteria andDongponoteNotEqualTo(String value) {
            addCriterion("dongponote <>", value, "dongponote");
            return (Criteria) this;
        }

        public Criteria andDongponoteGreaterThan(String value) {
            addCriterion("dongponote >", value, "dongponote");
            return (Criteria) this;
        }

        public Criteria andDongponoteGreaterThanOrEqualTo(String value) {
            addCriterion("dongponote >=", value, "dongponote");
            return (Criteria) this;
        }

        public Criteria andDongponoteLessThan(String value) {
            addCriterion("dongponote <", value, "dongponote");
            return (Criteria) this;
        }

        public Criteria andDongponoteLessThanOrEqualTo(String value) {
            addCriterion("dongponote <=", value, "dongponote");
            return (Criteria) this;
        }

        public Criteria andDongponoteLike(String value) {
            addCriterion("dongponote like", value, "dongponote");
            return (Criteria) this;
        }

        public Criteria andDongponoteNotLike(String value) {
            addCriterion("dongponote not like", value, "dongponote");
            return (Criteria) this;
        }

        public Criteria andDongponoteIn(List<String> values) {
            addCriterion("dongponote in", values, "dongponote");
            return (Criteria) this;
        }

        public Criteria andDongponoteNotIn(List<String> values) {
            addCriterion("dongponote not in", values, "dongponote");
            return (Criteria) this;
        }

        public Criteria andDongponoteBetween(String value1, String value2) {
            addCriterion("dongponote between", value1, value2, "dongponote");
            return (Criteria) this;
        }

        public Criteria andDongponoteNotBetween(String value1, String value2) {
            addCriterion("dongponote not between", value1, value2, "dongponote");
            return (Criteria) this;
        }

        public Criteria andBixiannoteIsNull() {
            addCriterion("bixiannote is null");
            return (Criteria) this;
        }

        public Criteria andBixiannoteIsNotNull() {
            addCriterion("bixiannote is not null");
            return (Criteria) this;
        }

        public Criteria andBixiannoteEqualTo(String value) {
            addCriterion("bixiannote =", value, "bixiannote");
            return (Criteria) this;
        }

        public Criteria andBixiannoteNotEqualTo(String value) {
            addCriterion("bixiannote <>", value, "bixiannote");
            return (Criteria) this;
        }

        public Criteria andBixiannoteGreaterThan(String value) {
            addCriterion("bixiannote >", value, "bixiannote");
            return (Criteria) this;
        }

        public Criteria andBixiannoteGreaterThanOrEqualTo(String value) {
            addCriterion("bixiannote >=", value, "bixiannote");
            return (Criteria) this;
        }

        public Criteria andBixiannoteLessThan(String value) {
            addCriterion("bixiannote <", value, "bixiannote");
            return (Criteria) this;
        }

        public Criteria andBixiannoteLessThanOrEqualTo(String value) {
            addCriterion("bixiannote <=", value, "bixiannote");
            return (Criteria) this;
        }

        public Criteria andBixiannoteLike(String value) {
            addCriterion("bixiannote like", value, "bixiannote");
            return (Criteria) this;
        }

        public Criteria andBixiannoteNotLike(String value) {
            addCriterion("bixiannote not like", value, "bixiannote");
            return (Criteria) this;
        }

        public Criteria andBixiannoteIn(List<String> values) {
            addCriterion("bixiannote in", values, "bixiannote");
            return (Criteria) this;
        }

        public Criteria andBixiannoteNotIn(List<String> values) {
            addCriterion("bixiannote not in", values, "bixiannote");
            return (Criteria) this;
        }

        public Criteria andBixiannoteBetween(String value1, String value2) {
            addCriterion("bixiannote between", value1, value2, "bixiannote");
            return (Criteria) this;
        }

        public Criteria andBixiannoteNotBetween(String value1, String value2) {
            addCriterion("bixiannote not between", value1, value2, "bixiannote");
            return (Criteria) this;
        }

        public Criteria andExplanationIsNull() {
            addCriterion("explanation is null");
            return (Criteria) this;
        }

        public Criteria andExplanationIsNotNull() {
            addCriterion("explanation is not null");
            return (Criteria) this;
        }

        public Criteria andExplanationEqualTo(String value) {
            addCriterion("explanation =", value, "explanation");
            return (Criteria) this;
        }

        public Criteria andExplanationNotEqualTo(String value) {
            addCriterion("explanation <>", value, "explanation");
            return (Criteria) this;
        }

        public Criteria andExplanationGreaterThan(String value) {
            addCriterion("explanation >", value, "explanation");
            return (Criteria) this;
        }

        public Criteria andExplanationGreaterThanOrEqualTo(String value) {
            addCriterion("explanation >=", value, "explanation");
            return (Criteria) this;
        }

        public Criteria andExplanationLessThan(String value) {
            addCriterion("explanation <", value, "explanation");
            return (Criteria) this;
        }

        public Criteria andExplanationLessThanOrEqualTo(String value) {
            addCriterion("explanation <=", value, "explanation");
            return (Criteria) this;
        }

        public Criteria andExplanationLike(String value) {
            addCriterion("explanation like", value, "explanation");
            return (Criteria) this;
        }

        public Criteria andExplanationNotLike(String value) {
            addCriterion("explanation not like", value, "explanation");
            return (Criteria) this;
        }

        public Criteria andExplanationIn(List<String> values) {
            addCriterion("explanation in", values, "explanation");
            return (Criteria) this;
        }

        public Criteria andExplanationNotIn(List<String> values) {
            addCriterion("explanation not in", values, "explanation");
            return (Criteria) this;
        }

        public Criteria andExplanationBetween(String value1, String value2) {
            addCriterion("explanation between", value1, value2, "explanation");
            return (Criteria) this;
        }

        public Criteria andExplanationNotBetween(String value1, String value2) {
            addCriterion("explanation not between", value1, value2, "explanation");
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