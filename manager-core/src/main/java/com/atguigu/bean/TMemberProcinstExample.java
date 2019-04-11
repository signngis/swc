package com.atguigu.bean;

import java.util.ArrayList;
import java.util.List;

public class TMemberProcinstExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TMemberProcinstExample() {
        oredCriteria = new ArrayList<Criteria>();
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
            criteria = new ArrayList<Criterion>();
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

        public Criteria andMemberidIsNull() {
            addCriterion("memberid is null");
            return (Criteria) this;
        }

        public Criteria andMemberidIsNotNull() {
            addCriterion("memberid is not null");
            return (Criteria) this;
        }

        public Criteria andMemberidEqualTo(Integer value) {
            addCriterion("memberid =", value, "memberid");
            return (Criteria) this;
        }

        public Criteria andMemberidNotEqualTo(Integer value) {
            addCriterion("memberid <>", value, "memberid");
            return (Criteria) this;
        }

        public Criteria andMemberidGreaterThan(Integer value) {
            addCriterion("memberid >", value, "memberid");
            return (Criteria) this;
        }

        public Criteria andMemberidGreaterThanOrEqualTo(Integer value) {
            addCriterion("memberid >=", value, "memberid");
            return (Criteria) this;
        }

        public Criteria andMemberidLessThan(Integer value) {
            addCriterion("memberid <", value, "memberid");
            return (Criteria) this;
        }

        public Criteria andMemberidLessThanOrEqualTo(Integer value) {
            addCriterion("memberid <=", value, "memberid");
            return (Criteria) this;
        }

        public Criteria andMemberidIn(List<Integer> values) {
            addCriterion("memberid in", values, "memberid");
            return (Criteria) this;
        }

        public Criteria andMemberidNotIn(List<Integer> values) {
            addCriterion("memberid not in", values, "memberid");
            return (Criteria) this;
        }

        public Criteria andMemberidBetween(Integer value1, Integer value2) {
            addCriterion("memberid between", value1, value2, "memberid");
            return (Criteria) this;
        }

        public Criteria andMemberidNotBetween(Integer value1, Integer value2) {
            addCriterion("memberid not between", value1, value2, "memberid");
            return (Criteria) this;
        }

        public Criteria andProcinstidIsNull() {
            addCriterion("procinstid is null");
            return (Criteria) this;
        }

        public Criteria andProcinstidIsNotNull() {
            addCriterion("procinstid is not null");
            return (Criteria) this;
        }

        public Criteria andProcinstidEqualTo(String value) {
            addCriterion("procinstid =", value, "procinstid");
            return (Criteria) this;
        }

        public Criteria andProcinstidNotEqualTo(String value) {
            addCriterion("procinstid <>", value, "procinstid");
            return (Criteria) this;
        }

        public Criteria andProcinstidGreaterThan(String value) {
            addCriterion("procinstid >", value, "procinstid");
            return (Criteria) this;
        }

        public Criteria andProcinstidGreaterThanOrEqualTo(String value) {
            addCriterion("procinstid >=", value, "procinstid");
            return (Criteria) this;
        }

        public Criteria andProcinstidLessThan(String value) {
            addCriterion("procinstid <", value, "procinstid");
            return (Criteria) this;
        }

        public Criteria andProcinstidLessThanOrEqualTo(String value) {
            addCriterion("procinstid <=", value, "procinstid");
            return (Criteria) this;
        }

        public Criteria andProcinstidLike(String value) {
            addCriterion("procinstid like", value, "procinstid");
            return (Criteria) this;
        }

        public Criteria andProcinstidNotLike(String value) {
            addCriterion("procinstid not like", value, "procinstid");
            return (Criteria) this;
        }

        public Criteria andProcinstidIn(List<String> values) {
            addCriterion("procinstid in", values, "procinstid");
            return (Criteria) this;
        }

        public Criteria andProcinstidNotIn(List<String> values) {
            addCriterion("procinstid not in", values, "procinstid");
            return (Criteria) this;
        }

        public Criteria andProcinstidBetween(String value1, String value2) {
            addCriterion("procinstid between", value1, value2, "procinstid");
            return (Criteria) this;
        }

        public Criteria andProcinstidNotBetween(String value1, String value2) {
            addCriterion("procinstid not between", value1, value2, "procinstid");
            return (Criteria) this;
        }

        public Criteria andProctypeIsNull() {
            addCriterion("proctype is null");
            return (Criteria) this;
        }

        public Criteria andProctypeIsNotNull() {
            addCriterion("proctype is not null");
            return (Criteria) this;
        }

        public Criteria andProctypeEqualTo(String value) {
            addCriterion("proctype =", value, "proctype");
            return (Criteria) this;
        }

        public Criteria andProctypeNotEqualTo(String value) {
            addCriterion("proctype <>", value, "proctype");
            return (Criteria) this;
        }

        public Criteria andProctypeGreaterThan(String value) {
            addCriterion("proctype >", value, "proctype");
            return (Criteria) this;
        }

        public Criteria andProctypeGreaterThanOrEqualTo(String value) {
            addCriterion("proctype >=", value, "proctype");
            return (Criteria) this;
        }

        public Criteria andProctypeLessThan(String value) {
            addCriterion("proctype <", value, "proctype");
            return (Criteria) this;
        }

        public Criteria andProctypeLessThanOrEqualTo(String value) {
            addCriterion("proctype <=", value, "proctype");
            return (Criteria) this;
        }

        public Criteria andProctypeLike(String value) {
            addCriterion("proctype like", value, "proctype");
            return (Criteria) this;
        }

        public Criteria andProctypeNotLike(String value) {
            addCriterion("proctype not like", value, "proctype");
            return (Criteria) this;
        }

        public Criteria andProctypeIn(List<String> values) {
            addCriterion("proctype in", values, "proctype");
            return (Criteria) this;
        }

        public Criteria andProctypeNotIn(List<String> values) {
            addCriterion("proctype not in", values, "proctype");
            return (Criteria) this;
        }

        public Criteria andProctypeBetween(String value1, String value2) {
            addCriterion("proctype between", value1, value2, "proctype");
            return (Criteria) this;
        }

        public Criteria andProctypeNotBetween(String value1, String value2) {
            addCriterion("proctype not between", value1, value2, "proctype");
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