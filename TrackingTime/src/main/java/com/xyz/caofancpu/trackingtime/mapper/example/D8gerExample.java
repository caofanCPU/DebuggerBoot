package com.xyz.caofancpu.trackingtime.mapper.example;

import com.xyz.caofancpu.trackingtime.constant.ActiveStatusEnum;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * D8gerMo对应的Example单表操作对象
 *
 * @author 帝八哥
 */
public class D8gerExample {

    protected String orderByClause;

    protected Integer limit;

    protected boolean distinct;

    protected List<Criteria> conditionCriteria;

    public D8gerExample() {
        conditionCriteria = new ArrayList<>();
    }

    public D8gerExample andOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
        return this;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public D8gerExample andLimit(Integer limit) {
        if (limit != null && limit > 0) {
            this.limit = limit;
        }
        return this;
    }

    public Integer getLimit() {
        return limit;
    }

    public D8gerExample andDistinct(boolean distinct) {
        this.distinct = distinct;
        return this;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getConditionCriteria() {
        return conditionCriteria;
    }

    public void or(Criteria criteria) {
        conditionCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        conditionCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (conditionCriteria.size() == 0) {
            conditionCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        return new Criteria();
    }

    public void clear() {
        conditionCriteria.clear();
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

        /**
         * id Is Null
         *
         * @return
         */
        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        /**
         * id IS Not Null
         *
         * @return
         */
        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        /**
         * id Equal
         *
         * @return
         */
        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        /**
         * id Not Equal
         *
         * @return
         */
        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        /**
         * id Greater Than
         *
         * @return
         */
        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        /**
         * id Greater Than Or Equal To
         *
         * @return
         */
        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        /**
         * id Less Than
         *
         * @return
         */
        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        /**
         * id Less Than Or Equal To
         *
         * @return
         */
        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        /**
         * id In
         *
         * @return
         */
        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        /**
         * id Not In
         *
         * @return
         */
        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        /**
         * id Between
         *
         * @return
         */
        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        /**
         * id Not Between
         *
         * @return
         */
        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        /**
         * name Is Null
         *
         * @return
         */
        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        /**
         * name IS Not Null
         *
         * @return
         */
        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        /**
         * name Equal
         *
         * @return
         */
        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        /**
         * name Not Equal
         *
         * @return
         */
        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        /**
         * name Greater Than
         *
         * @return
         */
        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        /**
         * name Greater Than Or Equal To
         *
         * @return
         */
        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        /**
         * name Less Than
         *
         * @return
         */
        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        /**
         * name Less Than Or Equal To
         *
         * @return
         */
        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        /**
         * name In
         *
         * @return
         */
        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        /**
         * name Not In
         *
         * @return
         */
        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        /**
         * name Between
         *
         * @return
         */
        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        /**
         * name Not Between
         *
         * @return
         */
        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        /**
         * name Like
         *
         * @return
         */
        public Criteria andNameLike(String value) {
            addCriterion("name like", value + "%", "name");
            return (Criteria) this;
        }

        /**
         * name Not Like
         *
         * @return
         */
        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value + "%", "name");
            return (Criteria) this;
        }

        /**
         * age Is Null
         *
         * @return
         */
        public Criteria andAgeIsNull() {
            addCriterion("age is null");
            return (Criteria) this;
        }

        /**
         * age IS Not Null
         *
         * @return
         */
        public Criteria andAgeIsNotNull() {
            addCriterion("age is not null");
            return (Criteria) this;
        }

        /**
         * age Equal
         *
         * @return
         */
        public Criteria andAgeEqualTo(Integer value) {
            addCriterion("age =", value, "age");
            return (Criteria) this;
        }

        /**
         * age Not Equal
         *
         * @return
         */
        public Criteria andAgeNotEqualTo(Integer value) {
            addCriterion("age <>", value, "age");
            return (Criteria) this;
        }

        /**
         * age Greater Than
         *
         * @return
         */
        public Criteria andAgeGreaterThan(Integer value) {
            addCriterion("age >", value, "age");
            return (Criteria) this;
        }

        /**
         * age Greater Than Or Equal To
         *
         * @return
         */
        public Criteria andAgeGreaterThanOrEqualTo(Integer value) {
            addCriterion("age >=", value, "age");
            return (Criteria) this;
        }

        /**
         * age Less Than
         *
         * @return
         */
        public Criteria andAgeLessThan(Integer value) {
            addCriterion("age <", value, "age");
            return (Criteria) this;
        }

        /**
         * age Less Than Or Equal To
         *
         * @return
         */
        public Criteria andAgeLessThanOrEqualTo(Integer value) {
            addCriterion("age <=", value, "age");
            return (Criteria) this;
        }

        /**
         * age In
         *
         * @return
         */
        public Criteria andAgeIn(List<Integer> values) {
            addCriterion("age in", values, "age");
            return (Criteria) this;
        }

        /**
         * age Not In
         *
         * @return
         */
        public Criteria andAgeNotIn(List<Integer> values) {
            addCriterion("age not in", values, "age");
            return (Criteria) this;
        }

        /**
         * age Between
         *
         * @return
         */
        public Criteria andAgeBetween(Integer value1, Integer value2) {
            addCriterion("age between", value1, value2, "age");
            return (Criteria) this;
        }

        /**
         * age Not Between
         *
         * @return
         */
        public Criteria andAgeNotBetween(Integer value1, Integer value2) {
            addCriterion("age not between", value1, value2, "age");
            return (Criteria) this;
        }

        /**
         * job Is Null
         *
         * @return
         */
        public Criteria andJobIsNull() {
            addCriterion("job is null");
            return (Criteria) this;
        }

        /**
         * job IS Not Null
         *
         * @return
         */
        public Criteria andJobIsNotNull() {
            addCriterion("job is not null");
            return (Criteria) this;
        }

        /**
         * job Equal
         *
         * @return
         */
        public Criteria andJobEqualTo(String value) {
            addCriterion("job =", value, "job");
            return (Criteria) this;
        }

        /**
         * job Not Equal
         *
         * @return
         */
        public Criteria andJobNotEqualTo(String value) {
            addCriterion("job <>", value, "job");
            return (Criteria) this;
        }

        /**
         * job Greater Than
         *
         * @return
         */
        public Criteria andJobGreaterThan(String value) {
            addCriterion("job >", value, "job");
            return (Criteria) this;
        }

        /**
         * job Greater Than Or Equal To
         *
         * @return
         */
        public Criteria andJobGreaterThanOrEqualTo(String value) {
            addCriterion("job >=", value, "job");
            return (Criteria) this;
        }

        /**
         * job Less Than
         *
         * @return
         */
        public Criteria andJobLessThan(String value) {
            addCriterion("job <", value, "job");
            return (Criteria) this;
        }

        /**
         * job Less Than Or Equal To
         *
         * @return
         */
        public Criteria andJobLessThanOrEqualTo(String value) {
            addCriterion("job <=", value, "job");
            return (Criteria) this;
        }

        /**
         * job In
         *
         * @return
         */
        public Criteria andJobIn(List<String> values) {
            addCriterion("job in", values, "job");
            return (Criteria) this;
        }

        /**
         * job Not In
         *
         * @return
         */
        public Criteria andJobNotIn(List<String> values) {
            addCriterion("job not in", values, "job");
            return (Criteria) this;
        }

        /**
         * job Between
         *
         * @return
         */
        public Criteria andJobBetween(String value1, String value2) {
            addCriterion("job between", value1, value2, "job");
            return (Criteria) this;
        }

        /**
         * job Not Between
         *
         * @return
         */
        public Criteria andJobNotBetween(String value1, String value2) {
            addCriterion("job not between", value1, value2, "job");
            return (Criteria) this;
        }

        /**
         * job Like
         *
         * @return
         */
        public Criteria andJobLike(String value) {
            addCriterion("job like", value + "%", "job");
            return (Criteria) this;
        }

        /**
         * job Not Like
         *
         * @return
         */
        public Criteria andJobNotLike(String value) {
            addCriterion("job not like", value + "%", "job");
            return (Criteria) this;
        }

        /**
         * activeStatus Is Null
         *
         * @return
         */
        public Criteria andActiveStatusIsNull() {
            addCriterion("active_status is null");
            return (Criteria) this;
        }

        /**
         * activeStatus IS Not Null
         *
         * @return
         */
        public Criteria andActiveStatusIsNotNull() {
            addCriterion("active_status is not null");
            return (Criteria) this;
        }

        /**
         * activeStatus Equal
         *
         * @return
         */
        public Criteria andActiveStatusEqualTo(ActiveStatusEnum value) {
            addCriterion("active_status =", value, "activeStatus");
            return (Criteria) this;
        }

        /**
         * activeStatus Not Equal
         *
         * @return
         */
        public Criteria andActiveStatusNotEqualTo(ActiveStatusEnum value) {
            addCriterion("active_status <>", value, "activeStatus");
            return (Criteria) this;
        }

        /**
         * activeStatus Greater Than
         *
         * @return
         */
        public Criteria andActiveStatusGreaterThan(ActiveStatusEnum value) {
            addCriterion("active_status >", value, "activeStatus");
            return (Criteria) this;
        }

        /**
         * activeStatus Greater Than Or Equal To
         *
         * @return
         */
        public Criteria andActiveStatusGreaterThanOrEqualTo(ActiveStatusEnum value) {
            addCriterion("active_status >=", value, "activeStatus");
            return (Criteria) this;
        }

        /**
         * activeStatus Less Than
         *
         * @return
         */
        public Criteria andActiveStatusLessThan(ActiveStatusEnum value) {
            addCriterion("active_status <", value, "activeStatus");
            return (Criteria) this;
        }

        /**
         * activeStatus Less Than Or Equal To
         *
         * @return
         */
        public Criteria andActiveStatusLessThanOrEqualTo(ActiveStatusEnum value) {
            addCriterion("active_status <=", value, "activeStatus");
            return (Criteria) this;
        }

        /**
         * activeStatus In
         *
         * @return
         */
        public Criteria andActiveStatusIn(List<ActiveStatusEnum> values) {
            addCriterion("active_status in", values, "activeStatus");
            return (Criteria) this;
        }

        /**
         * activeStatus Not In
         *
         * @return
         */
        public Criteria andActiveStatusNotIn(List<ActiveStatusEnum> values) {
            addCriterion("active_status not in", values, "activeStatus");
            return (Criteria) this;
        }

        /**
         * activeStatus Between
         *
         * @return
         */
        public Criteria andActiveStatusBetween(ActiveStatusEnum value1, ActiveStatusEnum value2) {
            addCriterion("active_status between", value1, value2, "activeStatus");
            return (Criteria) this;
        }

        /**
         * activeStatus Not Between
         *
         * @return
         */
        public Criteria andActiveStatusNotBetween(ActiveStatusEnum value1, ActiveStatusEnum value2) {
            addCriterion("active_status not between", value1, value2, "activeStatus");
            return (Criteria) this;
        }

        /**
         * createTime Is Null
         *
         * @return
         */
        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        /**
         * createTime IS Not Null
         *
         * @return
         */
        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        /**
         * createTime Equal
         *
         * @return
         */
        public Criteria andCreateTimeEqualTo(LocalDateTime value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        /**
         * createTime Not Equal
         *
         * @return
         */
        public Criteria andCreateTimeNotEqualTo(LocalDateTime value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        /**
         * createTime Greater Than
         *
         * @return
         */
        public Criteria andCreateTimeGreaterThan(LocalDateTime value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        /**
         * createTime Greater Than Or Equal To
         *
         * @return
         */
        public Criteria andCreateTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        /**
         * createTime Less Than
         *
         * @return
         */
        public Criteria andCreateTimeLessThan(LocalDateTime value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        /**
         * createTime Less Than Or Equal To
         *
         * @return
         */
        public Criteria andCreateTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        /**
         * createTime In
         *
         * @return
         */
        public Criteria andCreateTimeIn(List<LocalDateTime> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        /**
         * createTime Not In
         *
         * @return
         */
        public Criteria andCreateTimeNotIn(List<LocalDateTime> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        /**
         * createTime Between
         *
         * @return
         */
        public Criteria andCreateTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        /**
         * createTime Not Between
         *
         * @return
         */
        public Criteria andCreateTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        /**
         * updateTime Is Null
         *
         * @return
         */
        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        /**
         * updateTime IS Not Null
         *
         * @return
         */
        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        /**
         * updateTime Equal
         *
         * @return
         */
        public Criteria andUpdateTimeEqualTo(LocalDateTime value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        /**
         * updateTime Not Equal
         *
         * @return
         */
        public Criteria andUpdateTimeNotEqualTo(LocalDateTime value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        /**
         * updateTime Greater Than
         *
         * @return
         */
        public Criteria andUpdateTimeGreaterThan(LocalDateTime value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        /**
         * updateTime Greater Than Or Equal To
         *
         * @return
         */
        public Criteria andUpdateTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        /**
         * updateTime Less Than
         *
         * @return
         */
        public Criteria andUpdateTimeLessThan(LocalDateTime value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        /**
         * updateTime Less Than Or Equal To
         *
         * @return
         */
        public Criteria andUpdateTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        /**
         * updateTime In
         *
         * @return
         */
        public Criteria andUpdateTimeIn(List<LocalDateTime> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        /**
         * updateTime Not In
         *
         * @return
         */
        public Criteria andUpdateTimeNotIn(List<LocalDateTime> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        /**
         * updateTime Between
         *
         * @return
         */
        public Criteria andUpdateTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        /**
         * updateTime Not Between
         *
         * @return
         */
        public Criteria andUpdateTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
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
    }
}