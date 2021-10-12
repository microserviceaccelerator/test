package com.deloitte.accelerator.web;

import com.deloitte.accelerator.web.exception.ErrorResponse;
import com.deloitte.accelerator.web.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
@Slf4j
public class ControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)  // 400
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResponse handleIllegalArgumentException(final IllegalArgumentException ex) {
        log.error("Exception: ", ex);
        return ErrorResponse.builder()
            .message("Illegal Argument Exception: " + ex.getMessage())
            .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)  // 404
    @ExceptionHandler(ResourceNotFoundException.class)
    public ErrorResponse handleResourceNotFoundException(final ResourceNotFoundException ex) {
        log.error("Exception: ", ex);
        return ErrorResponse.builder()
                .message("Resource not found Exception: " + ex.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)  // 405
    @ExceptionHandler(UnsupportedOperationException.class)
    public ErrorResponse handleUnsupportedOperationException(final UnsupportedOperationException ex) {
        log.error("Exception: ", ex);
        return ErrorResponse.builder()
            .message("Unsupported Operation Exception: " + ex.getMessage())
            .build();
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)  // 405
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ErrorResponse handleHttpRequestMethodNotSupportedException(final HttpRequestMethodNotSupportedException ex) {
        log.error("Exception: ", ex);
        return ErrorResponse.builder()
            .message("HTTP Request Not Supported Exception: " + ex.getMessage())
            .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)  // 400
    @ExceptionHandler({BindException.class, ServletRequestBindingException.class})
    public ErrorResponse handleHttpRequestMethodNotSupportedException(final Exception ex) {
        log.error("Exception: ", ex);
        return ErrorResponse.builder()
                .message("Validation Exception: " + ex.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)  // 400
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ErrorResponse handleMethodArgumentTypeMismatchException(final MethodArgumentTypeMismatchException ex) {
        log.error("Exception: ", ex);
        return ErrorResponse.builder()
                .message("Validation Exception: " + ex.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)  // 400
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ErrorResponse handleHttpMessageNotReadableException(final HttpMessageNotReadableException ex) {
        log.error("Exception: ", ex);
        return ErrorResponse.builder()
                .message("Invalid request body: " + ex.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)  // 400
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("Exception: ", ex);
        return ErrorResponse.builder()
                .message("Invalid method argument(s): " + ex.getMessage())
                .build();
    }


    /**
     * This will handle exceptions that are not mapped.  If one is encountered then it should be added
     * above and handled specifically.  If this ever gets test coverage then it should be investigated.
     *
     * @param ex The exception
     * @return The error response object
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponse handleException(final Exception ex) {
        log.error("Exception: ", ex);
        return ErrorResponse.builder()
            .message("Exception: " + ex.getMessage())
            .build();
    }
}
