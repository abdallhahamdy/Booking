package com.AlTaraf.Booking.exception;

public enum ErrorCodes {
    DUPLICATE_USER("100", "User with the same email, phone number, and role already exists.");

    private String code;
    private String desc;

    ErrorCodes(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }
}
