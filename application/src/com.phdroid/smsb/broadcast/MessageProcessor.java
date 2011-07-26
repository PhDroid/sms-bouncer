package com.phdroid.smsb.broadcast;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.telephony.SmsMessage;
import com.phdroid.smsb.SmsPojo;
import com.phdroid.smsb.exceptions.ApplicationException;
import com.phdroid.smsb.filter.ISpamFilter;
import com.phdroid.smsb.filter.SmartSpamFilter;
import com.phdroid.smsb.storage.ApplicationSettings;
import com.phdroid.smsb.storage.dao.Session;
import com.phdroid.smsb.utility.SmsMessageTransferObject;

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
	public SmsMessageTransferObject[] ProcessMessages(SmsMessageTransferObject[] messages, Session session) {
		ISpamFilter spamFiler = new SmartSpamFilter(session);

		ArrayList<SmsMessageTransferObject> res = new ArrayList<SmsMessageTransferObject>();
		for (SmsMessageTransferObject message : messages) {
			boolean isSpam = spamFiler.isSpam(message);
			if (isSpam) {
				res.add(message);
			}
		}
		SmsMessageTransferObject[] resArray = new SmsMessageTransferObject[res.size()];
		res.toArray(resArray);
		return resArray;
	}
}
