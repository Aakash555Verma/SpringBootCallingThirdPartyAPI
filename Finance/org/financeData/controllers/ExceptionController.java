package org.financeData.controllers;

import org.financeData.dtos.ErrorResponse;
import org.financeData.exceptions.ExternalServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class ExceptionController {

	@ExceptionHandler({ IllegalArgumentException.class, NullPointerException.class })
	public ResponseEntity<String> handleBadRequestExceptions(Exception ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

	// With proper application format
	@ExceptionHandler({ IllegalStateException.class })
	public ResponseEntity<ErrorResponse> handleBadRequestExceptionsWithCustomMessage(Exception ex) {
		ErrorResponse error = new ErrorResponse(ex.getMessage(), 400);
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ExternalServiceException.class)
	public ResponseEntity<String> handleExternal(ExternalServiceException ex) {
		return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(ex.getMessage());
	}

	@ExceptionHandler(IndexOutOfBoundsException.class)
	public ResponseEntity<?> handleIndexException(IndexOutOfBoundsException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler({ MethodArgumentTypeMismatchException.class, NumberFormatException.class })
	public ResponseEntity<String> handleTypeMismatchExceptions(Exception ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
	}
}
