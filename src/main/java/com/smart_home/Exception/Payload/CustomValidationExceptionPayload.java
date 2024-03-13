package com.smart_home.Exception.Payload;

public class CustomValidationExceptionPayload {
    private final String message;

    public CustomValidationExceptionPayload(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
