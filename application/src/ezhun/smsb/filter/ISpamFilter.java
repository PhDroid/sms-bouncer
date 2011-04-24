package ezhun.smsb.filter;

import ezhun.smsb.SmsPojo;
import ezhun.smsb.exceptions.ApplicationException;

/**
 * Spam Filter interface.
 */
public interface ISpamFilter {
	/**
	 * Checks if message is a spam
	 *
	 * @param message incoming messages
	 * @return should message be considered as spam or not
	 */
	boolean isSpam(SmsPojo message) throws ApplicationException;
}
