package ezhun.smsb.broadcast;

import android.content.ContentResolver;
import android.content.ContentValues;
import ezhun.smsb.SmsPojo;
import ezhun.smsb.exceptions.ApplicationException;
import ezhun.smsb.filter.ISpamFilter;
import ezhun.smsb.filter.SmartSpamFilter;
import ezhun.smsb.storage.SmsContentProvider;

import java.util.ArrayList;

public class MessageProcessor implements IMessageProcessor {
	@Override
	public SmsPojo[] ProcessMessages(SmsPojo[] messages, ContentResolver resolver) {
		ISpamFilter spamFiler = new SmartSpamFilter(resolver);
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
				values.add(message.toContentValues());
			}
		}
		if (values.size() == 0) {
			return new SmsPojo[0];
		} else {
			ContentValues[] valuesArray = new ContentValues[values.size()];
			resolver.bulkInsert(SmsContentProvider.CONTENT_URI, values.toArray(valuesArray));

			SmsPojo[] resArray = new SmsPojo[res.size()];
			res.toArray(resArray);
			return resArray;
		}
	}
}
