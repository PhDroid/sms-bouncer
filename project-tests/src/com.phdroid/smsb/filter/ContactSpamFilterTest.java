package com.phdroid.smsb.filter;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.net.Uri;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Data;
import com.phdroid.smsb.SmsPojo;
import com.phdroid.smsb.TestSmsPojo;
import com.phdroid.smsb.base.ProviderTestBase;
import com.phdroid.smsb.filter.doubles.PhoneContentProviderFake;
import junit.framework.Assert;

import java.util.Hashtable;

/**
 * Test class for ContactSpamFilter.
 */
public class ContactSpamFilterTest extends ProviderTestBase {
	private static String SENDER = "(067) 129-09-45";

	@Override
	public Hashtable<Uri, ContentProvider> getTestContentProviders() {
		PhoneContentProviderFake phoneContentProvider = new PhoneContentProviderFake();

		Hashtable<Uri, ContentProvider> settings = new Hashtable<Uri, ContentProvider>();
		settings.put(Data.CONTENT_URI, phoneContentProvider);
		settings.put(Phone.CONTENT_URI, phoneContentProvider);
		return settings;
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();

		ContentValues values = new ContentValues();
		values.put(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE);
		values.put(Phone.NUMBER, SENDER);
		getContentResolver().insert(Data.CONTENT_URI, values);
	}

	public void testKnownNumber() throws Exception {
		TestSmsPojo message = new TestSmsPojo();
		message.setSender(SENDER);
		message.setReceived((int) (System.currentTimeMillis() / 1000L));
		message.setMessage("I am not a SPAM message");

		ISpamFilter filter = new ContactSpamFilter(getContentResolver());
		boolean isSpam = filter.isSpam(message);
		Assert.assertEquals(false, isSpam);
	}

	public void testUnknownNumber() throws Exception {
		TestSmsPojo message = new TestSmsPojo();
		message.setSender("(097) 112-33-26");
		message.setReceived((int) (System.currentTimeMillis() / 1000L));
		message.setMessage("I am SPAM message");

		ISpamFilter filter = new ContactSpamFilter(getContentResolver());
		boolean isSpam = filter.isSpam(message);
		Assert.assertEquals(true, isSpam);
	}
}
