package com.phdroid.smsb.broadcast;

import android.content.ContentResolver;
import com.phdroid.smsb.SmsPojo;

public interface IMessageProcessor {
	/**
	 * Processes incoming messages
	 *
	 * @param messages Incoming messages
	 * @param resolver Content resolver
	 * @return Array of messages filtered as spam
	 */
	public SmsPojo[] ProcessMessages(SmsPojo[] messages, ContentResolver resolver);
}
