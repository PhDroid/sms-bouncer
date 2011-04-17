package ezhun.smsb.service;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

/**
 * Please, write short description of what this file is for.
 */
public class SmsAnalysisServiceConnection implements ServiceConnection {
	private SmsAnalysisService boundService;

	public void onServiceConnected(ComponentName className, IBinder service) {
		// This is called when the connection with the service has been
		// established, giving us the service object we can use to
		// interact with the service.  Because we have bound to a explicit
		// service that we know is running in our own process, we can
		// cast its IBinder to a concrete class and directly access it.
		boundService = ((SmsAnalysisService.LocalBinder) service).getService();
	}

	public void onServiceDisconnected(ComponentName className) {
		// This is called when the connection with the service has been
		// unexpectedly disconnected -- that is, its process crashed.
		// Because it is running in our same process, we should never
		// see this happen.
		boundService = null;
	}
}
