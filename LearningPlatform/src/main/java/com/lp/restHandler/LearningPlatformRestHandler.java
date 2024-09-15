package com.lp.restHandler;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class LearningPlatformRestHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<List<?>> processUnmergeException(final MethodArgumentNotValidException ex) {

		List<?> list = ex.getBindingResult().getAllErrors().stream().map(fieldError -> fieldError.getDefaultMessage())
				.collect(Collectors.toList());
		return new ResponseEntity<>(list, HttpStatus.BAD_REQUEST);
	}

}
