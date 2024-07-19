package com.fusion.sso.exception;

import org.springframework.http.HttpStatus;

/**
 * Custom exception class for resource not found errors.
 */
public class CommonNotFound extends Exception {

	private static final long serialVersionUID = 1L;

	private final String errorMessage;
	private final String path;
	private final HttpStatus status;

	/**
	 * Constructs a CommonNotFound exception with the specified error message,
	 * request path, and HTTP status code.
	 *
	 * @param errorMessage The error message describing not found error.
	 * @param path         The request path where the error occurred.
	 * @param status       The HTTP status code to be returned.
	 */
	public CommonNotFound(String errorMessage, String path, HttpStatus status) {
		this.errorMessage = errorMessage;
		this.path = path;
		this.status = status;
	}

	/**
	 * Get the error message describing the not found error.
	 *
	 * @return The error message.
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * Get the request path where the error occurred.
	 *
	 * @return The request path.
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Get the HTTP status code associated with the not found error.
	 *
	 * @return The HTTP status code.
	 */
	public HttpStatus getStatus() {
		return status;
	}

}