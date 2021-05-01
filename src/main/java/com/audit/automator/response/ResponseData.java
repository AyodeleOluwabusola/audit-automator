package com.audit.automator.response;

import java.io.Serializable;

public class ResponseData implements Serializable {
    private static final long serialVersionUID = -8159409134154100174L;
    private Integer code;
    private String description;

    public ResponseData() {
    }

    public ResponseData(IResponseCodeEnum responseCodeEnum) {
        this.handleAssignCode(responseCodeEnum);
    }

    public void assignCode(IResponseCodeEnum responseCodeEnum) {
        this.handleAssignCode(responseCodeEnum);
    }

    private void handleAssignCode(IResponseCodeEnum responseCodeEnum) {
        if (responseCodeEnum != null) {
            this.code = responseCodeEnum.getCode();
            this.description = responseCodeEnum.getDescription();
        }

    }

    public Integer getCode() {
        return this.code;
    }

    public String getDescription() {
        return this.description;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String toString() {
        return "ResponseData(code=" + this.getCode() + ", description=" + this.getDescription() + ")";
    }
}