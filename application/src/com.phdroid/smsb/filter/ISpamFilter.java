package com.phdroid.smsb.filter;

import android.telephony.SmsMessage;
import com.phdroid.smsb.SmsPojo;
import com.phdroid.smsb.exceptions.ApplicationException;
import com.phdroid.smsb.utility.SmsMessageTransferObject;

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
	boolean isSpam(SmsMessageTransferObject message);
}
