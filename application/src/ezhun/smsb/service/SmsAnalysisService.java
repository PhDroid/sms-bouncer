package ezhun.smsb.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

/**
 * Please, write short description of what this file is for.
 */
public class SmsAnalysisService extends Service {
	public IBinder onBind(Intent intent) {
		return binder;
	}

	// This is the object that receives interactions from clients.  See
    // RemoteService for a more complete example.
    private final IBinder binder = new LocalBinder();

	/**
     * Class for clients to access.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with
     * IPC.
     */
    public class LocalBinder extends Binder {
        SmsAnalysisService getService() {
            return SmsAnalysisService.this;
        }
    }
}
