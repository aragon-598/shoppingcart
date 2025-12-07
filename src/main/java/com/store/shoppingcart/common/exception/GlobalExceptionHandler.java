package com.store.shoppingcart.common.exception;

import com.store.shoppingcart.clients.domain.exception.ClientNotFoundException;
import com.store.shoppingcart.clients.domain.exception.DuplicateDocumentException;
import com.store.shoppingcart.clients.domain.exception.InvalidClientDataException;
import com.store.shoppingcart.clients.domain.exception.InvalidDocumentException;
import com.store.shoppingcart.clients.domain.exception.InvalidPhoneNumberException;
import com.store.shoppingcart.clients.domain.exception.UserAlreadyHasClientException;
import com.store.shoppingcart.common.dto.ApiResponse;
import com.store.shoppingcart.common.dto.ErrorResponse;
import com.store.shoppingcart.security.domain.exception.InvalidCredentialsException;
import com.store.shoppingcart.security.domain.exception.InvalidEmailException;
import com.store.shoppingcart.security.domain.exception.UserAlreadyExistsException;
import com.store.shoppingcart.security.domain.exception.UserInactiveException;
import com.store.shoppingcart.security.domain.exception.WeakPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Object>> handleUserAlreadyExists(UserAlreadyExistsException ex) {
        ErrorResponse error = new ErrorResponse(ex.getMessage());
        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(ApiResponse.error(HttpStatus.CONFLICT.value(), error));
    }
    
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApiResponse<Object>> handleInvalidCredentials(InvalidCredentialsException ex) {
        ErrorResponse error = new ErrorResponse(ex.getMessage());
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(ApiResponse.error(HttpStatus.UNAUTHORIZED.value(), error));
    }
    
    @ExceptionHandler(UserInactiveException.class)
    public ResponseEntity<ApiResponse<Object>> handleUserInactive(UserInactiveException ex) {
        ErrorResponse error = new ErrorResponse(ex.getMessage());
        return ResponseEntity
            .status(HttpStatus.FORBIDDEN)
            .body(ApiResponse.error(HttpStatus.FORBIDDEN.value(), error));
    }
    
    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleClientNotFound(ClientNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(ex.getMessage());
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ApiResponse.error(HttpStatus.NOT_FOUND.value(), error));
    }
    
    @ExceptionHandler({DuplicateDocumentException.class, UserAlreadyHasClientException.class})
    public ResponseEntity<ApiResponse<Object>> handleDuplicateDocument(RuntimeException ex) {
        ErrorResponse error = new ErrorResponse(ex.getMessage());
        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(ApiResponse.error(HttpStatus.CONFLICT.value(), error));
    }
    
    @ExceptionHandler({InvalidEmailException.class, WeakPasswordException.class, 
                      InvalidPhoneNumberException.class, InvalidDocumentException.class,
                      InvalidClientDataException.class, IllegalArgumentException.class})
    public ResponseEntity<ApiResponse<Object>> handleValidationExceptions(RuntimeException ex) {
        ErrorResponse error = new ErrorResponse(ex.getMessage());
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ApiResponse.error(HttpStatus.BAD_REQUEST.value(), error));
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> details = ex.getBindingResult()
            .getAllErrors()
            .stream()
            .map(error -> {
                String fieldName = ((FieldError) error).getField();
                String message = error.getDefaultMessage();
                return fieldName + ": " + message;
            })
            .collect(Collectors.toList());
        
        ErrorResponse error = new ErrorResponse("Validation failed", details);
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ApiResponse.error(HttpStatus.BAD_REQUEST.value(), error));
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGenericException(Exception ex) {
        ErrorResponse error = new ErrorResponse("Internal server error: " + ex.getMessage());
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), error));
    }
}
