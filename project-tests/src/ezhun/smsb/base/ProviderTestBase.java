package ezhun.smsb.base;

import android.content.ContentProvider;
import android.net.Uri;

import javax.naming.OperationNotSupportedException;
import java.util.Hashtable;

/**
 * Base class for all provider test cases.
 */
public abstract class ProviderTestBase extends MockedContextTestBase {
	/**
	 * Prepares list of content providers and corresponding Uri's for context setup.
	 *
	 * @return content providers and corresponding Uri's
	 * @throws javax.naming.OperationNotSupportedException
	 *
	 */
	@SuppressWarnings({"JavaDoc"})
	public abstract Hashtable<Uri, ContentProvider> getTestContentProviders() throws OperationNotSupportedException;

	@Override
	public void setUp() throws Exception {
		super.setUp();

		try {
			Hashtable<Uri, ContentProvider> settings = getTestContentProviders();
			this.attachContentProviders(settings);
		} catch (OperationNotSupportedException e) {
			//do nothing
			//for one minute =)
		}
	}

	protected ProviderTestBase() {
	}
}
