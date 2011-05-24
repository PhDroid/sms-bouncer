package ezhun.smsb.base.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.test.mock.MockContext;

import java.util.HashMap;
import java.util.Map;

/**
 * MockContext descendant with resource capabilities.
 */
public class ResourcefulMockContext extends MockContext {
	private Map<String, SharedPreferences> inMemoryPreferences;
	private Context properContext;

	private Context getProperContext() {
		return properContext;
	}

	public ResourcefulMockContext(Context properContext) {
		inMemoryPreferences = new HashMap<String, SharedPreferences>();
		this.properContext = properContext;
	}

	@Override
	public Resources getResources() {
		return getProperContext().getResources();
	}

	@Override
	public synchronized SharedPreferences getSharedPreferences(String name, int mode) {
		if (!inMemoryPreferences.containsKey(name)) {
			inMemoryPreferences.put(name, new InMemoryPreferences());
		}
		return inMemoryPreferences.get(name);
	}
}