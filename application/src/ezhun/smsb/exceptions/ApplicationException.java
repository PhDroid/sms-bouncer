package ezhun.smsb.exceptions;

/**
 * General exception for Sms-bouncer application.
 */
public class ApplicationException extends Exception {
	public ApplicationException() {
	}

	public ApplicationException(String s) {
		super(s);
	}

	public ApplicationException(String s, Throwable throwable) {
		super(s, throwable);
	}

	public ApplicationException(Throwable throwable) {
		super(throwable);
	}
}
