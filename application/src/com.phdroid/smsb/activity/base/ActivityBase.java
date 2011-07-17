package com.phdroid.smsb.activity.base;

import android.app.Activity;
import com.phdroid.smsb.storage.ApplicationSettings;
import com.phdroid.smsb.storage.dao.Session;

/**
 * Base class for application activities.
 */
public class ActivityBase extends Activity {
	private Session session;
	private ApplicationSettings settings;

	public void dataBind() {
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
}
