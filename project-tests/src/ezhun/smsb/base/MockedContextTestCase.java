package ezhun.smsb.base;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.test.AndroidTestCase;
import android.test.IsolatedContext;
import android.test.RenamingDelegatingContext;
import android.test.mock.MockContentResolver;
import android.test.mock.MockContext;

import java.util.Hashtable;

/**
 * This is a base class for all TestCases with ability to use MockContext instead of real one.
 */
public abstract class MockedContextTestCase extends AndroidTestCase {
	IsolatedContext context;
	MockContentResolver resolver;

	@Override
	public Context getContext() {
		return context;
	}

	public ContentResolver getContentResolver() {
		return resolver;
	}

	public abstract Hashtable<Uri, ContentProvider> getTestContentProviders();

	@Override
	public void setUp() throws Exception {
		super.setUp();

		Hashtable<Uri, ContentProvider> settings = getTestContentProviders();

		resolver = new MockContentResolver();
		for (Uri uri : settings.keySet()) {
			ContentProvider provider = settings.get(uri);
			resolver.addProvider(uri.getAuthority(), provider);
		}

		context = new IsolatedContext(resolver, super.getContext());
	}

	protected MockedContextTestCase() {
	}
}
