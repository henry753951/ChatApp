package com.chatapp.backend.entity;

import org.springframework.data.mongodb.core.aggregation.VariableOperators.Map;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL) // 若參數是 null 則不顯示
public class BaseResponse<T> {
    public Integer code = HttpStatus.OK.value();
    public String msg;
    public Boolean success = true;
    public String errorMessage;
    public T data;


    public BaseResponse() {
    }

    public BaseResponse(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public BaseResponse(String msg) {
        this.msg = msg;
    }

    public BaseResponse(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public void setError(String errorMessage) {
        this.code = HttpStatus.INTERNAL_SERVER_ERROR.value();
        this.errorMessage = errorMessage;
        this.success = false;
    }

}