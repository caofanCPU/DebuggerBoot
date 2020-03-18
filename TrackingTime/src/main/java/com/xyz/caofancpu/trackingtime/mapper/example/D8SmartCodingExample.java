package com.xyz.caofancpu.trackingtime.mapper.example;

import com.xyz.caofancpu.trackingtime.constant.enums.EncryptTypeEnum;
import com.xyz.caofancpu.trackingtime.constant.enums.MarkContentStatusEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * D8SmartCodingMo对应的Example单表操作对象
 *
 * @author 帝八哥
 */
public class D8SmartCodingExample {

    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public D8SmartCodingExample() {
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
        return new Criteria();
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
        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        /**
         * id Not Equal
         *
         * @return
         */
        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        /**
         * id Greater Than
         *
         * @return
         */
        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        /**
         * id Greater Than Or Equal To
         *
         * @return
         */
        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        /**
         * id Less Than
         *
         * @return
         */
        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        /**
         * id Less Than Or Equal To
         *
         * @return
         */
        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        /**
         * id In
         *
         * @return
         */
        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        /**
         * id Not In
         *
         * @return
         */
        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        /**
         * id Between
         *
         * @return
         */
        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        /**
         * id Not Between
         *
         * @return
         */
        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        /**
         * score Is Null
         *
         * @return
         */
        public Criteria andScoreIsNull() {
            addCriterion("score is null");
            return (Criteria) this;
        }

        /**
         * score IS Not Null
         *
         * @return
         */
        public Criteria andScoreIsNotNull() {
            addCriterion("score is not null");
            return (Criteria) this;
        }

        /**
         * score Equal
         *
         * @return
         */
        public Criteria andScoreEqualTo(Long value) {
            addCriterion("score =", value, "score");
            return (Criteria) this;
        }

        /**
         * score Not Equal
         *
         * @return
         */
        public Criteria andScoreNotEqualTo(Long value) {
            addCriterion("score <>", value, "score");
            return (Criteria) this;
        }

        /**
         * score Greater Than
         *
         * @return
         */
        public Criteria andScoreGreaterThan(Long value) {
            addCriterion("score >", value, "score");
            return (Criteria) this;
        }

        /**
         * score Greater Than Or Equal To
         *
         * @return
         */
        public Criteria andScoreGreaterThanOrEqualTo(Long value) {
            addCriterion("score >=", value, "score");
            return (Criteria) this;
        }

        /**
         * score Less Than
         *
         * @return
         */
        public Criteria andScoreLessThan(Long value) {
            addCriterion("score <", value, "score");
            return (Criteria) this;
        }

        /**
         * score Less Than Or Equal To
         *
         * @return
         */
        public Criteria andScoreLessThanOrEqualTo(Long value) {
            addCriterion("score <=", value, "score");
            return (Criteria) this;
        }

        /**
         * score In
         *
         * @return
         */
        public Criteria andScoreIn(List<Long> values) {
            addCriterion("score in", values, "score");
            return (Criteria) this;
        }

        /**
         * score Not In
         *
         * @return
         */
        public Criteria andScoreNotIn(List<Long> values) {
            addCriterion("score not in", values, "score");
            return (Criteria) this;
        }

        /**
         * score Between
         *
         * @return
         */
        public Criteria andScoreBetween(Long value1, Long value2) {
            addCriterion("score between", value1, value2, "score");
            return (Criteria) this;
        }

        /**
         * score Not Between
         *
         * @return
         */
        public Criteria andScoreNotBetween(Long value1, Long value2) {
            addCriterion("score not between", value1, value2, "score");
            return (Criteria) this;
        }

        /**
         * nickName Is Null
         *
         * @return
         */
        public Criteria andNickNameIsNull() {
            addCriterion("nick_name is null");
            return (Criteria) this;
        }

        /**
         * nickName IS Not Null
         *
         * @return
         */
        public Criteria andNickNameIsNotNull() {
            addCriterion("nick_name is not null");
            return (Criteria) this;
        }

        /**
         * nickName Equal
         *
         * @return
         */
        public Criteria andNickNameEqualTo(String value) {
            addCriterion("nick_name =", value, "nickName");
            return (Criteria) this;
        }

        /**
         * nickName Not Equal
         *
         * @return
         */
        public Criteria andNickNameNotEqualTo(String value) {
            addCriterion("nick_name <>", value, "nickName");
            return (Criteria) this;
        }

        /**
         * nickName Greater Than
         *
         * @return
         */
        public Criteria andNickNameGreaterThan(String value) {
            addCriterion("nick_name >", value, "nickName");
            return (Criteria) this;
        }

        /**
         * nickName Greater Than Or Equal To
         *
         * @return
         */
        public Criteria andNickNameGreaterThanOrEqualTo(String value) {
            addCriterion("nick_name >=", value, "nickName");
            return (Criteria) this;
        }

        /**
         * nickName Less Than
         *
         * @return
         */
        public Criteria andNickNameLessThan(String value) {
            addCriterion("nick_name <", value, "nickName");
            return (Criteria) this;
        }

        /**
         * nickName Less Than Or Equal To
         *
         * @return
         */
        public Criteria andNickNameLessThanOrEqualTo(String value) {
            addCriterion("nick_name <=", value, "nickName");
            return (Criteria) this;
        }

        /**
         * nickName In
         *
         * @return
         */
        public Criteria andNickNameIn(List<String> values) {
            addCriterion("nick_name in", values, "nickName");
            return (Criteria) this;
        }

        /**
         * nickName Not In
         *
         * @return
         */
        public Criteria andNickNameNotIn(List<String> values) {
            addCriterion("nick_name not in", values, "nickName");
            return (Criteria) this;
        }

        /**
         * nickName Between
         *
         * @return
         */
        public Criteria andNickNameBetween(String value1, String value2) {
            addCriterion("nick_name between", value1, value2, "nickName");
            return (Criteria) this;
        }

        /**
         * nickName Not Between
         *
         * @return
         */
        public Criteria andNickNameNotBetween(String value1, String value2) {
            addCriterion("nick_name not between", value1, value2, "nickName");
            return (Criteria) this;
        }

        /**
         * nickName Like
         *
         * @return
         */
        public Criteria andNickNameLike(String value) {
            addCriterion("nick_name like", value + "%", "nickName");
            return (Criteria) this;
        }

        /**
         * nickName Not Like
         *
         * @return
         */
        public Criteria andNickNameNotLike(String value) {
            addCriterion("nick_name not like", value + "%", "nickName");
            return (Criteria) this;
        }

        /**
         * deleted Is Null
         *
         * @return
         */
        public Criteria andDeletedIsNull() {
            addCriterion("deleted is null");
            return (Criteria) this;
        }

        /**
         * deleted IS Not Null
         *
         * @return
         */
        public Criteria andDeletedIsNotNull() {
            addCriterion("deleted is not null");
            return (Criteria) this;
        }

        /**
         * deleted Equal
         *
         * @return
         */
        public Criteria andDeletedEqualTo(Boolean value) {
            addCriterion("deleted =", value, "deleted");
            return (Criteria) this;
        }

        /**
         * deleted Not Equal
         *
         * @return
         */
        public Criteria andDeletedNotEqualTo(Boolean value) {
            addCriterion("deleted <>", value, "deleted");
            return (Criteria) this;
        }

        /**
         * deleted Greater Than
         *
         * @return
         */
        public Criteria andDeletedGreaterThan(Boolean value) {
            addCriterion("deleted >", value, "deleted");
            return (Criteria) this;
        }

        /**
         * deleted Greater Than Or Equal To
         *
         * @return
         */
        public Criteria andDeletedGreaterThanOrEqualTo(Boolean value) {
            addCriterion("deleted >=", value, "deleted");
            return (Criteria) this;
        }

        /**
         * deleted Less Than
         *
         * @return
         */
        public Criteria andDeletedLessThan(Boolean value) {
            addCriterion("deleted <", value, "deleted");
            return (Criteria) this;
        }

        /**
         * deleted Less Than Or Equal To
         *
         * @return
         */
        public Criteria andDeletedLessThanOrEqualTo(Boolean value) {
            addCriterion("deleted <=", value, "deleted");
            return (Criteria) this;
        }

        /**
         * deleted In
         *
         * @return
         */
        public Criteria andDeletedIn(List<Boolean> values) {
            addCriterion("deleted in", values, "deleted");
            return (Criteria) this;
        }

        /**
         * deleted Not In
         *
         * @return
         */
        public Criteria andDeletedNotIn(List<Boolean> values) {
            addCriterion("deleted not in", values, "deleted");
            return (Criteria) this;
        }

        /**
         * deleted Between
         *
         * @return
         */
        public Criteria andDeletedBetween(Boolean value1, Boolean value2) {
            addCriterion("deleted between", value1, value2, "deleted");
            return (Criteria) this;
        }

        /**
         * deleted Not Between
         *
         * @return
         */
        public Criteria andDeletedNotBetween(Boolean value1, Boolean value2) {
            addCriterion("deleted not between", value1, value2, "deleted");
            return (Criteria) this;
        }

        /**
         * registerDate Is Null
         *
         * @return
         */
        public Criteria andRegisterDateIsNull() {
            addCriterion("register_date is null");
            return (Criteria) this;
        }

        /**
         * registerDate IS Not Null
         *
         * @return
         */
        public Criteria andRegisterDateIsNotNull() {
            addCriterion("register_date is not null");
            return (Criteria) this;
        }

        /**
         * registerDate Equal
         *
         * @return
         */
        public Criteria andRegisterDateEqualTo(Date value) {
            addCriterion("register_date =", value, "registerDate");
            return (Criteria) this;
        }

        /**
         * registerDate Not Equal
         *
         * @return
         */
        public Criteria andRegisterDateNotEqualTo(Date value) {
            addCriterion("register_date <>", value, "registerDate");
            return (Criteria) this;
        }

        /**
         * registerDate Greater Than
         *
         * @return
         */
        public Criteria andRegisterDateGreaterThan(Date value) {
            addCriterion("register_date >", value, "registerDate");
            return (Criteria) this;
        }

        /**
         * registerDate Greater Than Or Equal To
         *
         * @return
         */
        public Criteria andRegisterDateGreaterThanOrEqualTo(Date value) {
            addCriterion("register_date >=", value, "registerDate");
            return (Criteria) this;
        }

        /**
         * registerDate Less Than
         *
         * @return
         */
        public Criteria andRegisterDateLessThan(Date value) {
            addCriterion("register_date <", value, "registerDate");
            return (Criteria) this;
        }

        /**
         * registerDate Less Than Or Equal To
         *
         * @return
         */
        public Criteria andRegisterDateLessThanOrEqualTo(Date value) {
            addCriterion("register_date <=", value, "registerDate");
            return (Criteria) this;
        }

        /**
         * registerDate In
         *
         * @return
         */
        public Criteria andRegisterDateIn(List<Date> values) {
            addCriterion("register_date in", values, "registerDate");
            return (Criteria) this;
        }

        /**
         * registerDate Not In
         *
         * @return
         */
        public Criteria andRegisterDateNotIn(List<Date> values) {
            addCriterion("register_date not in", values, "registerDate");
            return (Criteria) this;
        }

        /**
         * registerDate Between
         *
         * @return
         */
        public Criteria andRegisterDateBetween(Date value1, Date value2) {
            addCriterion("register_date between", value1, value2, "registerDate");
            return (Criteria) this;
        }

        /**
         * registerDate Not Between
         *
         * @return
         */
        public Criteria andRegisterDateNotBetween(Date value1, Date value2) {
            addCriterion("register_date not between", value1, value2, "registerDate");
            return (Criteria) this;
        }

        /**
         * preStartTime Is Null
         *
         * @return
         */
        public Criteria andPreStartTimeIsNull() {
            addCriterion("pre_start_time is null");
            return (Criteria) this;
        }

        /**
         * preStartTime IS Not Null
         *
         * @return
         */
        public Criteria andPreStartTimeIsNotNull() {
            addCriterion("pre_start_time is not null");
            return (Criteria) this;
        }

        /**
         * preStartTime Equal
         *
         * @return
         */
        public Criteria andPreStartTimeEqualTo(LocalDateTime value) {
            addCriterion("pre_start_time =", value, "preStartTime");
            return (Criteria) this;
        }

        /**
         * preStartTime Not Equal
         *
         * @return
         */
        public Criteria andPreStartTimeNotEqualTo(LocalDateTime value) {
            addCriterion("pre_start_time <>", value, "preStartTime");
            return (Criteria) this;
        }

        /**
         * preStartTime Greater Than
         *
         * @return
         */
        public Criteria andPreStartTimeGreaterThan(LocalDateTime value) {
            addCriterion("pre_start_time >", value, "preStartTime");
            return (Criteria) this;
        }

        /**
         * preStartTime Greater Than Or Equal To
         *
         * @return
         */
        public Criteria andPreStartTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("pre_start_time >=", value, "preStartTime");
            return (Criteria) this;
        }

        /**
         * preStartTime Less Than
         *
         * @return
         */
        public Criteria andPreStartTimeLessThan(LocalDateTime value) {
            addCriterion("pre_start_time <", value, "preStartTime");
            return (Criteria) this;
        }

        /**
         * preStartTime Less Than Or Equal To
         *
         * @return
         */
        public Criteria andPreStartTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("pre_start_time <=", value, "preStartTime");
            return (Criteria) this;
        }

        /**
         * preStartTime In
         *
         * @return
         */
        public Criteria andPreStartTimeIn(List<LocalDateTime> values) {
            addCriterion("pre_start_time in", values, "preStartTime");
            return (Criteria) this;
        }

        /**
         * preStartTime Not In
         *
         * @return
         */
        public Criteria andPreStartTimeNotIn(List<LocalDateTime> values) {
            addCriterion("pre_start_time not in", values, "preStartTime");
            return (Criteria) this;
        }

        /**
         * preStartTime Between
         *
         * @return
         */
        public Criteria andPreStartTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("pre_start_time between", value1, value2, "preStartTime");
            return (Criteria) this;
        }

        /**
         * preStartTime Not Between
         *
         * @return
         */
        public Criteria andPreStartTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("pre_start_time not between", value1, value2, "preStartTime");
            return (Criteria) this;
        }

        /**
         * exactMoney Is Null
         *
         * @return
         */
        public Criteria andExactMoneyIsNull() {
            addCriterion("exact_money is null");
            return (Criteria) this;
        }

        /**
         * exactMoney IS Not Null
         *
         * @return
         */
        public Criteria andExactMoneyIsNotNull() {
            addCriterion("exact_money is not null");
            return (Criteria) this;
        }

        /**
         * exactMoney Equal
         *
         * @return
         */
        public Criteria andExactMoneyEqualTo(BigDecimal value) {
            addCriterion("exact_money =", value, "exactMoney");
            return (Criteria) this;
        }

        /**
         * exactMoney Not Equal
         *
         * @return
         */
        public Criteria andExactMoneyNotEqualTo(BigDecimal value) {
            addCriterion("exact_money <>", value, "exactMoney");
            return (Criteria) this;
        }

        /**
         * exactMoney Greater Than
         *
         * @return
         */
        public Criteria andExactMoneyGreaterThan(BigDecimal value) {
            addCriterion("exact_money >", value, "exactMoney");
            return (Criteria) this;
        }

        /**
         * exactMoney Greater Than Or Equal To
         *
         * @return
         */
        public Criteria andExactMoneyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("exact_money >=", value, "exactMoney");
            return (Criteria) this;
        }

        /**
         * exactMoney Less Than
         *
         * @return
         */
        public Criteria andExactMoneyLessThan(BigDecimal value) {
            addCriterion("exact_money <", value, "exactMoney");
            return (Criteria) this;
        }

        /**
         * exactMoney Less Than Or Equal To
         *
         * @return
         */
        public Criteria andExactMoneyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("exact_money <=", value, "exactMoney");
            return (Criteria) this;
        }

        /**
         * exactMoney In
         *
         * @return
         */
        public Criteria andExactMoneyIn(List<BigDecimal> values) {
            addCriterion("exact_money in", values, "exactMoney");
            return (Criteria) this;
        }

        /**
         * exactMoney Not In
         *
         * @return
         */
        public Criteria andExactMoneyNotIn(List<BigDecimal> values) {
            addCriterion("exact_money not in", values, "exactMoney");
            return (Criteria) this;
        }

        /**
         * exactMoney Between
         *
         * @return
         */
        public Criteria andExactMoneyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("exact_money between", value1, value2, "exactMoney");
            return (Criteria) this;
        }

        /**
         * exactMoney Not Between
         *
         * @return
         */
        public Criteria andExactMoneyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("exact_money not between", value1, value2, "exactMoney");
            return (Criteria) this;
        }

        /**
         * englishMoney Is Null
         *
         * @return
         */
        public Criteria andEnglishMoneyIsNull() {
            addCriterion("english_money is null");
            return (Criteria) this;
        }

        /**
         * englishMoney IS Not Null
         *
         * @return
         */
        public Criteria andEnglishMoneyIsNotNull() {
            addCriterion("english_money is not null");
            return (Criteria) this;
        }

        /**
         * englishMoney Equal
         *
         * @return
         */
        public Criteria andEnglishMoneyEqualTo(Double value) {
            addCriterion("english_money =", value, "englishMoney");
            return (Criteria) this;
        }

        /**
         * englishMoney Not Equal
         *
         * @return
         */
        public Criteria andEnglishMoneyNotEqualTo(Double value) {
            addCriterion("english_money <>", value, "englishMoney");
            return (Criteria) this;
        }

        /**
         * englishMoney Greater Than
         *
         * @return
         */
        public Criteria andEnglishMoneyGreaterThan(Double value) {
            addCriterion("english_money >", value, "englishMoney");
            return (Criteria) this;
        }

        /**
         * englishMoney Greater Than Or Equal To
         *
         * @return
         */
        public Criteria andEnglishMoneyGreaterThanOrEqualTo(Double value) {
            addCriterion("english_money >=", value, "englishMoney");
            return (Criteria) this;
        }

        /**
         * englishMoney Less Than
         *
         * @return
         */
        public Criteria andEnglishMoneyLessThan(Double value) {
            addCriterion("english_money <", value, "englishMoney");
            return (Criteria) this;
        }

        /**
         * englishMoney Less Than Or Equal To
         *
         * @return
         */
        public Criteria andEnglishMoneyLessThanOrEqualTo(Double value) {
            addCriterion("english_money <=", value, "englishMoney");
            return (Criteria) this;
        }

        /**
         * englishMoney In
         *
         * @return
         */
        public Criteria andEnglishMoneyIn(List<Double> values) {
            addCriterion("english_money in", values, "englishMoney");
            return (Criteria) this;
        }

        /**
         * englishMoney Not In
         *
         * @return
         */
        public Criteria andEnglishMoneyNotIn(List<Double> values) {
            addCriterion("english_money not in", values, "englishMoney");
            return (Criteria) this;
        }

        /**
         * englishMoney Between
         *
         * @return
         */
        public Criteria andEnglishMoneyBetween(Double value1, Double value2) {
            addCriterion("english_money between", value1, value2, "englishMoney");
            return (Criteria) this;
        }

        /**
         * englishMoney Not Between
         *
         * @return
         */
        public Criteria andEnglishMoneyNotBetween(Double value1, Double value2) {
            addCriterion("english_money not between", value1, value2, "englishMoney");
            return (Criteria) this;
        }

        /**
         * frenchMoney Is Null
         *
         * @return
         */
        public Criteria andFrenchMoneyIsNull() {
            addCriterion("french_money is null");
            return (Criteria) this;
        }

        /**
         * frenchMoney IS Not Null
         *
         * @return
         */
        public Criteria andFrenchMoneyIsNotNull() {
            addCriterion("french_money is not null");
            return (Criteria) this;
        }

        /**
         * frenchMoney Equal
         *
         * @return
         */
        public Criteria andFrenchMoneyEqualTo(Float value) {
            addCriterion("french_money =", value, "frenchMoney");
            return (Criteria) this;
        }

        /**
         * frenchMoney Not Equal
         *
         * @return
         */
        public Criteria andFrenchMoneyNotEqualTo(Float value) {
            addCriterion("french_money <>", value, "frenchMoney");
            return (Criteria) this;
        }

        /**
         * frenchMoney Greater Than
         *
         * @return
         */
        public Criteria andFrenchMoneyGreaterThan(Float value) {
            addCriterion("french_money >", value, "frenchMoney");
            return (Criteria) this;
        }

        /**
         * frenchMoney Greater Than Or Equal To
         *
         * @return
         */
        public Criteria andFrenchMoneyGreaterThanOrEqualTo(Float value) {
            addCriterion("french_money >=", value, "frenchMoney");
            return (Criteria) this;
        }

        /**
         * frenchMoney Less Than
         *
         * @return
         */
        public Criteria andFrenchMoneyLessThan(Float value) {
            addCriterion("french_money <", value, "frenchMoney");
            return (Criteria) this;
        }

        /**
         * frenchMoney Less Than Or Equal To
         *
         * @return
         */
        public Criteria andFrenchMoneyLessThanOrEqualTo(Float value) {
            addCriterion("french_money <=", value, "frenchMoney");
            return (Criteria) this;
        }

        /**
         * frenchMoney In
         *
         * @return
         */
        public Criteria andFrenchMoneyIn(List<Float> values) {
            addCriterion("french_money in", values, "frenchMoney");
            return (Criteria) this;
        }

        /**
         * frenchMoney Not In
         *
         * @return
         */
        public Criteria andFrenchMoneyNotIn(List<Float> values) {
            addCriterion("french_money not in", values, "frenchMoney");
            return (Criteria) this;
        }

        /**
         * frenchMoney Between
         *
         * @return
         */
        public Criteria andFrenchMoneyBetween(Float value1, Float value2) {
            addCriterion("french_money between", value1, value2, "frenchMoney");
            return (Criteria) this;
        }

        /**
         * frenchMoney Not Between
         *
         * @return
         */
        public Criteria andFrenchMoneyNotBetween(Float value1, Float value2) {
            addCriterion("french_money not between", value1, value2, "frenchMoney");
            return (Criteria) this;
        }

        /**
         * koreanMoney Is Null
         *
         * @return
         */
        public Criteria andKoreanMoneyIsNull() {
            addCriterion("korean_money is null");
            return (Criteria) this;
        }

        /**
         * koreanMoney IS Not Null
         *
         * @return
         */
        public Criteria andKoreanMoneyIsNotNull() {
            addCriterion("korean_money is not null");
            return (Criteria) this;
        }

        /**
         * koreanMoney Equal
         *
         * @return
         */
        public Criteria andKoreanMoneyEqualTo(Short value) {
            addCriterion("korean_money =", value, "koreanMoney");
            return (Criteria) this;
        }

        /**
         * koreanMoney Not Equal
         *
         * @return
         */
        public Criteria andKoreanMoneyNotEqualTo(Short value) {
            addCriterion("korean_money <>", value, "koreanMoney");
            return (Criteria) this;
        }

        /**
         * koreanMoney Greater Than
         *
         * @return
         */
        public Criteria andKoreanMoneyGreaterThan(Short value) {
            addCriterion("korean_money >", value, "koreanMoney");
            return (Criteria) this;
        }

        /**
         * koreanMoney Greater Than Or Equal To
         *
         * @return
         */
        public Criteria andKoreanMoneyGreaterThanOrEqualTo(Short value) {
            addCriterion("korean_money >=", value, "koreanMoney");
            return (Criteria) this;
        }

        /**
         * koreanMoney Less Than
         *
         * @return
         */
        public Criteria andKoreanMoneyLessThan(Short value) {
            addCriterion("korean_money <", value, "koreanMoney");
            return (Criteria) this;
        }

        /**
         * koreanMoney Less Than Or Equal To
         *
         * @return
         */
        public Criteria andKoreanMoneyLessThanOrEqualTo(Short value) {
            addCriterion("korean_money <=", value, "koreanMoney");
            return (Criteria) this;
        }

        /**
         * koreanMoney In
         *
         * @return
         */
        public Criteria andKoreanMoneyIn(List<Short> values) {
            addCriterion("korean_money in", values, "koreanMoney");
            return (Criteria) this;
        }

        /**
         * koreanMoney Not In
         *
         * @return
         */
        public Criteria andKoreanMoneyNotIn(List<Short> values) {
            addCriterion("korean_money not in", values, "koreanMoney");
            return (Criteria) this;
        }

        /**
         * koreanMoney Between
         *
         * @return
         */
        public Criteria andKoreanMoneyBetween(Short value1, Short value2) {
            addCriterion("korean_money between", value1, value2, "koreanMoney");
            return (Criteria) this;
        }

        /**
         * koreanMoney Not Between
         *
         * @return
         */
        public Criteria andKoreanMoneyNotBetween(Short value1, Short value2) {
            addCriterion("korean_money not between", value1, value2, "koreanMoney");
            return (Criteria) this;
        }

        /**
         * encryptType Is Null
         *
         * @return
         */
        public Criteria andEncryptTypeIsNull() {
            addCriterion("encrypt_type is null");
            return (Criteria) this;
        }

        /**
         * encryptType IS Not Null
         *
         * @return
         */
        public Criteria andEncryptTypeIsNotNull() {
            addCriterion("encrypt_type is not null");
            return (Criteria) this;
        }

        /**
         * encryptType Equal
         *
         * @return
         */
        public Criteria andEncryptTypeEqualTo(EncryptTypeEnum value) {
            addCriterion("encrypt_type =", value, "encryptType");
            return (Criteria) this;
        }

        /**
         * encryptType Not Equal
         *
         * @return
         */
        public Criteria andEncryptTypeNotEqualTo(EncryptTypeEnum value) {
            addCriterion("encrypt_type <>", value, "encryptType");
            return (Criteria) this;
        }

        /**
         * encryptType Greater Than
         *
         * @return
         */
        public Criteria andEncryptTypeGreaterThan(EncryptTypeEnum value) {
            addCriterion("encrypt_type >", value, "encryptType");
            return (Criteria) this;
        }

        /**
         * encryptType Greater Than Or Equal To
         *
         * @return
         */
        public Criteria andEncryptTypeGreaterThanOrEqualTo(EncryptTypeEnum value) {
            addCriterion("encrypt_type >=", value, "encryptType");
            return (Criteria) this;
        }

        /**
         * encryptType Less Than
         *
         * @return
         */
        public Criteria andEncryptTypeLessThan(EncryptTypeEnum value) {
            addCriterion("encrypt_type <", value, "encryptType");
            return (Criteria) this;
        }

        /**
         * encryptType Less Than Or Equal To
         *
         * @return
         */
        public Criteria andEncryptTypeLessThanOrEqualTo(EncryptTypeEnum value) {
            addCriterion("encrypt_type <=", value, "encryptType");
            return (Criteria) this;
        }

        /**
         * encryptType In
         *
         * @return
         */
        public Criteria andEncryptTypeIn(List<EncryptTypeEnum> values) {
            addCriterion("encrypt_type in", values, "encryptType");
            return (Criteria) this;
        }

        /**
         * encryptType Not In
         *
         * @return
         */
        public Criteria andEncryptTypeNotIn(List<EncryptTypeEnum> values) {
            addCriterion("encrypt_type not in", values, "encryptType");
            return (Criteria) this;
        }

        /**
         * encryptType Between
         *
         * @return
         */
        public Criteria andEncryptTypeBetween(EncryptTypeEnum value1, EncryptTypeEnum value2) {
            addCriterion("encrypt_type between", value1, value2, "encryptType");
            return (Criteria) this;
        }

        /**
         * encryptType Not Between
         *
         * @return
         */
        public Criteria andEncryptTypeNotBetween(EncryptTypeEnum value1, EncryptTypeEnum value2) {
            addCriterion("encrypt_type not between", value1, value2, "encryptType");
            return (Criteria) this;
        }

        /**
         * markContentStatus Is Null
         *
         * @return
         */
        public Criteria andMarkContentStatusIsNull() {
            addCriterion("mark_content_status is null");
            return (Criteria) this;
        }

        /**
         * markContentStatus IS Not Null
         *
         * @return
         */
        public Criteria andMarkContentStatusIsNotNull() {
            addCriterion("mark_content_status is not null");
            return (Criteria) this;
        }

        /**
         * markContentStatus Equal
         *
         * @return
         */
        public Criteria andMarkContentStatusEqualTo(MarkContentStatusEnum value) {
            addCriterion("mark_content_status =", value, "markContentStatus");
            return (Criteria) this;
        }

        /**
         * markContentStatus Not Equal
         *
         * @return
         */
        public Criteria andMarkContentStatusNotEqualTo(MarkContentStatusEnum value) {
            addCriterion("mark_content_status <>", value, "markContentStatus");
            return (Criteria) this;
        }

        /**
         * markContentStatus Greater Than
         *
         * @return
         */
        public Criteria andMarkContentStatusGreaterThan(MarkContentStatusEnum value) {
            addCriterion("mark_content_status >", value, "markContentStatus");
            return (Criteria) this;
        }

        /**
         * markContentStatus Greater Than Or Equal To
         *
         * @return
         */
        public Criteria andMarkContentStatusGreaterThanOrEqualTo(MarkContentStatusEnum value) {
            addCriterion("mark_content_status >=", value, "markContentStatus");
            return (Criteria) this;
        }

        /**
         * markContentStatus Less Than
         *
         * @return
         */
        public Criteria andMarkContentStatusLessThan(MarkContentStatusEnum value) {
            addCriterion("mark_content_status <", value, "markContentStatus");
            return (Criteria) this;
        }

        /**
         * markContentStatus Less Than Or Equal To
         *
         * @return
         */
        public Criteria andMarkContentStatusLessThanOrEqualTo(MarkContentStatusEnum value) {
            addCriterion("mark_content_status <=", value, "markContentStatus");
            return (Criteria) this;
        }

        /**
         * markContentStatus In
         *
         * @return
         */
        public Criteria andMarkContentStatusIn(List<MarkContentStatusEnum> values) {
            addCriterion("mark_content_status in", values, "markContentStatus");
            return (Criteria) this;
        }

        /**
         * markContentStatus Not In
         *
         * @return
         */
        public Criteria andMarkContentStatusNotIn(List<MarkContentStatusEnum> values) {
            addCriterion("mark_content_status not in", values, "markContentStatus");
            return (Criteria) this;
        }

        /**
         * markContentStatus Between
         *
         * @return
         */
        public Criteria andMarkContentStatusBetween(MarkContentStatusEnum value1, MarkContentStatusEnum value2) {
            addCriterion("mark_content_status between", value1, value2, "markContentStatus");
            return (Criteria) this;
        }

        /**
         * markContentStatus Not Between
         *
         * @return
         */
        public Criteria andMarkContentStatusNotBetween(MarkContentStatusEnum value1, MarkContentStatusEnum value2) {
            addCriterion("mark_content_status not between", value1, value2, "markContentStatus");
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