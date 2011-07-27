package com.phdroid.smsb.activity.base;

import android.app.Activity;
import android.util.Log;
import com.phdroid.smsb.application.ApplicationController;
import com.phdroid.smsb.storage.ApplicationSettings;
import com.phdroid.smsb.storage.dao.Session;
import com.phdroid.smsb.utility.NotificationUtility;

/**
 * Base class for application activities.
 */
public class ActivityBase extends Activity {
	private Session session;
	private ApplicationSettings settings;
	private final Object lock = new Object();

	public void dataBind() {
	}

	public ApplicationController getApplicationContext(){
		return (ApplicationController)super.getApplicationContext();
	}

	public Session getSession() {
		if (session == null) {
			session = new Session(getSettings(), this.getContentResolver());
		}
		return session;
	}

	public ApplicationSettings getSettings() {
		if (settings == null) {
			settings = new ApplicationSettings(this);
		}
		return settings;
	}

	@Override
	protected void onStart() {
		super.onStart();

		NotificationUtility.getInstance(this).clearAll();
	}

	@Override
	protected void onResume() {
		super.onResume();

		synchronized (lock) {
			getApplicationContext().setCurrentActivity(this);
			Log.d("sms-bouncer", String.format("[set] currentActivity = %s", this.getClass().getSimpleName()));
		}
	}

	@Override
	protected void onPause() {
		super.onPause();

		synchronized (lock) {
			ActivityBase activity = getApplicationContext().getCurrentActivity();
			if (activity != null && activity.getClass() == this.getClass()) {
				getApplicationContext().setCurrentActivity(null);
				Log.d("sms-bouncer", "[set] currentActivity = null");
			}
		}
	}
}
