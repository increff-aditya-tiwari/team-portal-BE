package com.increff.teamer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CommonApiException.class)
    public ResponseEntity<ApiErrorResponse> handleCommonApiException(CommonApiException ex) {
        ApiErrorResponse errorResponse = new ApiErrorResponse(
                ex.getStatus(),
                ex.getMessage()
        );
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }

}
