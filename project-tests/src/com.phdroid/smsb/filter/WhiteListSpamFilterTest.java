package com.phdroid.smsb.filter;

import android.content.ContentProvider;
import android.net.Uri;
import com.phdroid.smsb.SmsPojo;
import com.phdroid.smsb.base.ProviderTestBase;
import com.phdroid.smsb.exceptions.ApplicationException;
import com.phdroid.smsb.storage.dao.SmsContentProvider;
import junit.framework.Assert;

import java.util.Hashtable;

/**
 * Test class for WhiteListSpamFilter.
 */
public class WhiteListSpamFilterTest extends ProviderTestBase {
	private static String SENDER = "(097) 112 33 26";

	@Override
	public Hashtable<Uri, ContentProvider> getTestContentProviders() {
		Hashtable<Uri, ContentProvider> settings = new Hashtable<Uri, ContentProvider>();
		settings.put(SmsContentProvider.CONTENT_URI, new SmsContentProvider());
		return settings;
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();

		SmsPojo spam = new SmsPojo();
		spam.setSender("1346");
		spam.setMessage("Novaja aktsia ot magazinov Kharkova");
		spam.setReceived((int) (System.currentTimeMillis() / 1000L));
		getContentResolver().insert(SmsContentProvider.CONTENT_URI, spam.toContentValues());

		SmsPojo notSpam = new SmsPojo();
		notSpam.setSender(SENDER);
		notSpam.setMessage("Let's grab some whisky");
		notSpam.setReceived((int) (System.currentTimeMillis() / 1000L));
		notSpam.setMarkedNotSpamByUser(true);
		getContentResolver().insert(SmsContentProvider.CONTENT_URI, notSpam.toContentValues());
	}

	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		getContentResolver().delete(SmsContentProvider.CONTENT_URI, null, null);
	}

	public void testWhiteList() throws ApplicationException {
		SmsPojo spam1 = new SmsPojo();
		spam1.setSender("1346");
		spam1.setMessage("Novaja aktsia ot magazinov Kharkova");
		spam1.setReceived((int) (System.currentTimeMillis() / 1000L));

		SmsPojo spam2 = new SmsPojo();
		spam2.setSender("1343");
		spam2.setMessage("Staraja aktsia ot magazinov Kharkova");
		spam2.setReceived((int) (System.currentTimeMillis() / 1000L));

		SmsPojo notSpam = new SmsPojo();
		notSpam.setSender(SENDER);
		notSpam.setMessage("Let's grab some whisky again");
		notSpam.setReceived((int) (System.currentTimeMillis() / 1000L));

		ISpamFilter filter = new WhiteListSpamFilter(getContentResolver());
		Assert.assertEquals(true, filter.isSpam(spam1));
		Assert.assertEquals(true, filter.isSpam(spam2));
		Assert.assertEquals(false, filter.isSpam(notSpam));
	}
}
