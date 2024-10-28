package es.joseluisfm.pruebatecnica_w2m.exception;

public class ComponentException extends Exception {

	private static final long serialVersionUID = -2149522646485093358L;

	public ComponentException() {
		super();
	}

	public ComponentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ComponentException(String message, Throwable cause) {
		super(message, cause);
	}

	public ComponentException(String message) {
		super(message);
	}

	public ComponentException(Throwable cause) {
		super(cause);
	}

}
