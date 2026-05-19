package com.example.capstone2.Advice;

import com.example.capstone2.Api.ApiException;
import com.example.capstone2.Api.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<?> apiException (ApiException apiException ){
        String message = apiException.getMessage();
        return ResponseEntity.status(400).body(new ApiResponse(message));
    }
    @ExceptionHandler (value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse>
    MethodArgumentNotValidException (MethodArgumentNotValidException e) {
        String msg = e.getFieldError().getDefaultMessage();
        return ResponseEntity.status(400).body(new ApiResponse(msg)) ;
    }
    @ExceptionHandler (value = SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ApiResponse> SQLIntegrityConstraintViolationException (SQLIntegrityConstraintViolationException  e) {
        String msg=e.getMessage () ;
        return ResponseEntity.status (400) .body (new ApiResponse (msg)) ;}
    @ExceptionHandler (value = HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse>
    HttpRequestMethodNotSupportedException (HttpRequestMethodNotSupportedException e) {
        String msg = e. getMessage () ;
        return ResponseEntity. status (400) .body (new ApiResponse (msg) ) ;}
    @ExceptionHandler (value = MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse>
    MethodArgumentTypeMismatchException (MethodArgumentTypeMismatchException e) {
        String msg = e. getMessage () ;
        return ResponseEntity. status (400) .body (new ApiResponse (msg) ) ; }
    
}
