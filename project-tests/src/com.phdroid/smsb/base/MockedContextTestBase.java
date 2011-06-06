package com.phdroid.smsb.base;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.test.AndroidTestCase;
import android.test.IsolatedContext;
import android.test.mock.MockContentResolver;
import com.phdroid.smsb.base.util.ContextBuilder;

import java.util.Hashtable;

/**
 * This is a base class for all TestCases with ability to use MockContext instead of real one.
 */
public class MockedContextTestBase extends AndroidTestCase {
	public static final String FILENAME_PREFIX = "test.com.phdroid.";
	IsolatedContext context;
	MockContentResolver resolver;


	private Context getProperContext() {
		return super.getContext();
	}

	public Context getMockContext() {
		assertNotNull(context);
		return context;
	}

	@Override
	public Context getContext() {
		return this.getMockContext();
	}

	public ContentResolver getContentResolver() {
		assertNotNull(resolver);
		return resolver;
	}

	/**
	 * Attaches passed content providers to mocked context.
	 *
	 * @param testContentProviders providers to attach
	 */
	public void attachContentProviders(Hashtable<Uri, ContentProvider> testContentProviders) {
		if (testContentProviders == null) {
			return;
		}

		for (Uri uri : testContentProviders.keySet()) {
			ContentProvider provider = testContentProviders.get(uri);
			provider.attachInfo(context, null);
			resolver.addProvider(uri.getAuthority(), provider);
		}
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();

		resolver = new MockContentResolver();

		context = ContextBuilder.createIsolatedContext(getProperContext(), resolver, FILENAME_PREFIX);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	protected MockedContextTestBase() {
	}
}
