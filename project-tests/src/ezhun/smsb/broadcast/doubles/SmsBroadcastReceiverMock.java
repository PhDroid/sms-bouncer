package ezhun.smsb.broadcast.doubles;

import android.content.Context;
import android.content.Intent;
import android.text.AndroidCharacter;
import android.util.Log;
import ezhun.smsb.broadcast.SmsReceiver;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Mocking sms broadcast receiver class.
 */
public class SmsBroadcastReceiverMock extends SmsReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		BroadcastCallRegistration.getInstance().registerCall(this, intent);
	}
}
