package com.phdroid.smsb.filter;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.net.Uri;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Data;
import com.phdroid.smsb.SmsPojo;
import com.phdroid.smsb.TestSmsPojo;
import com.phdroid.smsb.base.ProviderTestBase;
import com.phdroid.smsb.exceptions.ApplicationException;
import com.phdroid.smsb.filter.doubles.PhoneContentProviderFake;
import com.phdroid.smsb.storage.dao.*;
import junit.framework.Assert;

import java.util.Hashtable;

/**
 * Test class for SmartSpamFilter.
 */
public class SmartSpamFilterTest extends ProviderTestBase {
	private static String SENDER = "(067) 129-09-45";

	@Override
	public Hashtable<Uri, ContentProvider> getTestContentProviders() {
		PhoneContentProviderFake phoneContentProvider = new PhoneContentProviderFake();

		Hashtable<Uri, ContentProvider> settings = new Hashtable<Uri, ContentProvider>();
		settings.put(SmsContentProvider.CONTENT_URI, new SmsContentProvider());
		settings.put(SenderContentProvider.CONTENT_URI, new SenderContentProvider());
		settings.put(Data.CONTENT_URI, phoneContentProvider);
		settings.put(Phone.CONTENT_URI, phoneContentProvider);
		return settings;
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();

		//prepare for contact spam filter
		ContentValues values = new ContentValues();
		values.put(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE);
		values.put(Phone.NUMBER, SENDER);
		getContentResolver().insert(Data.CONTENT_URI, values);

		//prepare for white list filter
        DaoMaster m = new DaoMaster(getContentResolver());
        SmsMessageSenderEntry spamSender = m.insertOrSelectSender("1346");
        SmsMessageEntry spam = new SmsMessageEntry();
		spam.setSenderId(spamSender.getId());
		spam.setMessage("Novaja aktsia ot magazinov Kharkova");
		spam.setReceived((int) (System.currentTimeMillis() / 1000L));
		getContentResolver().insert(SmsContentProvider.CONTENT_URI, spam.toContentValues());

        SmsMessageSenderEntry sender = m.insertOrSelectSender(SENDER);
		SmsMessageEntry notSpam = new SmsMessageEntry();
		notSpam.setSenderId(sender.getId());
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

	public void testWhiteList() throws ApplicationException {
		TestSmsPojo spam1 = new TestSmsPojo();
		spam1.setSender("1346");
		spam1.setMessage("Novaja aktsia ot magazinov Kharkova");
		spam1.setReceived((int) (System.currentTimeMillis() / 1000L));

		TestSmsPojo spam2 = new TestSmsPojo();
		spam2.setSender("1343");
		spam2.setMessage("Staraja aktsia ot magazinov Kharkova");
		spam2.setReceived((int) (System.currentTimeMillis() / 1000L));

		TestSmsPojo notSpam = new TestSmsPojo();
		notSpam.setSender(SENDER);
		notSpam.setMessage("Let's grab some whisky again");
		notSpam.setReceived((int) (System.currentTimeMillis() / 1000L));

		ISpamFilter filter = new WhiteListSpamFilter(getContentResolver());
		Assert.assertEquals(true, filter.isSpam(spam1));
		Assert.assertEquals(true, filter.isSpam(spam2));
		Assert.assertEquals(false, filter.isSpam(notSpam));
	}
}
