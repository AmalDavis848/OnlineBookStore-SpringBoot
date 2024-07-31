package com.example.orderDetail.controllor;

import com.example.orderDetail.dto.OrderDetailDto;
import com.example.orderDetail.dto.StudentBookDto;
import com.example.orderDetail.exception.*;
import com.example.orderDetail.service.OrderService;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderControllor {
    @Autowired
    private OrderService orderService;

    @PostMapping("/borrow")
    public ResponseEntity<?> borrowBook(@RequestParam Long studentId, @RequestParam Long bookId) {
        try {
            OrderDetailDto orderDetailDto = orderService.borrowBook(studentId, bookId);
            return new ResponseEntity<>(orderDetailDto, HttpStatus.OK);
        } catch (InvalidStudentIdException e) {
            return new ResponseEntity<>(new CustomErrorResponse(LocalDateTime.now(), processMessage(e.getMessage()), "Invalid Student ID Error"), HttpStatus.NOT_FOUND);
        } catch (InvalidBookIdException e) {
            return new ResponseEntity<>(new CustomErrorResponse(LocalDateTime.now(), processMessage(e.getMessage()), "Invalid Book ID Error"), HttpStatus.NOT_FOUND);
        } catch (BookUnavailableException e) {
            return new ResponseEntity<>(new CustomErrorResponse(LocalDateTime.now(), processMessage(e.getMessage()), "Book Unavailable Error"), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomErrorResponse(LocalDateTime.now(), processMessage("Not a valid Id"), "Not Found Error"), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/return")
    public ResponseEntity<?> returnBook(@RequestParam Long orderId) {
        try {
            OrderDetailDto orderDetailDto = orderService.returnBook(orderId);
            return new ResponseEntity<>(orderDetailDto, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(new CustomErrorResponse(LocalDateTime.now(), processMessage(e.getMessage()), "Resource Not Found"), HttpStatus.NOT_FOUND);
        } catch (RunTimeBookException e) {
            return new ResponseEntity<>(new CustomErrorResponse(LocalDateTime.now(), processMessage(e.getMessage()), "Runtime Book Error"), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomErrorResponse(LocalDateTime.now(), processMessage("Internal Server Error"), "Unknown Error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllOrders() {
        try {
            List<OrderDetailDto> orders = orderService.getAllOrders();
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomErrorResponse(LocalDateTime.now(), processMessage("Internal Server Error"), "Unknown Error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/student")
    public ResponseEntity<?> getAllStudentOrders() {
        try {
            List<StudentBookDto> studentOrders = orderService.getAllStudentBooks();
            return new ResponseEntity<>(studentOrders, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomErrorResponse(LocalDateTime.now(), processMessage("Internal Server Error"), "Unknown Error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

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
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RunTimeBookException.class)
    public ResponseEntity<CustomErrorResponse> handleRunTimeBookException(RunTimeBookException ex) {
        CustomErrorResponse errorResponse = new CustomErrorResponse(LocalDateTime.now(), processMessage(ex.getMessage()), "Runtime Book Error");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    private String processMessage(String message) {
        return message.replaceAll("\\r\\n|\\r|\\n", " ");
    }







}