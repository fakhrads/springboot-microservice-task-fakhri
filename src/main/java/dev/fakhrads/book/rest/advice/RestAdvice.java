package dev.fakhrads.book.rest.advice;

import dev.fakhrads.book.exception.NotFoundException;
import dev.fakhrads.book.dto.DtoHelper;
import dev.fakhrads.book.dto.DtoResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestAdvice {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<DtoResponse<Object>> handleNotFound(NotFoundException ex, HttpServletRequest req) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(DtoHelper.fail(ex.getMessage(), null, req.getRequestURI()));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public ResponseEntity<DtoResponse<Object>> handleValidation(Exception ex, HttpServletRequest req) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(DtoHelper.fail("Validation error", null, req.getRequestURI()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<DtoResponse<Object>> handleConstraint(DataIntegrityViolationException ex, HttpServletRequest req) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(DtoHelper.fail("Data constraint violation (maybe duplicate ISBN)", null, req.getRequestURI()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<DtoResponse<Object>> handleAny(Exception ex, HttpServletRequest req) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(DtoHelper.fail("Unexpected error", null, req.getRequestURI()));
    }
}
