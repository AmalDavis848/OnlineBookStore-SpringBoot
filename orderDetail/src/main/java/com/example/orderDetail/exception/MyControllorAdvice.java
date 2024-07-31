package com.example.orderDetail.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class MyControllorAdvice {


    @ExceptionHandler(InvalidStudentIdException.class)
    public ResponseEntity<CustomErrorResponse> handleInvalidStudentIdException(InvalidStudentIdException ex) {
        CustomErrorResponse errorResponse = new CustomErrorResponse(LocalDateTime.now(), processMessage(ex.getMessage()), "Invalid Student ID");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidBookIdException.class)
    public ResponseEntity<CustomErrorResponse> handleInvalidBookIdException(InvalidBookIdException ex) {
        CustomErrorResponse errorResponse = new CustomErrorResponse(LocalDateTime.now(), processMessage(ex.getMessage()), "Invalid Book ID");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BookUnavailableException.class)
    public ResponseEntity<CustomErrorResponse> handleBookUnavailableException(BookUnavailableException ex) {
        CustomErrorResponse errorResponse = new CustomErrorResponse(LocalDateTime.now(), processMessage(ex.getMessage()), "Book Unavailable");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        CustomErrorResponse errorResponse = new CustomErrorResponse(LocalDateTime.now(), processMessage(ex.getMessage()), "Resource Not Found");
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND); // Return 404 Not Found
    }

    @ExceptionHandler(RunTimeBookException.class)
    public ResponseEntity<CustomErrorResponse> handleRunTimeBookException(RunTimeBookException ex) {
        CustomErrorResponse errorResponse = new CustomErrorResponse(LocalDateTime.now(), processMessage(ex.getMessage()), "Runtime Book Error");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomErrorResponse> handleGeneralException(Exception ex) {
        CustomErrorResponse errorResponse = new CustomErrorResponse(LocalDateTime.now(), processMessage("Internal Server Error"), "Unknown Error");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String processMessage(String message) {
        return message.replaceAll("\\r\\n|\\r|\\n", " ");
    }
}


