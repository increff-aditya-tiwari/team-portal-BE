package com.increff.teamer.exception;

import com.increff.teamer.model.constant.ApiStatus;
import org.springframework.http.HttpStatus;

public class CommonApiException extends Exception{
    private HttpStatus status;

    public CommonApiException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public CommonApiException(HttpStatus status) {
        this.status = status;
    }

    public HttpStatus getStatus() {
        return this.status;
    }
}
