package com.rahul.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class EmployeeRestControllerAdvice {

	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public Map<String,Object> handleRuntimeException(RuntimeException exp){
		Map<String,Object> response = new HashMap<>();
		response.put("Success", Boolean.FALSE);
		response.put("Message", exp.getMessage());
		return response;
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public Map<String,Object> handleRuntimeException(Exception exp){
		Map<String,Object> response = new HashMap<>();
		response.put("Success", Boolean.FALSE);
		response.put("Message", exp.getMessage());
		return response;
	}
	
	
}
