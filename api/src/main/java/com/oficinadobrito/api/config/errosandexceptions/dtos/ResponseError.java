package com.oficinadobrito.api.config.errosandexceptions.dtos;

public class ResponseError{

    private String reason;
    private String description;

    public ResponseError() {
    }

    public ResponseError(String reason) {
        this.reason = reason;
    }

    public ResponseError(String reason, String description) {
        this.reason = reason;
        this.description = description;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
