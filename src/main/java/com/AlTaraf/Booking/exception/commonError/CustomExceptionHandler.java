package com.AlTaraf.Booking.exception.commonError;

import com.AlTaraf.Booking.payload.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.converter.HttpMessageNotReadableException;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        // Get the root cause of the exception
        Throwable cause = ex.getRootCause();

        // Customize the error message based on the root cause
        String errorMessage = "Bad Request: Invalid request body";
        if (cause != null) {
            errorMessage += ". Reason: " + cause.getMessage();
        }

        // Construct the response
        ApiResponse response = new ApiResponse(400, errorMessage);
        return ResponseEntity.badRequest().body(response);
    }
}