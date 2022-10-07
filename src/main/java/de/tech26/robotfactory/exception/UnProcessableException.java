package de.tech26.robotfactory.exception;


public class UnProcessableException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public UnProcessableException() {
	}

	public UnProcessableException(String message) {
		super(message);
	}
}