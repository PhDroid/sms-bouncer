package com.phdroid.smsb.filter;

import android.content.ContentResolver;
import android.database.Cursor;
import com.phdroid.smsb.SmsPojo;
import com.phdroid.smsb.exceptions.ApplicationException;
import com.phdroid.smsb.exceptions.ArgumentException;
import com.phdroid.smsb.storage.dao.SmsContentProvider;

/**
 * Spam Filter taking into consideration only white list of senders.
 */
public class WhiteListSpamFilter implements ISpamFilter {
	ContentResolver resolver;

	public WhiteListSpamFilter(ContentResolver resolver) throws ApplicationException {
		if (resolver == null) {
			throw new ArgumentException(resolver);
		}
		this.resolver = resolver;
	}

	public ContentResolver getContentResolver() {
		return this.resolver;
	}

	@Override
	public boolean isSpam(SmsPojo message) {
		String[] selectionArgs = {message.getSender(), "1"};
		Cursor cur = getContentResolver().query(SmsContentProvider.CONTENT_URI,
				null,
				SmsContentProvider.SENDER + " = :1 AND " + SmsContentProvider.USER_FLAG_NOT_SPAM + " = :2",
				selectionArgs,
				null);

		return cur.getCount() == 0;
	}
}
