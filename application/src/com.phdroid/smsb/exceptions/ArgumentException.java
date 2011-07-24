package com.phdroid.smsb.exceptions;

/**
 * General argument exception.
 */
public class ArgumentException extends ApplicationException {
	Object argument;

	/**
	 * General argument exception
	 *
	 * @param argument argument that is not OK
	 */
	public ArgumentException(Object argument) {
		super("Given argument is not OK");
		this.argument = argument;
	}
}
