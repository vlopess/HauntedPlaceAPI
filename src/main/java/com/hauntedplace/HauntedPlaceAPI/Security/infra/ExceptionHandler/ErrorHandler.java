package com.hauntedplace.HauntedPlaceAPI.Security.infra.ExceptionHandler;

import com.hauntedplace.HauntedPlaceAPI.Security.infra.NotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler({EntityNotFoundException.class, NotFoundException.class})
    public ResponseEntity handleEntityNotFoundException(RuntimeException e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public ResponseEntity handleInternalAuthenticationServiceException(InternalAuthenticationServiceException e) {
        return ResponseEntity.badRequest().build();
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity handleExceptionFollower(Exception e) {
        return ResponseEntity.badRequest().body("is Already Followed!");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException500(Exception e) {
        return ResponseEntity.internalServerError().body(e.getLocalizedMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity handleErrorBadCredentials() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleError400(MethodArgumentNotValidException ex) {
        var errors = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(errors.stream().map(DataErrorValidation::new).toList());
    }


    private record DataErrorValidation(String key, String message) {
        public DataErrorValidation(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }
}
