package com.audit.automator.entities;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.Date;

@StaticMetamodel(AbstractBasePkEntity.class)
public class AbstractBasePkEntity_ {
    public static volatile SingularAttribute<AbstractBasePkEntity, Boolean> active;
    public static volatile SingularAttribute<AbstractBasePkEntity, Boolean> deleted;
    public static volatile SingularAttribute<AbstractBasePkEntity, Date> createDate;
    public static volatile SingularAttribute<AbstractBasePkEntity, Date> lastModified;

    public AbstractBasePkEntity_() {
    }
}