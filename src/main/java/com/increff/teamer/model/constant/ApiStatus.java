package com.increff.teamer.model.constant;

import org.springframework.http.HttpRequest;
import org.springframework.web.client.HttpStatusCodeException;

public enum ApiStatus {
    AUTH_ERROR,
    BAD_DATA,
    RESOURCE_EXISTS,
    NOT_FOUND,
    REMOTE_ERROR,
    UNKNOWN_ERROR,

    UNAUTHORIZED;

    private ApiStatus() {
    }
}
