package br.com.productmanagement.config;

import br.com.productmanagement.core.exception.InvalidCategoryExecption;
import br.com.productmanagement.core.exception.InvalidProductException;
import br.com.productmanagement.core.exception.ProductNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleProductNotFoundException(ProductNotFoundException ex, HttpServletRequest request) {
        Map<String, Object> body = extractErrorInfo(ex, request);
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidProductException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidProductException(InvalidProductException ex, HttpServletRequest request) {
        Map<String, Object> body = extractErrorInfo(ex, request);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidCategoryExecption.class)
    public ResponseEntity<Map<String, Object>> handleInvalidCategoryException(InvalidCategoryExecption ex, HttpServletRequest request) {
        Map<String, Object> body = extractErrorInfo(ex, request);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    private static Map<String, Object> extractErrorInfo(Exception ex, HttpServletRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("date", LocalDateTime.now());
        body.put("error", ex.getMessage());
        body.put("path", request.getRequestURI());
        body.put("method", request.getMethod());
        return body;
    }
}
