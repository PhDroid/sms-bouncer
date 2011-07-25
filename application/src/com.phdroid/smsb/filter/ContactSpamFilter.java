package com.phdroid.smsb.filter;

import com.phdroid.smsb.SmsPojo;
import com.phdroid.smsb.storage.dao.Session;
import com.phdroid.smsb.storage.dao.SmsMessageSenderEntry;

/**
 * Spam Filter implementation taking into consideration only contacts from address book.
 */
public class ContactSpamFilter implements ISpamFilter {
	private Session session;

	public ContactSpamFilter(Session session) {
		this.session = session;
	}

	@Override
	public boolean isSpam(SmsPojo message) {
		SmsMessageSenderEntry sender = this.session.getSenderById(message.getSenderId());
		return !this.session.isKnownSender(sender);
	}
}
