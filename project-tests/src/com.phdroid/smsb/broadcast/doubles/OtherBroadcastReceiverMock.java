package com.phdroid.smsb.broadcast.doubles;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Please, write short description of what this file is for.
 */
public class OtherBroadcastReceiverMock extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		BroadcastCallRegistration.getInstance().registerCall(this, intent);
	}
}
