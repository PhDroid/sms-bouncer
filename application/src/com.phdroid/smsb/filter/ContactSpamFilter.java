package com.phdroid.smsb.filter;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;
import com.phdroid.smsb.SmsPojo;
import com.phdroid.smsb.exceptions.ApplicationException;
import com.phdroid.smsb.exceptions.ArgumentException;

/**
 * Spam Filter implementation taking into consideration only contacts from address book.
 */
public class ContactSpamFilter implements ISpamFilter {
	ContentResolver resolver;

	public ContactSpamFilter(ContentResolver resolver) throws ApplicationException {
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
		String[] selectionArgs = {message.getSender()};
		Cursor cur = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
				null,
				ContactsContract.CommonDataKinds.Phone.NUMBER + " = :1",
				selectionArgs,
				null);

		return cur.getCount() == 0;
	}
}
