package de.tech26.robotfactory.exception;

import org.springframework.http.HttpStatus;

import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

public class ApiException {
	private Timestamp timestamp;
	private String message;
	private HttpStatus status;
	private List<String> errors;

	private static ApiException apiException = null;

	private ApiException() {
		super();
	}

	public static ApiException getInstance(final HttpStatus status, final String message, final String error) {
		if (apiException == null)
			apiException = new ApiException();

		return getInstance(status, message, Arrays.asList(error));
	}

	public static ApiException getInstance(final HttpStatus status, final String message, final List<String> errors) {
		if (apiException == null)
			apiException = new ApiException();

		apiException.setTimestamp(Timestamp.valueOf(ZonedDateTime.now().toLocalDateTime()));
		apiException.setMessage(message);
		apiException.setStatus(status);
		apiException.setErrors(errors);
		return apiException;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}
	private void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public HttpStatus getStatus() {
		return status;
	}
	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public List<String> getErrors() {
		return errors;
	}
	public void setErrors(List<String> errors) {
		this.errors = errors;
	}


	@Override
	public String toString() {
		return "Exception [timestamp=" + timestamp + ", message=" + message + ", status=" + status + ", errors="
				+ errors + "]";
	}

}