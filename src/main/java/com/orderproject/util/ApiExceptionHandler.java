package com.orderproject.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.orderproject.exception.InvalidDateException;

@RestControllerAdvice
public class ApiExceptionHandler {
	
	//make util
//	 @ExceptionHandler(Exception.class)
//	    public ResponseEntity<String> handleException(Exception ex) {
//	        // Customize the response based on the exception
//	        String errorMessage = "An error occurred: " + ex.getMessage();
//	        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
//	    }
	
	 @ResponseStatus(HttpStatus.BAD_REQUEST)
	 @ExceptionHandler(MethodArgumentNotValidException.class)
	 public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException ex){
		 System.out.println(">>>>>>");
		 Map<String, String> errorMap =new HashMap<>();
		 ex.getBindingResult().getFieldErrors().forEach(error -> {
			 errorMap.put(error.getField(),error.getDefaultMessage());
		 });
		 
		 System.out.println(">>>>>>"+errorMap);
		 return errorMap;
	 }
	 
	 @ExceptionHandler(InvalidDateException.class)
	 public ResponseEntity<String> customEx(InvalidDateException ex){
		 return new ResponseEntity<>("Invalid date Entered",HttpStatus.BAD_REQUEST);
		 
	 }
	
}
