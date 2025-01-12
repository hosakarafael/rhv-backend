package com.rafaelhosaka.rhv.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {
    private String message = "";
    private ErrorCode errorCode = ErrorCode.AS_SUCCESS;

    public Response(String message){
        this.message = message;
    }
}
