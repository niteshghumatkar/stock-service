package com.code4info.stockservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class APIExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({StockNotFoundException.class})
    public ResponseEntity<Object> handleStockNotFoundException(StockNotFoundException ex, WebRequest req){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "Stock Not Found");
        body.put("exception",ex);
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

}
