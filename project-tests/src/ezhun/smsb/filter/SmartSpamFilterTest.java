package ezhun.smsb.filter;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.provider.ContactsContract;
import android.test.AndroidTestCase;

import java.util.ArrayList;

/**
 * Test class for SmartSpamFilter.
 */
public class SmartSpamFilterTest extends AndroidTestCase {
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
		ops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
				.withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, "com.ezhun.smsb")
				.withValue(ContactsContract.RawContacts.ACCOUNT_NAME, "mike_sullivan")
				.build());

		ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
				.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
				.withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
				.withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, SENDER)
				.build());
		ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
				.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
				.withValue(ContactsContract.Data.MIMETYPE,
						ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
				.withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, "Mike Sullivan")
				.build());

		getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
	}

	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		getContentResolver().delete(ContactsContract.RawContacts.CONTENT_URI, null, null);
	}
}
