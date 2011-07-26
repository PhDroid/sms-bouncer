package com.phdroid.smsb.broadcast;

import android.telephony.SmsMessage;
import com.phdroid.smsb.storage.dao.Session;
import com.phdroid.smsb.utility.SmsMessageTransferObject;

public interface IMessageProcessor {
	/**
	 * Processes incoming messages
	 *
	 * @param messages Incoming messages
	 * @param session  Session object
	 * @return Array of messages filtered as spam
	 */
	public SmsMessageTransferObject[] ProcessMessages(SmsMessageTransferObject[] messages, Session session);
}
