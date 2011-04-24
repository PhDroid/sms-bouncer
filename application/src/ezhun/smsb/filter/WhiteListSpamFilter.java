package ezhun.smsb.filter;

import android.content.ContentResolver;
import android.database.Cursor;
import ezhun.smsb.SmsPojo;
import ezhun.smsb.exceptions.ApplicationException;
import ezhun.smsb.exceptions.ArgumentException;
import ezhun.smsb.storage.SmsContentProvider;

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

		return cur.getCount() != 0;
	}
}
