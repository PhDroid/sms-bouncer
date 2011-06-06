package com.phdroid.smsb.base.util;

import android.content.*;
import android.content.pm.PackageManager;
import android.test.IsolatedContext;
import junit.framework.Assert;

/**
 * Broadcast Context isolated from the rest of the system.
 */
public class IsolatedBroadcastContext extends IsolatedContext {
	Context broadcastContext;

	public IsolatedBroadcastContext(ContentResolver resolver, Context targetContext, Context broadcastContext) {
		super(resolver, targetContext);
		this.broadcastContext = broadcastContext;
	}

	public Context getBroadcastContext() {
		Assert.assertNotNull(broadcastContext);
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
