package com.audit.automator.entities;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(BaseLongPkEntity.class)
public class BaseLongPkEntity_ extends AbstractBasePkEntity_ {
    public static volatile SingularAttribute<BaseLongPkEntity, Long> pk;

    public BaseLongPkEntity_() {
    }
}
