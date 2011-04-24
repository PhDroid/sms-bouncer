package ezhun.smsb.filter;

import android.provider.ContactsContract;
import android.test.ProviderTestCase2;

/**
 * Test class for ContactSpamFilter.
 */
public class ContactSpamFilterTest extends ProviderTestCase2 {
	public ContactSpamFilterTest() {
		super(ContactsContract.Contacts.class, ContactsContract.Contacts.CONTENT_URI.getAuthority());
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
	}
}
