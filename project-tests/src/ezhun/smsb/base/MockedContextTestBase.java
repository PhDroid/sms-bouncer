package ezhun.smsb.base;

import android.content.*;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
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

	private class BroadcastContext extends IsolatedContext {
		private static final String ANDROID_PERMISSION_RECEIVE_SMS = "android.permission.RECEIVE_SMS";
		Context broadcastContext;

		public BroadcastContext(ContentResolver resolver, Context targetContext, Context broadcastContext) {
			super(resolver, targetContext);
			this.broadcastContext = broadcastContext;
		}

		public Context getBroadcastContext() {
			assertNotNull(broadcastContext);
			return broadcastContext;
		}

		@Override
		public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter) {
			return getBroadcastContext().registerReceiver(receiver, filter);
		}

		@Override
		public PackageManager getPackageManager() {
			return getBroadcastContext().getPackageManager();
		}

		@Override
		public void unregisterReceiver(BroadcastReceiver receiver) {
			getBroadcastContext().unregisterReceiver(receiver);
		}

		@Override
		public void sendOrderedBroadcast(Intent intent, String receiverPermission) {
			getBroadcastContext().sendOrderedBroadcast(intent, receiverPermission);
		}

		@Override
		public void sendBroadcast(Intent intent, String receiverPermission) {
			getBroadcastContext().sendBroadcast(intent, receiverPermission);
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
		final String filenamePrefix = "test.ezhun.";
		RenamingDelegatingContext targetContextWrapper = new RenamingDelegatingContext(
				new ResourcefulMockContext(), // The context that most methods are delegated to
				getProperContext(), // The context that file methods are delegated to
				filenamePrefix);
		context = new BroadcastContext(resolver, targetContextWrapper, getProperContext());
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	protected MockedContextTestBase() {
	}
}
