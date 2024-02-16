package com.AlTaraf.Booking.Exception;

public class DuplicateUserException extends RuntimeException {
    public DuplicateUserException(String message) {
        super(message);
    }
}
