package ru.vilonov.effective.mobile.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.sql.SQLException;
import java.util.Locale;
import java.util.NoSuchElementException;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class ExceptionHandlerController {
    private final MessageSource messageSource;

    @ExceptionHandler({NoSuchElementException.class, NoResourceFoundException.class})
    private ResponseEntity<ProblemDetail> handleNotFound(NoSuchElementException exception, Locale locale) {
        log.warn("handleNotFound catch exception ");
        String message = this.messageSource
                .getMessage(exception.getMessage(), new Object[0], exception.getMessage(),locale);
        return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, message))
                .build();
    }

    @ExceptionHandler({SQLException.class, MethodArgumentNotValidException.class})
    private ResponseEntity<ProblemDetail> handleSqlException(SQLException exception, Locale locale) {
        log.warn("handleSqlException catch exception ");
        String message = this.messageSource
                .getMessage(exception.getMessage(), new Object[0], exception.getMessage(),locale);
        return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, message))
                .build();
    }

}
