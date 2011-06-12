package com.phdroid.smsb.storage;

import android.content.ContentValues;
import com.phdroid.smsb.SmsPojo;
import com.phdroid.smsb.storage.dao.SmsContentProvider;

/**
 * Please, write short description of what this file is for.
 */
public class SmsPojoAdapter {
    public static ContentValues toContentValues (SmsPojo sms) {
		ContentValues values = new ContentValues();
		values.put(SmsContentProvider.SENDER, sms.getSender());
		values.put(SmsContentProvider.MESSAGE, sms.getMessage());
		values.put(SmsContentProvider.RECEIVED, sms.getReceived());
        values.put(SmsContentProvider.READ, sms.isRead());
		values.put(SmsContentProvider.USER_FLAG_NOT_SPAM, sms.isMarkedNotSpamByUser());
		return values;
	}
}
