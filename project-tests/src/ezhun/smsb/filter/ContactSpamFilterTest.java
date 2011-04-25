package ezhun.smsb.filter;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.test.AndroidTestCase;
import ezhun.smsb.SmsPojo;
import junit.framework.Assert;

import java.util.ArrayList;

/**
 * Test class for ContactSpamFilter.
 */
public class ContactSpamFilterTest extends AndroidTestCase {
	private static String SENDER = "(067) 129-09-45";

	private ContentResolver getContentResolver() {
		return this.getContext().getContentResolver();
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();

		ArrayList<ContentProviderOperation> ops =
				new ArrayList<ContentProviderOperation>();

		int rawContactInsertIndex = ops.size();
		ops.add(ContentProviderOperation.newInsert(RawContacts.CONTENT_URI)
				.withValue(RawContacts.ACCOUNT_TYPE, "com.ezhun.smsb")
				.withValue(RawContacts.ACCOUNT_NAME, "mike_sullivan")
				.build());

		ops.add(ContentProviderOperation.newInsert(Data.CONTENT_URI)
				.withValueBackReference(Data.RAW_CONTACT_ID, rawContactInsertIndex)
				.withValue(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE)
				.withValue(Phone.NUMBER, SENDER)
				.build());
		ops.add(ContentProviderOperation.newInsert(Data.CONTENT_URI)
				.withValueBackReference(Data.RAW_CONTACT_ID, rawContactInsertIndex)
				.withValue(Data.MIMETYPE,
						StructuredName.CONTENT_ITEM_TYPE)
				.withValue(StructuredName.DISPLAY_NAME, "Mike Sullivan")
				.build());

		getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
	}

	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		getContentResolver().delete(ContactsContract.RawContacts.CONTENT_URI, null, null);
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
}
