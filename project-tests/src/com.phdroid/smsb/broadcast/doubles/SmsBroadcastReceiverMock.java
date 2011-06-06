package com.phdroid.smsb.broadcast.doubles;

import android.content.Context;
import android.content.Intent;
import com.phdroid.smsb.broadcast.SmsReceiver;

/**
 * Mocking sms broadcast receiver class.
 */
public class SmsBroadcastReceiverMock extends SmsReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		BroadcastCallRegistration.getInstance().registerCall(this, intent);
	}
}
