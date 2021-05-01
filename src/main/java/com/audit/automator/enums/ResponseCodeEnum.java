package com.audit.automator.enums;

import com.audit.automator.response.IResponseCodeEnum;

public enum ResponseCodeEnum implements IResponseCodeEnum {
    SUCCESS(0, "Success"),
    ERROR(-1, "Unable to process your request."),
    EXCEPTION(-2, "Exception thrown during processing of request."),
    ;

    private final int code;
    private final String description;

    private ResponseCodeEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
