package warehouse.controllers;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.context.request.WebRequest;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.*;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;

import warehouse.exceptions.*;

@AllArgsConstructor
class MyErrorData {
	public Object errorMessage;
	public LocalDateTime occurredOn;
	public String status;
	// …
}

@RestControllerAdvice
@Slf4j
public class ExceptionsController {
	private String processingExceptions(Exception e) {
		log.error("excepion class: {}, message: {}", e.getClass().getSimpleName(), e.getMessage());
		return e.getMessage();
	}

	@ExceptionHandler(DuplicatedException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	String duplicatedKeyHandler(DuplicatedException e) {
		return processingExceptions(e);
	}

	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String notFounHandler(NotFoundException e) {
		return processingExceptions(e);
	}
	
	@ExceptionHandler(InvalidCredentialException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String invalidCredentialExceptionHandler(InvalidCredentialException e) {
		return processingExceptions(e);
	}
	
	@ExceptionHandler(PasswordExpiredException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	String passwordExpiredHandler(PasswordExpiredException e) {
		return processingExceptions(e);
	}
	
	@ExceptionHandler(WebExchangeBindException.class)
	public ResponseEntity<Object> WebExchangeBindExceptionHandler(WebExchangeBindException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getAllErrors().forEach((error) -> {
			String fieldName = (error instanceof FieldError) ? ((FieldError) error).getField() : "<common>";
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	String OtherExceptionHandler(Exception e) {
		return processingExceptions(e);
	}
}
