package com.phdroid.smsb.broadcast;

import android.content.ContentResolver;
import android.content.ContentValues;
import com.phdroid.smsb.SmsPojo;
import com.phdroid.smsb.exceptions.ApplicationException;
import com.phdroid.smsb.filter.ISpamFilter;
import com.phdroid.smsb.filter.SmartSpamFilter;
import com.phdroid.smsb.storage.ApplicationSettings;
import com.phdroid.smsb.storage.dao.Session;

import java.util.ArrayList;

public class MessageProcessor implements IMessageProcessor {
	/**
	 * Processes incoming messages
	 *
	 * @param messages Incoming messages
	 * @param session Session object
	 * @return Array of messages filtered as spam
	 */
	@Override
	public SmsPojo[] ProcessMessages(SmsPojo[] messages, Session session) {
		ISpamFilter spamFiler = new SmartSpamFilter(session);
		ArrayList<ContentValues> values = new ArrayList<ContentValues>();

		ArrayList<SmsPojo> res = new ArrayList<SmsPojo>();
		for (SmsPojo message : messages) {
			boolean isSpam = false;
			try {
				isSpam = spamFiler.isSpam(message);
			} catch (ApplicationException e) {
				//todo: implement logging
			}
			if (isSpam) {
				res.add(message);
			}
		}
		SmsPojo[] resArray = new SmsPojo[res.size()];
		res.toArray(resArray);
		return resArray;
	}
}
