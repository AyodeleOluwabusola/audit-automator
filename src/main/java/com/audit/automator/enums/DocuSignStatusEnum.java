package com.audit.automator.enums;

import lombok.Getter;

@Getter
public enum DocuSignStatusEnum {

    CREATED("Crated"),
    DELIVERED("Delivered"),
    SENT("Sent"),
    COMPLETED("Completed"),
    ;

    DocuSignStatusEnum(String name) {
        this.name = name;
    }

    private String name;
}
