package ezhun.smsb.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Spam analysis make take a while, but this should not be visible to end-user.
 * AnalysisService does some tricky logic while the system continues operating.
 */
public class AnalysisService extends Service {
	public IBinder onBind(Intent intent) {
		return null;
	}
}
