package com.hard.qroyal.integration.exception;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionHandler {

	@org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> validateHandleException(MethodArgumentNotValidException ex) {
		ErrorDto dto = new ErrorDto(HttpStatus.BAD_REQUEST, "Validation error");
		dto.setDetails(ex.getBindingResult().getAllErrors().stream()
				.map(err -> err.unwrap(ConstraintViolation.class))
				.map(err -> String.format("'%s' %s", err.getPropertyPath(), err.getMessage()))
				.collect(Collectors.toList()));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dto);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(BadCredentialsException.class)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	private ResponseEntity<?> handleMessageAuth(BadCredentialsException e, HttpServletRequest request) {
		ErrorDto message = new ErrorDto(HttpStatus.BAD_REQUEST, "Unauthorized");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
	}

	@Data
	public static class ErrorDto {

		private final int status;

		private final String error;

		private final String message;

		private List<String> details;

		public ErrorDto(HttpStatus httpStatus, String message) {
			status = httpStatus.value();
			error = httpStatus.getReasonPhrase();
			this.message = message;
		}
	}
}