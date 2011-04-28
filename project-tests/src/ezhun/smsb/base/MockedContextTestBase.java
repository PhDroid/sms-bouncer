package ezhun.smsb.base;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
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
public class MockedContextTestBase extends AndroidTestCase {
	private class ResourcefulMockContext extends MockContext {
		@Override
		public Resources getResources() {
			return getProperContext().getResources();
		}
	}

	private Context getProperContext() {
		return super.getContext();
	}

	IsolatedContext context;
	MockContentResolver resolver;

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
		final String filenamePrefix = "test.ezhun.";
		RenamingDelegatingContext targetContextWrapper = new RenamingDelegatingContext(
				new ResourcefulMockContext(), // The context that most methods are delegated to
				getProperContext(), // The context that file methods are delegated to
				filenamePrefix);
		context = new IsolatedContext(resolver, targetContextWrapper);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	protected MockedContextTestBase() {
	}
}
