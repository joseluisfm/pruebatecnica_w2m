package es.joseluisfm.pruebatecnica_w2m.exception;

import org.springframework.http.HttpStatus;

public class RestException extends Exception {
	private static final long serialVersionUID = 3627389052361811195L;

	private HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR; // NOSONAR

	public RestException() {
		super();
	}

	public RestException(HttpStatus httpStatus) {
		super();
		this.httpStatus = httpStatus;
	}

	public RestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		tratarLogicException(cause);
	}

	public RestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, HttpStatus httpStatus) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.httpStatus = httpStatus;
	}

	public RestException(String message, Throwable cause) {
		super(message, cause);
		tratarLogicException(cause);
	}

	public RestException(String message, Throwable cause, HttpStatus httpStatus) {
		super(message, cause);
		this.httpStatus = httpStatus;
		tratarLogicException(cause);
	}

	public RestException(String message) {
		super(message);
	}

	public RestException(String message, HttpStatus httpStatus) {
		super(message);
		this.httpStatus = httpStatus;
	}

	public RestException(Throwable cause) {
		super(cause);
		tratarLogicException(cause);
	}

	public RestException(Throwable cause, HttpStatus httpStatus) {
		super(cause);
		this.httpStatus = httpStatus;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	private void tratarLogicException(Throwable cause) {
		if (cause instanceof LogicException e) {
			this.httpStatus = e.getHttpStatus();
		}
	}

}
