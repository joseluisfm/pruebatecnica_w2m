package es.joseluisfm.pruebatecnica_w2m.exception;

import java.lang.reflect.UndeclaredThrowableException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(RestExceptionHandler.class);

	private static final String INPUT_FIELDS_VALIDATION_ERROR = "Input fields validation error: ";
	private static final String INTERNAL_SERVER_ERROR = "Internal Server Error";

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
		return this.handleAllAux(ex, request);
	}

	@ExceptionHandler({ UndeclaredThrowableException.class })
	public ResponseEntity<Object> handleAllUndeclaredThrowableException(Exception ex, WebRequest request) {
		if (ex.getCause() instanceof RestException e) {
			return this.restExceptionHandleAux(e, request);
		} else {
			return this.handleAllAux(ex, request);
		}
	}

	private ResponseEntity<Object> handleAllAux(Exception ex, WebRequest request) {
		LOG.error(ex.getMessage(), ex);
		ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, getMensaje(ex));
		return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	@ExceptionHandler({ RestException.class })
	public ResponseEntity<Object> restExceptionHandle(RestException ex, WebRequest request) {
		return restExceptionHandleAux(ex, request);
	}

	private ResponseEntity<Object> restExceptionHandleAux(RestException ex, WebRequest request) {
		LOG.error(ex.getMessage(), ex);
		ApiError apiError = new ApiError(ex.getHttpStatus(), getMensaje(ex));
		return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	@ExceptionHandler({ ConstraintViolationException.class })
	public ResponseEntity<Object> validationExceptionHandle(RestException ex, WebRequest request) {
		LOG.error(INPUT_FIELDS_VALIDATION_ERROR + ex.getMessage(), ex);
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, INPUT_FIELDS_VALIDATION_ERROR + getMensaje(ex));
		return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	/**
	 * Gets all the nested messages
	 * 
	 * @param e Exception
	 * @return {@link String}
	 */
	private static final String getMensaje(final Exception e) {
		return getMensaje((Throwable) e);
	}

	/**
	 * Gets all the nested messages
	 * 
	 * @param e Throwable
	 * @return {@link String}
	 */
	private static final String getMensaje(final Throwable e) {
		StringBuilder sb = new StringBuilder();
		if (e != null) {
			Throwable thr = e;
			while ((thr instanceof RestException || thr instanceof LogicException) && thr.getCause() != null) {
				if (!sb.isEmpty()) {
					sb.append("; ");
				}
				sb.append(thr.getCause().getMessage());
				thr = thr.getCause();
			}
			if (sb.toString().isEmpty()) {
				sb.append(INTERNAL_SERVER_ERROR);
			}
		}
		return sb.toString();
	}

}