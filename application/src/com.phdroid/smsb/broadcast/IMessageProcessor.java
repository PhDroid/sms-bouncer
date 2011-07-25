package com.phdroid.smsb.broadcast;

import android.content.ContentResolver;
import com.phdroid.smsb.SmsPojo;
import com.phdroid.smsb.storage.dao.Session;

public interface IMessageProcessor {
	/**
	 * Processes incoming messages
	 *
	 * @param messages Incoming messages
	 * @param session Session object
	 * @return Array of messages filtered as spam
	 */
	public SmsPojo[] ProcessMessages(SmsPojo[] messages, Session session);
}
