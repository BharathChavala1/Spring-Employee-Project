package com.example.demo.controller.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;


@ControllerAdvice
public class ExceptionHandlerClass {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleMethodException(MethodArgumentNotValidException ex) {
		List<String> errorMsg = ex.getFieldErrors().stream().map(t -> t.getDefaultMessage())
				.collect(Collectors.toList());
		return new ResponseEntity<>(errorMsg, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NoResourceFoundException.class)
	public ResponseEntity<?> noResourceFound(NoResourceFoundException ex) {
		String message = ex.getMessage();
		return new ResponseEntity<>(message, ex.getStatusCode());
	}


}
