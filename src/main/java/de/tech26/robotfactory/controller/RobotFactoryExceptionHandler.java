package de.tech26.robotfactory.controller;

import de.tech26.robotfactory.exception.ApiException;
import de.tech26.robotfactory.exception.UnProcessableException;
import de.tech26.robotfactory.exception.ValidationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class RobotFactoryExceptionHandler extends ResponseEntityExceptionHandler {

	ApiException errorDetails = null;

	@ExceptionHandler({ ValidationException.class })
	public ResponseEntity<Object> validationException(final Exception ex, final WebRequest request) {
		errorDetails = ApiException.getInstance(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), ex.getMessage());

		return new ResponseEntity<Object>(errorDetails, new HttpHeaders(), errorDetails.getStatus());
	}


	@ExceptionHandler({ UnProcessableException.class })
	public ResponseEntity<Object> unProcessableException(final Exception ex, final WebRequest request) {
		errorDetails = ApiException.getInstance(HttpStatus.UNPROCESSABLE_ENTITY, ex.getLocalizedMessage(), ex.getMessage());

		return new ResponseEntity<Object>(errorDetails, new HttpHeaders(), errorDetails.getStatus());
	}

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleAllTypesOfExceptions(final Exception ex, final WebRequest request) {
		final ApiException errorDetails = ApiException.getInstance(HttpStatus.INTERNAL_SERVER_ERROR,
				ex.getLocalizedMessage(), "error occurred");

		return new ResponseEntity<Object>(errorDetails, new HttpHeaders(), errorDetails.getStatus());
	}

}