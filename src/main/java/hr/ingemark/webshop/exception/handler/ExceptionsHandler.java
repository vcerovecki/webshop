package hr.ingemark.webshop.exception.handler;

import java.util.stream.Collectors;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import hr.ingemark.webshop.exception.CustomException;
import hr.ingemark.webshop.model.common.CustomResponse;
import hr.ingemark.webshop.util.CreateResponseUtil;

@ControllerAdvice
public class ExceptionsHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	private ResponseEntity<CustomResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		String tMessage = e.getBindingResult()
			.getAllErrors()
			.stream()
			.map(err -> err.getDefaultMessage())
			.collect(Collectors.joining(";"));
		return new ResponseEntity<CustomResponse>(CreateResponseUtil.createResponse(400, tMessage), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(CustomException.class)
	private ResponseEntity<CustomResponse> handleCustomException(CustomException e) {
		return new ResponseEntity<CustomResponse>(CreateResponseUtil.createResponse(e.getCode(), e.getMessage()), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	private ResponseEntity<CustomResponse> handleSQLException(ConstraintViolationException e) {
		return new ResponseEntity<CustomResponse>(CreateResponseUtil.createResponse(500, "Internal server error"), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
