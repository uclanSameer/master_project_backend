package com.example.neighbour.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class ErrorResponseException extends ResponseStatusException {

    public ErrorResponseException(int httpStatusCode, String message) {
        super(HttpStatusCode.valueOf(httpStatusCode), message);
    }

    public ErrorResponseException(int httpStatusCode, String message, Throwable cause) {
        super(HttpStatusCode.valueOf(httpStatusCode), message, cause);
    }

}
