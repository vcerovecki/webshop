package hr.ingemark.webshop.exception.handler;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import hr.ingemark.webshop.exception.CustomException;
import hr.ingemark.webshop.model.common.CustomError;
import hr.ingemark.webshop.model.common.ErrorResponse;

@ControllerAdvice
public class ExceptionsHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	private ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		List<CustomError> errors = new ArrayList<CustomError>();
		for(ObjectError err : e.getBindingResult().getAllErrors()) {
			errors.add(new CustomError(400, err.getDefaultMessage()));
		}
		return new ResponseEntity<ErrorResponse>(ErrorResponse.builder().errorMessage(errors).build(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(CustomException.class)
	private ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
		List<CustomError> errors = new ArrayList<CustomError>();
		CustomError error = new CustomError();
		error.setCode(e.getCode());
		error.setErrorMessage(e.getMessage());
		errors.add(error);
		return new ResponseEntity<ErrorResponse>(ErrorResponse.builder().errorMessage(errors).build(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	private ResponseEntity<ErrorResponse> handleSQLException(ConstraintViolationException e) {
		List<CustomError> errors = new ArrayList<CustomError>();
		CustomError error = new CustomError();
		error.setErrorMessage(e.getMessage());
		errors.add(error);
		return new ResponseEntity<ErrorResponse>(ErrorResponse.builder().errorMessage(errors).build(), HttpStatus.BAD_REQUEST);
	}
	
}
