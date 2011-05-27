package ezhun.smsb.base.util;

import android.content.Context;
import android.test.mock.MockApplication;

/**
 * Application that provides access to context
 */
public class MockContextApplication extends MockApplication {
	private Context context;

	public MockContextApplication(Context context) {
		this.context = context;
	}

	@Override
	public Context getApplicationContext() {
		return context;
	}
}
