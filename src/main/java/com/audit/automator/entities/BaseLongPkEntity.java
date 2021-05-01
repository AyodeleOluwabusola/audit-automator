package com.audit.automator.entities;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@ToString(callSuper=true)
@MappedSuperclass
public class BaseLongPkEntity extends AbstractBasePkEntity<Long> {

    /**
     *
     */
    private static final long serialVersionUID = 6038267027278614236L;

    @Id
    @GeneratedValue
    @Column(name = "PK", nullable = false, insertable = true, updatable = false)
    private Long pk;

    protected BaseLongPkEntity() {
    }

}
