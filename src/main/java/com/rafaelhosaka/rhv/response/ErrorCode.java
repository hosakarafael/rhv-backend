package com.rafaelhosaka.rhv.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ErrorCode {
    AS_SUCCESS("AS000"),
    AS_BAD_CREDENTIALS("AS001"),
    AS_EMAIL_EMPTY("AS002"),
    AS_PASSWORD_EMPTY("AS003"),
    AS_DUPLICATE_EMAIL("AS004"),
    AS_NAME_EMPTY("AS005"),
    AS_EXCEPTION("AS100"),

    US_SUCCESS("US000"),
    US_ENTITY_NOT_FOUND("US001"),
    US_UPLOAD_FAILED("US002"),
    US_EXCEPTION("US100"),

    VS_SUCCESS("VS000"),
    VS_ENTITY_NOT_FOUND("VS001"),
    VS_FORBIDDEN_SUBJECT("VS002"),
    VS_USER_ID_NULL("VS003"),
    VS_TITLE_EMPTY("VS004"),
    VS_TITLE_LENGTH("VS005"),
    VS_DESCRIPTION_LENGTH("VS006"),
    VS_UPLOAD_FAILED("VS007"),
    VS_EXCEPTION("VS100");

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
        return AS_SUCCESS;
    }
}
