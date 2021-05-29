package com.audit.automator.repository;


import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.SingularAttribute;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ExtraQueryParam<T> implements Serializable {
    private static final long serialVersionUID = 4805518179122197752L;
    private Integer maxResult;
    private Integer firstResult;
    private Map<SingularAttribute<? super T, ?>, ExtraQueryParam.ParamOrder> orderParams;
    private Set<Attribute<? super T, ?>> eagerLoadedAttributes;
    private Map<String, Object> hints;
    private Set<Attribute<? super T, ?>> groupByAttributes;

    public ExtraQueryParam() {
    }

    public ExtraQueryParam.ParamOrder addOrderParam(SingularAttribute<? super T, ?> singularAttribute, ExtraQueryParam.ParamOrder paramOrder) {
        if (this.orderParams == null) {
            this.orderParams = new HashMap();
        }

        return (ExtraQueryParam.ParamOrder)this.orderParams.put(singularAttribute, paramOrder);
    }

    public boolean addEagerLoadedAttribute(Attribute<? super T, ?> attribute) {
        if (this.eagerLoadedAttributes == null) {
            this.eagerLoadedAttributes = new HashSet();
        }

        return this.eagerLoadedAttributes.add(attribute);
    }

    public Object addHint(String hint, Object value) {
        if (this.hints == null) {
            this.hints = new HashMap();
        }

        return this.hints.put(hint, value);
    }

    public boolean addGroupByAttribute(Attribute<? super T, ?> attribute) {
        if (this.groupByAttributes == null) {
            this.groupByAttributes = new HashSet();
        }

        return this.groupByAttributes.add(attribute);
    }

    public Integer getMaxResult() {
        return this.maxResult;
    }

    public Integer getFirstResult() {
        return this.firstResult;
    }

    public Map<SingularAttribute<? super T, ?>, ExtraQueryParam.ParamOrder> getOrderParams() {
        return this.orderParams;
    }

    public Set<Attribute<? super T, ?>> getEagerLoadedAttributes() {
        return this.eagerLoadedAttributes;
    }

    public Map<String, Object> getHints() {
        return this.hints;
    }

    public Set<Attribute<? super T, ?>> getGroupByAttributes() {
        return this.groupByAttributes;
    }

    public void setMaxResult(Integer maxResult) {
        this.maxResult = maxResult;
    }

    public void setFirstResult(Integer firstResult) {
        this.firstResult = firstResult;
    }

    public void setOrderParams(Map<SingularAttribute<? super T, ?>, ExtraQueryParam.ParamOrder> orderParams) {
        this.orderParams = orderParams;
    }

    public void setEagerLoadedAttributes(Set<Attribute<? super T, ?>> eagerLoadedAttributes) {
        this.eagerLoadedAttributes = eagerLoadedAttributes;
    }

    public void setHints(Map<String, Object> hints) {
        this.hints = hints;
    }

    public void setGroupByAttributes(Set<Attribute<? super T, ?>> groupByAttributes) {
        this.groupByAttributes = groupByAttributes;
    }

    public String toString() {
        return "ExtraQueryParam(maxResult=" + this.getMaxResult() + ", firstResult=" + this.getFirstResult() + ", orderParams=" + this.getOrderParams() + ", eagerLoadedAttributes=" + this.getEagerLoadedAttributes() + ", hints=" + this.getHints() + ", groupByAttributes=" + this.getGroupByAttributes() + ")";
    }

    public static enum ParamOrder {
        ASC,
        DESC;

        private ParamOrder() {
        }
    }
}

