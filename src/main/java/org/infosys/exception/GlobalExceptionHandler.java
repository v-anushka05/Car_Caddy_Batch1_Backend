package org.infosys.exception;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.infosys.exception.DuplicateContactNumberException;
import org.infosys.exception.DuplicateEmailException;
import org.infosys.exception.InvalidEntityException;
import org.infosys.exception.InvalidLoginException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
	    Map<String, String> errors = new HashMap<>();
	    ex.getBindingResult().getFieldErrors().forEach(error -> {
	        errors.put(error.getField(), error.getDefaultMessage()); // Field-specific errors
	    });
	    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

    
    @ExceptionHandler(InvalidEntityException.class)
    public ResponseEntity<Map<String, String>> handleNotFoundException(InvalidEntityException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("message", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    

	@ExceptionHandler(DuplicateEmailException.class)
	public ResponseEntity<Map<String, String>> handleDuplicateEmail(DuplicateEmailException ex) {
		Map<String, String> errors = new HashMap<>();
		errors.put("emailId", ex.getMessage()); // Bind error to emailId field
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(DuplicateContactNumberException.class)
	public ResponseEntity<Map<String, String>> handleDuplicateContactNumber(DuplicateContactNumberException ex) {
		Map<String, String> errors = new HashMap<>();
		errors.put("contactNumber", ex.getMessage()); // Bind error to contactNumber field
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InvalidLoginException.class)
	public ResponseEntity<Map<String, String>> handleInvalidLogin(InvalidLoginException ex) {
		Map<String, String> errors = new HashMap<>();
		errors.put("error", ex.getMessage()); // Bind error to error field
		return new ResponseEntity<>(errors, HttpStatus.UNAUTHORIZED);
	}	

}
