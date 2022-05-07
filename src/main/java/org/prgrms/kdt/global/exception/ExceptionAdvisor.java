package org.prgrms.kdt.global.exception;

import org.prgrms.kdt.domain.book.exception.BookException;
import org.prgrms.kdt.domain.book.exception.ItemException;
import org.prgrms.kdt.domain.order.exception.OrderException;
import org.prgrms.kdt.domain.user.exception.UserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionAdvisor{

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorResponse> handleUserException(UserException e) {
        int statusCode = e.getStatusCode();
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.resolve(statusCode));
    }

    @ExceptionHandler(OrderException.class)
    public ResponseEntity<ErrorResponse> handleOrderException(OrderException e) {
        int statusCode = e.getStatusCode();
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.resolve(statusCode));
    }

    @ExceptionHandler(BookException.class)
    public ResponseEntity<ErrorResponse> handleBookException(BookException e) {
        int statusCode = e.getStatusCode();
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.resolve(statusCode));
    }

    @ExceptionHandler(ItemException.class)
    public ResponseEntity<ErrorResponse> handleItemException(ItemException e) {
        int statusCode = e.getStatusCode();
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.resolve(statusCode));
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> handleBindException(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        String msg = bindingResult.getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.joining(" "));
        ErrorResponse errorResponse = new ErrorResponse(msg);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleArgumentException(IllegalArgumentException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}

