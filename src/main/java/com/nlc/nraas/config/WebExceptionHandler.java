package com.nlc.nraas.config;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@RestControllerAdvice
public class WebExceptionHandler { 
	/*private final Logger logger = LoggerFactory.getLogger(getClass());*/
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = { ConstraintViolationException.class })
	public Object handleConstraintViolationException(ConstraintViolationException e) {

		Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
		StringBuilder strBuilder = new StringBuilder();
		for (ConstraintViolation<?> violation : violations) {
			strBuilder.append(violation.getMessage() + "\n");
		}
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("status", HttpStatus.BAD_REQUEST.value());
		response.put("message", strBuilder.toString());
		return response;
	}
	
	@ResponseStatus(HttpStatus.CONFLICT)
	@ExceptionHandler(value = { DataIntegrityViolationException.class })
	public Object handleConflict(DataIntegrityViolationException e) {
		
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("status", HttpStatus.CONFLICT.value());
		response.put("message", e.getMessage());
		return response;
	}
}
