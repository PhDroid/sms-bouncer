package com.phdroid.smsb.filter;

import android.telephony.SmsMessage;
import com.phdroid.smsb.SmsPojo;
import com.phdroid.smsb.storage.dao.Session;
import com.phdroid.smsb.storage.dao.SmsMessageSenderEntry;
import com.phdroid.smsb.utility.SmsMessageTransferObject;

/**
 * Spam Filter implementation taking into consideration only contacts from address book.
 */
public class ContactSpamFilter implements ISpamFilter {
	private Session session;

	public ContactSpamFilter(Session session) {
		this.session = session;
	}

	@Override
	public boolean isSpam(SmsMessageTransferObject message) {
		return !this.session.isKnownSender(message.getSender());
	}
}
