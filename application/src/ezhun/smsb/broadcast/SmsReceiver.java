package ezhun.smsb.broadcast;

import android.content.*;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;
import ezhun.smsb.SmsPojo;
import ezhun.smsb.storage.SmsContentProvider;

/**
 * SmsReceiver listens to broadcast event and triggers if SMS is received.
 */
public class SmsReceiver extends BroadcastReceiver {
	private static final String LOG_TAG = "ezhun.smsb";
	/* package */ static final String ACTION =
			"android.provider.Telephony.SMS_RECEIVED";

	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(ACTION)) {
			/* The SMS-Messages are 'hiding' within the extras of the Intent. */
			Bundle bundle = intent.getExtras();
			if (bundle != null) {
				/* Get all messages contained in the Intent*/

				Object[] pdusObj = (Object[]) bundle.get("pdus");

				ContentResolver c = context.getContentResolver();
				ContentValues[] values = new ContentValues[pdusObj.length];

				for (int i = 0; i < pdusObj.length; i++) {
					SmsMessage msg = SmsMessage.createFromPdu((byte[])pdusObj[i]);
					SmsPojo sms = new SmsPojo(msg);
					values[i] = sms.toContentValues();
				}
				c.bulkInsert(SmsContentProvider.CONTENT_URI, values);
			}
		}
	}

}