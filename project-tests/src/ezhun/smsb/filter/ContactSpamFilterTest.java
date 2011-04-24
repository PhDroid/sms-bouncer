package ezhun.smsb.filter;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.provider.ContactsContract;
import android.test.AndroidTestCase;
import android.test.ProviderTestCase2;

import java.util.ArrayList;

/**
 * Test class for ContactSpamFilter.
 */
public class ContactSpamFilterTest extends AndroidTestCase {
	private ContentResolver getContentResolver() {
		return this.getContext().getContentResolver();
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();

		ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
		int rawContactInsertIndex = ops.size();

		ops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
				.withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
				.withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
				.build());
		ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
				.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
				.withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
				.withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, "9X-XXXXXXXXX")
				.build());
		ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
				.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
				.withValue(ContactsContract.Data.MIMETYPE,
						ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
				.withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, "Mike Sullivan")
				.build());
		ContentProviderResult[] res = getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
	}
}
