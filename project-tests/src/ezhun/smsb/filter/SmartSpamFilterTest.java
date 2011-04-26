package ezhun.smsb.filter;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.provider.ContactsContract;
import android.test.AndroidTestCase;
import ezhun.smsb.SmsPojo;
import ezhun.smsb.base.MockedContextTestCase;
import ezhun.smsb.exceptions.ApplicationException;
import ezhun.smsb.filter.doubles.PhoneContentProviderFake;
import ezhun.smsb.storage.SmsContentProvider;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import junit.framework.Assert;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Test class for SmartSpamFilter.
 */
public class SmartSpamFilterTest extends MockedContextTestCase {
	private static String SENDER = "(067) 129-09-45";

	@Override
	public Hashtable<Uri, ContentProvider> getTestContentProviders() {
		PhoneContentProviderFake phoneContentProvider = new PhoneContentProviderFake();

		Hashtable<Uri, ContentProvider> settings = new Hashtable<Uri, ContentProvider>();
		settings.put(SmsContentProvider.CONTENT_URI, new SmsContentProvider());
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

	public void testKnownNumber() throws Exception {
		SmsPojo message = new SmsPojo();
		message.setSender(SENDER);
		message.setReceived((int) (System.currentTimeMillis() / 1000L));
		message.setMessage("I am not a SPAM message");

		ISpamFilter filter = new ContactSpamFilter(getContentResolver());
		boolean isSpam = filter.isSpam(message);
		Assert.assertEquals(false, isSpam);
	}

	public void testUnknownNumber() throws Exception {
		SmsPojo message = new SmsPojo();
		message.setSender("(097) 112-33-26");
		message.setReceived((int) (System.currentTimeMillis() / 1000L));
		message.setMessage("I am SPAM message");

		ISpamFilter filter = new ContactSpamFilter(getContentResolver());
		boolean isSpam = filter.isSpam(message);
		Assert.assertEquals(true, isSpam);
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
