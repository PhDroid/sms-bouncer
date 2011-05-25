package ezhun.smsb.base.util;

import android.content.ContentResolver;
import android.content.Context;
import android.test.IsolatedContext;
import android.test.RenamingDelegatingContext;

/**
 * Creates complex contexts.
 */
public class ContextBuilder {
	public static IsolatedContext createIsolatedContext(Context properContext, ContentResolver contentResolver, String filePrefix) {
		RenamingDelegatingContext targetContextWrapper = new RenamingDelegatingContext(
				new ResourcefulMockContext(properContext), // The context that most methods are delegated to
				properContext, // The context that file methods are delegated to
				filePrefix);
		return new IsolatedBroadcastContext(contentResolver, targetContextWrapper, properContext);
	}
}
