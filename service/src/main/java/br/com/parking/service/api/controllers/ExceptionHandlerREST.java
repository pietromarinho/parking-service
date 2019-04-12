package br.com.parking.service.api.controllers;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionHandlerREST{
	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private			Logger			logger;

	public ExceptionHandlerREST() {
		setLogger(LoggerFactory.getLogger(ExceptionHandler.class));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleException(MethodArgumentNotValidException e) {
		getLogger().error(e.getMessage());
		return ResponseEntity.badRequest().body(e.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage).collect(Collectors.toList()));
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<?> handleException(ConstraintViolationException e) {
		getLogger().error(e.getMessage());
		return ResponseEntity.badRequest().body(e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.toList()));
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<?> handleException(HttpRequestMethodNotSupportedException e) {
		getLogger().error(e.getMessage());
		return ResponseEntity.badRequest().body("method." + e.getMethod().toLowerCase() + ".not.supported");
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<?> handleException(HttpMessageNotReadableException e) {
		getLogger().error(e.getMessage());
		return ResponseEntity.badRequest().body(e.getRootCause() != null? e.getRootCause().getMessage() : "No message for this error!");
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<?> handleException(RuntimeException e) {
		getLogger().error(e.getMessage());
		return ResponseEntity.badRequest().build();
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<?> handleException(DataIntegrityViolationException e) {
		getLogger().error(e.getMessage());
		if (e.getCause() instanceof org.hibernate.exception.ConstraintViolationException)
			return new ResponseEntity<>(((org.hibernate.exception.ConstraintViolationException) e.getCause()).getConstraintName().replaceAll("_", ".") + ".violation", HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>("database.violation", HttpStatus.BAD_REQUEST);
	}
}