package es.joseluisfm.pruebatecnica_w2m.exception;

import org.springframework.http.HttpStatus;

public class LogicException extends Exception {

	private static final long serialVersionUID = 3260133159442709642L;
	private HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR; //NOSONAR

	public LogicException() {
		super();
	}

	public LogicException(HttpStatus httpStatus) {
		super();
		this.httpStatus = httpStatus;
	}

	public LogicException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.ifThrowableIsLogicExceptionTakeHisHttpStatus(cause);
	}

	public LogicException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, HttpStatus httpStatus) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.httpStatus = httpStatus;
	}

	public LogicException(String message, Throwable cause) {
		super(message, cause);
		this.ifThrowableIsLogicExceptionTakeHisHttpStatus(cause);
	}

	public LogicException(String message, Throwable cause, HttpStatus httpStatus) {
		super(message, cause);
		this.httpStatus = httpStatus;
	}

	public LogicException(String message) {
		super(message);
	}

	public LogicException(String message, HttpStatus httpStatus) {
		super(message);
		this.httpStatus = httpStatus;
	}

	public LogicException(Throwable cause) {
		super(cause);
		this.ifThrowableIsLogicExceptionTakeHisHttpStatus(cause);
	}

	public LogicException(Throwable cause, HttpStatus httpStatus) {
		super(cause);
		this.httpStatus = httpStatus;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	private void ifThrowableIsLogicExceptionTakeHisHttpStatus(Throwable cause) {
		if (cause instanceof LogicException e) {
			this.httpStatus = e.httpStatus;
		}
	}
}
