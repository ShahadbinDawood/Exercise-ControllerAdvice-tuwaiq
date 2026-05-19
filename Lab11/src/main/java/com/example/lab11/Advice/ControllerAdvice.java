package com.example.lab11.Advice;

import com.example.lab11.Api.ApiException;
import com.example.lab11.Api.ApiResponse;
import org.hibernate.StaleObjectStateException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<?> ApiException (ApiException apiException){
        String message = apiException.getMessage();
        return ResponseEntity.status(400).body(new ApiResponse(message));
    }
    @ExceptionHandler(StaleObjectStateException.class)
    public ResponseEntity<?> ApiException(StaleObjectStateException apiException){
        String message = apiException.getMessage();
        return ResponseEntity.status(400).body(new ApiResponse(message));
    }

}
