package com.nissan.car.dealership.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotCreatedException.class)
    public final ResponseEntity<Object> handleResourceNotCreatedException(ResourceNotCreatedException ex,
                                                                          WebRequest request) {
        Map<String, String> details = new HashMap<>();
        details.put("error", ex.getLocalizedMessage());

        ErrorResponse errorResponse = new ErrorResponse();
        buildErrorResponse(errorResponse, details, HttpStatus.BAD_REQUEST, "Resource not created or already exists");

        return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<Object> handleNotFoundException(ResourceNotFoundException ex,
                                                                WebRequest request) {
        Map<String, String> details = new HashMap<>();
        details.put("error", ex.getLocalizedMessage());

        ErrorResponse errorResponse = new ErrorResponse();
        buildErrorResponse(errorResponse, details, HttpStatus.NOT_FOUND, "Given resource not found");

        return new ResponseEntity(errorResponse, HttpStatus.NOT_FOUND);
    }
    private void buildErrorResponse(ErrorResponse errorResponse, Map<String, String> errorDetails, HttpStatus httpStatus,
                                    String errorMessage) {
        errorResponse.setHttpStatusCode(httpStatus.value());
        errorResponse.setHttpStatusMessage(httpStatus);
        errorResponse.setErrorDetails(errorDetails);
        errorResponse.setErrorMessage(errorMessage);

    }
}
