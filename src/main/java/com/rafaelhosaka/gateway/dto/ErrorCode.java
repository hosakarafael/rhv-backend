package com.rafaelhosaka.gateway.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ErrorCode {
    DEFAULT("AS000"),
    BAD_CREDENTIALS("AS001"),
    EMAIL_EMPTY("AS002"),
    PASSWORD_EMPTY("AS003"),
    EXCEPTION("AS100");

    private final String code;

    ErrorCode(String code) {
        this.code = code;
    }

    @JsonValue
    public String getCode() {
        return code;
    }

    @JsonCreator
    public static ErrorCode fromCode(String code) {
        for (ErrorCode errorCode : values()) {
            if (errorCode.code.equals(code)) {
                return errorCode;
            }
        }
        return DEFAULT;
    }
}
