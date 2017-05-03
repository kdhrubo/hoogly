package com.effectiv.crm.exception;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class DefaultRestErrorHandler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleNotFoundException(BusinessException ex, HttpServletRequest request) {
		//TODO - get a key from ex object and look up in the error configuration file.
		
		ErrorDetail errorDetail = new ErrorDetail("Business Error", HttpStatus.NOT_FOUND.value(),ex.getMessage(), new Date().getTime(), null, null,"");
		return new ResponseEntity<>(errorDetail, null, HttpStatus.NOT_FOUND);
    }
	
	
}
