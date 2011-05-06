package ezhun.smsb.storage;

import android.content.ContentValues;
import android.database.Cursor;
import ezhun.smsb.SmsPojo;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Please, write short description of what this file is for.
 */
public class SmsPojoAdapter {
	public static SmsPojo fromCursor (Cursor smsCursor) {
		throw new NotImplementedException();
	}

	public static ContentValues toContentValues (SmsPojo sms) {
		ContentValues values = new ContentValues();
		values.put(SmsContentProvider.SENDER, sms.getSender());
		values.put(SmsContentProvider.MESSAGE, sms.getMessage());
		values.put(SmsContentProvider.RECEIVED, sms.getReceived());
		values.put(SmsContentProvider.SYSTEM_FLAG_SPAM, sms.isMarkedSpamBySystem());
		values.put(SmsContentProvider.USER_FLAG_SPAM, sms.isMarkedSpamByUser());
		values.put(SmsContentProvider.USER_FLAG_NOT_SPAM, sms.isMarkedNotSpamByUser());
		return values;
	}
}
