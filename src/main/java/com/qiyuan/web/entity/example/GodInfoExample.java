package com.qiyuan.web.entity.example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GodInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public GodInfoExample() {
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

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(String value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(String value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(String value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(String value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(String value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLike(String value) {
            addCriterion("user_id like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotLike(String value) {
            addCriterion("user_id not like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<String> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<String> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(String value1, String value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(String value1, String value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andGodIdIsNull() {
            addCriterion("god_id is null");
            return (Criteria) this;
        }

        public Criteria andGodIdIsNotNull() {
            addCriterion("god_id is not null");
            return (Criteria) this;
        }

        public Criteria andGodIdEqualTo(String value) {
            addCriterion("god_id =", value, "godId");
            return (Criteria) this;
        }

        public Criteria andGodIdNotEqualTo(String value) {
            addCriterion("god_id <>", value, "godId");
            return (Criteria) this;
        }

        public Criteria andGodIdGreaterThan(String value) {
            addCriterion("god_id >", value, "godId");
            return (Criteria) this;
        }

        public Criteria andGodIdGreaterThanOrEqualTo(String value) {
            addCriterion("god_id >=", value, "godId");
            return (Criteria) this;
        }

        public Criteria andGodIdLessThan(String value) {
            addCriterion("god_id <", value, "godId");
            return (Criteria) this;
        }

        public Criteria andGodIdLessThanOrEqualTo(String value) {
            addCriterion("god_id <=", value, "godId");
            return (Criteria) this;
        }

        public Criteria andGodIdLike(String value) {
            addCriterion("god_id like", value, "godId");
            return (Criteria) this;
        }

        public Criteria andGodIdNotLike(String value) {
            addCriterion("god_id not like", value, "godId");
            return (Criteria) this;
        }

        public Criteria andGodIdIn(List<String> values) {
            addCriterion("god_id in", values, "godId");
            return (Criteria) this;
        }

        public Criteria andGodIdNotIn(List<String> values) {
            addCriterion("god_id not in", values, "godId");
            return (Criteria) this;
        }

        public Criteria andGodIdBetween(String value1, String value2) {
            addCriterion("god_id between", value1, value2, "godId");
            return (Criteria) this;
        }

        public Criteria andGodIdNotBetween(String value1, String value2) {
            addCriterion("god_id not between", value1, value2, "godId");
            return (Criteria) this;
        }

        public Criteria andLevelIsNull() {
            addCriterion("level is null");
            return (Criteria) this;
        }

        public Criteria andLevelIsNotNull() {
            addCriterion("level is not null");
            return (Criteria) this;
        }

        public Criteria andLevelEqualTo(Byte value) {
            addCriterion("level =", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelNotEqualTo(Byte value) {
            addCriterion("level <>", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelGreaterThan(Byte value) {
            addCriterion("level >", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelGreaterThanOrEqualTo(Byte value) {
            addCriterion("level >=", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelLessThan(Byte value) {
            addCriterion("level <", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelLessThanOrEqualTo(Byte value) {
            addCriterion("level <=", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelIn(List<Byte> values) {
            addCriterion("level in", values, "level");
            return (Criteria) this;
        }

        public Criteria andLevelNotIn(List<Byte> values) {
            addCriterion("level not in", values, "level");
            return (Criteria) this;
        }

        public Criteria andLevelBetween(Byte value1, Byte value2) {
            addCriterion("level between", value1, value2, "level");
            return (Criteria) this;
        }

        public Criteria andLevelNotBetween(Byte value1, Byte value2) {
            addCriterion("level not between", value1, value2, "level");
            return (Criteria) this;
        }

        public Criteria andExpIsNull() {
            addCriterion("exp is null");
            return (Criteria) this;
        }

        public Criteria andExpIsNotNull() {
            addCriterion("exp is not null");
            return (Criteria) this;
        }

        public Criteria andExpEqualTo(Byte value) {
            addCriterion("exp =", value, "exp");
            return (Criteria) this;
        }

        public Criteria andExpNotEqualTo(Byte value) {
            addCriterion("exp <>", value, "exp");
            return (Criteria) this;
        }

        public Criteria andExpGreaterThan(Byte value) {
            addCriterion("exp >", value, "exp");
            return (Criteria) this;
        }

        public Criteria andExpGreaterThanOrEqualTo(Byte value) {
            addCriterion("exp >=", value, "exp");
            return (Criteria) this;
        }

        public Criteria andExpLessThan(Byte value) {
            addCriterion("exp <", value, "exp");
            return (Criteria) this;
        }

        public Criteria andExpLessThanOrEqualTo(Byte value) {
            addCriterion("exp <=", value, "exp");
            return (Criteria) this;
        }

        public Criteria andExpIn(List<Byte> values) {
            addCriterion("exp in", values, "exp");
            return (Criteria) this;
        }

        public Criteria andExpNotIn(List<Byte> values) {
            addCriterion("exp not in", values, "exp");
            return (Criteria) this;
        }

        public Criteria andExpBetween(Byte value1, Byte value2) {
            addCriterion("exp between", value1, value2, "exp");
            return (Criteria) this;
        }

        public Criteria andExpNotBetween(Byte value1, Byte value2) {
            addCriterion("exp not between", value1, value2, "exp");
            return (Criteria) this;
        }

        public Criteria andJiaobeiLastTimeIsNull() {
            addCriterion("jiaobei_last_time is null");
            return (Criteria) this;
        }

        public Criteria andJiaobeiLastTimeIsNotNull() {
            addCriterion("jiaobei_last_time is not null");
            return (Criteria) this;
        }

        public Criteria andJiaobeiLastTimeEqualTo(Date value) {
            addCriterion("jiaobei_last_time =", value, "jiaobeiLastTime");
            return (Criteria) this;
        }

        public Criteria andJiaobeiLastTimeNotEqualTo(Date value) {
            addCriterion("jiaobei_last_time <>", value, "jiaobeiLastTime");
            return (Criteria) this;
        }

        public Criteria andJiaobeiLastTimeGreaterThan(Date value) {
            addCriterion("jiaobei_last_time >", value, "jiaobeiLastTime");
            return (Criteria) this;
        }

        public Criteria andJiaobeiLastTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("jiaobei_last_time >=", value, "jiaobeiLastTime");
            return (Criteria) this;
        }

        public Criteria andJiaobeiLastTimeLessThan(Date value) {
            addCriterion("jiaobei_last_time <", value, "jiaobeiLastTime");
            return (Criteria) this;
        }

        public Criteria andJiaobeiLastTimeLessThanOrEqualTo(Date value) {
            addCriterion("jiaobei_last_time <=", value, "jiaobeiLastTime");
            return (Criteria) this;
        }

        public Criteria andJiaobeiLastTimeIn(List<Date> values) {
            addCriterion("jiaobei_last_time in", values, "jiaobeiLastTime");
            return (Criteria) this;
        }

        public Criteria andJiaobeiLastTimeNotIn(List<Date> values) {
            addCriterion("jiaobei_last_time not in", values, "jiaobeiLastTime");
            return (Criteria) this;
        }

        public Criteria andJiaobeiLastTimeBetween(Date value1, Date value2) {
            addCriterion("jiaobei_last_time between", value1, value2, "jiaobeiLastTime");
            return (Criteria) this;
        }

        public Criteria andJiaobeiLastTimeNotBetween(Date value1, Date value2) {
            addCriterion("jiaobei_last_time not between", value1, value2, "jiaobeiLastTime");
            return (Criteria) this;
        }

        public Criteria andGoldenExpirationIsNull() {
            addCriterion("golden_expiration is null");
            return (Criteria) this;
        }

        public Criteria andGoldenExpirationIsNotNull() {
            addCriterion("golden_expiration is not null");
            return (Criteria) this;
        }

        public Criteria andGoldenExpirationEqualTo(Date value) {
            addCriterion("golden_expiration =", value, "goldenExpiration");
            return (Criteria) this;
        }

        public Criteria andGoldenExpirationNotEqualTo(Date value) {
            addCriterion("golden_expiration <>", value, "goldenExpiration");
            return (Criteria) this;
        }

        public Criteria andGoldenExpirationGreaterThan(Date value) {
            addCriterion("golden_expiration >", value, "goldenExpiration");
            return (Criteria) this;
        }

        public Criteria andGoldenExpirationGreaterThanOrEqualTo(Date value) {
            addCriterion("golden_expiration >=", value, "goldenExpiration");
            return (Criteria) this;
        }

        public Criteria andGoldenExpirationLessThan(Date value) {
            addCriterion("golden_expiration <", value, "goldenExpiration");
            return (Criteria) this;
        }

        public Criteria andGoldenExpirationLessThanOrEqualTo(Date value) {
            addCriterion("golden_expiration <=", value, "goldenExpiration");
            return (Criteria) this;
        }

        public Criteria andGoldenExpirationIn(List<Date> values) {
            addCriterion("golden_expiration in", values, "goldenExpiration");
            return (Criteria) this;
        }

        public Criteria andGoldenExpirationNotIn(List<Date> values) {
            addCriterion("golden_expiration not in", values, "goldenExpiration");
            return (Criteria) this;
        }

        public Criteria andGoldenExpirationBetween(Date value1, Date value2) {
            addCriterion("golden_expiration between", value1, value2, "goldenExpiration");
            return (Criteria) this;
        }

        public Criteria andGoldenExpirationNotBetween(Date value1, Date value2) {
            addCriterion("golden_expiration not between", value1, value2, "goldenExpiration");
            return (Criteria) this;
        }

        public Criteria andOnshelfTimeIsNull() {
            addCriterion("onshelf_time is null");
            return (Criteria) this;
        }

        public Criteria andOnshelfTimeIsNotNull() {
            addCriterion("onshelf_time is not null");
            return (Criteria) this;
        }

        public Criteria andOnshelfTimeEqualTo(Date value) {
            addCriterion("onshelf_time =", value, "onshelfTime");
            return (Criteria) this;
        }

        public Criteria andOnshelfTimeNotEqualTo(Date value) {
            addCriterion("onshelf_time <>", value, "onshelfTime");
            return (Criteria) this;
        }

        public Criteria andOnshelfTimeGreaterThan(Date value) {
            addCriterion("onshelf_time >", value, "onshelfTime");
            return (Criteria) this;
        }

        public Criteria andOnshelfTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("onshelf_time >=", value, "onshelfTime");
            return (Criteria) this;
        }

        public Criteria andOnshelfTimeLessThan(Date value) {
            addCriterion("onshelf_time <", value, "onshelfTime");
            return (Criteria) this;
        }

        public Criteria andOnshelfTimeLessThanOrEqualTo(Date value) {
            addCriterion("onshelf_time <=", value, "onshelfTime");
            return (Criteria) this;
        }

        public Criteria andOnshelfTimeIn(List<Date> values) {
            addCriterion("onshelf_time in", values, "onshelfTime");
            return (Criteria) this;
        }

        public Criteria andOnshelfTimeNotIn(List<Date> values) {
            addCriterion("onshelf_time not in", values, "onshelfTime");
            return (Criteria) this;
        }

        public Criteria andOnshelfTimeBetween(Date value1, Date value2) {
            addCriterion("onshelf_time between", value1, value2, "onshelfTime");
            return (Criteria) this;
        }

        public Criteria andOnshelfTimeNotBetween(Date value1, Date value2) {
            addCriterion("onshelf_time not between", value1, value2, "onshelfTime");
            return (Criteria) this;
        }

        public Criteria andOffshelfTimeIsNull() {
            addCriterion("offshelf_time is null");
            return (Criteria) this;
        }

        public Criteria andOffshelfTimeIsNotNull() {
            addCriterion("offshelf_time is not null");
            return (Criteria) this;
        }

        public Criteria andOffshelfTimeEqualTo(Date value) {
            addCriterion("offshelf_time =", value, "offshelfTime");
            return (Criteria) this;
        }

        public Criteria andOffshelfTimeNotEqualTo(Date value) {
            addCriterion("offshelf_time <>", value, "offshelfTime");
            return (Criteria) this;
        }

        public Criteria andOffshelfTimeGreaterThan(Date value) {
            addCriterion("offshelf_time >", value, "offshelfTime");
            return (Criteria) this;
        }

        public Criteria andOffshelfTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("offshelf_time >=", value, "offshelfTime");
            return (Criteria) this;
        }

        public Criteria andOffshelfTimeLessThan(Date value) {
            addCriterion("offshelf_time <", value, "offshelfTime");
            return (Criteria) this;
        }

        public Criteria andOffshelfTimeLessThanOrEqualTo(Date value) {
            addCriterion("offshelf_time <=", value, "offshelfTime");
            return (Criteria) this;
        }

        public Criteria andOffshelfTimeIn(List<Date> values) {
            addCriterion("offshelf_time in", values, "offshelfTime");
            return (Criteria) this;
        }

        public Criteria andOffshelfTimeNotIn(List<Date> values) {
            addCriterion("offshelf_time not in", values, "offshelfTime");
            return (Criteria) this;
        }

        public Criteria andOffshelfTimeBetween(Date value1, Date value2) {
            addCriterion("offshelf_time between", value1, value2, "offshelfTime");
            return (Criteria) this;
        }

        public Criteria andOffshelfTimeNotBetween(Date value1, Date value2) {
            addCriterion("offshelf_time not between", value1, value2, "offshelfTime");
            return (Criteria) this;
        }

        public Criteria andCooldownTimeIsNull() {
            addCriterion("cooldown_time is null");
            return (Criteria) this;
        }

        public Criteria andCooldownTimeIsNotNull() {
            addCriterion("cooldown_time is not null");
            return (Criteria) this;
        }

        public Criteria andCooldownTimeEqualTo(Date value) {
            addCriterion("cooldown_time =", value, "cooldownTime");
            return (Criteria) this;
        }

        public Criteria andCooldownTimeNotEqualTo(Date value) {
            addCriterion("cooldown_time <>", value, "cooldownTime");
            return (Criteria) this;
        }

        public Criteria andCooldownTimeGreaterThan(Date value) {
            addCriterion("cooldown_time >", value, "cooldownTime");
            return (Criteria) this;
        }

        public Criteria andCooldownTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("cooldown_time >=", value, "cooldownTime");
            return (Criteria) this;
        }

        public Criteria andCooldownTimeLessThan(Date value) {
            addCriterion("cooldown_time <", value, "cooldownTime");
            return (Criteria) this;
        }

        public Criteria andCooldownTimeLessThanOrEqualTo(Date value) {
            addCriterion("cooldown_time <=", value, "cooldownTime");
            return (Criteria) this;
        }

        public Criteria andCooldownTimeIn(List<Date> values) {
            addCriterion("cooldown_time in", values, "cooldownTime");
            return (Criteria) this;
        }

        public Criteria andCooldownTimeNotIn(List<Date> values) {
            addCriterion("cooldown_time not in", values, "cooldownTime");
            return (Criteria) this;
        }

        public Criteria andCooldownTimeBetween(Date value1, Date value2) {
            addCriterion("cooldown_time between", value1, value2, "cooldownTime");
            return (Criteria) this;
        }

        public Criteria andCooldownTimeNotBetween(Date value1, Date value2) {
            addCriterion("cooldown_time not between", value1, value2, "cooldownTime");
            return (Criteria) this;
        }

        public Criteria andOfferingListIsNull() {
            addCriterion("offering_list is null");
            return (Criteria) this;
        }

        public Criteria andOfferingListIsNotNull() {
            addCriterion("offering_list is not null");
            return (Criteria) this;
        }

        public Criteria andOfferingListEqualTo(String value) {
            addCriterion("offering_list =", value, "offeringList");
            return (Criteria) this;
        }

        public Criteria andOfferingListNotEqualTo(String value) {
            addCriterion("offering_list <>", value, "offeringList");
            return (Criteria) this;
        }

        public Criteria andOfferingListGreaterThan(String value) {
            addCriterion("offering_list >", value, "offeringList");
            return (Criteria) this;
        }

        public Criteria andOfferingListGreaterThanOrEqualTo(String value) {
            addCriterion("offering_list >=", value, "offeringList");
            return (Criteria) this;
        }

        public Criteria andOfferingListLessThan(String value) {
            addCriterion("offering_list <", value, "offeringList");
            return (Criteria) this;
        }

        public Criteria andOfferingListLessThanOrEqualTo(String value) {
            addCriterion("offering_list <=", value, "offeringList");
            return (Criteria) this;
        }

        public Criteria andOfferingListLike(String value) {
            addCriterion("offering_list like", value, "offeringList");
            return (Criteria) this;
        }

        public Criteria andOfferingListNotLike(String value) {
            addCriterion("offering_list not like", value, "offeringList");
            return (Criteria) this;
        }

        public Criteria andOfferingListIn(List<String> values) {
            addCriterion("offering_list in", values, "offeringList");
            return (Criteria) this;
        }

        public Criteria andOfferingListNotIn(List<String> values) {
            addCriterion("offering_list not in", values, "offeringList");
            return (Criteria) this;
        }

        public Criteria andOfferingListBetween(String value1, String value2) {
            addCriterion("offering_list between", value1, value2, "offeringList");
            return (Criteria) this;
        }

        public Criteria andOfferingListNotBetween(String value1, String value2) {
            addCriterion("offering_list not between", value1, value2, "offeringList");
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