package com.audit.automator.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CONFIG")
@Getter
@Setter
public class Configuration extends BaseLongPkEntity{

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "VALUE", nullable = false, length = 512)
    private String value;
}
