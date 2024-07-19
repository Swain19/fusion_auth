package com.fusion.sso.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(CommonNotFound.class)
	protected ResponseEntity<Object> handleEntityNotFound(CommonNotFound ex) {
		ServerResponse serverError = createServerError(ex.getStatus(), ex.getPath(), ex.getErrorMessage());
		return buildResponseEntity(serverError);
	}

//	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
															   final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		logger.info(ex.getClass().getName());

		final List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
		Map<String, Set<String>> errorsMap = fieldErrors.stream().collect(Collectors.groupingBy(FieldError::getField,
				Collectors.mapping(FieldError::getDefaultMessage, Collectors.toSet())));
		ServerResponse serverError = createServerError(HttpStatus.BAD_REQUEST, "", errorsMap.toString());
		return buildResponseEntity(serverError);
	}

	private ServerResponse createServerError(HttpStatus status, String path, String errorMessage) {
		ServerResponse serverError = new ServerResponse();
		serverError.setTimestamp(LocalDateTime.now());
		serverError.setStatus(String.valueOf(status.value()));
		serverError.setError(status);
		serverError.setPath(path);
		serverError.setMessage(errorMessage);
		serverError.setException(errorMessage);
		return serverError;
	}

	private ResponseEntity<Object> buildResponseEntity(ServerResponse serverError) {
		return new ResponseEntity<>(serverError, serverError.getError());
	}
}