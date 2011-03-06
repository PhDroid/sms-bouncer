package ezhun.smsb.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

/**
 * SmsReceiver listens to broadcast event and triggers if sms is received.
 */
public class SmsReceiver extends BroadcastReceiver {
	private static final String LOG_TAG = "ezhun.smsb";
	/* package */ static final String ACTION =
			"android.provider.Telephony.SMS_RECEIVED";

	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(ACTION)) {
			// if(message starts with SMStretcher recognize BYTE)
			StringBuilder sb = new StringBuilder();

			/* The SMS-Messages are 'hiding' within the extras of the Intent. */
			Bundle bundle = intent.getExtras();
			if (bundle != null) {
				/* Get all messages contained in the Intent*/

				Object[] pdusObj = (Object[]) bundle.get("pdus");
				SmsMessage[] messages = new SmsMessage[pdusObj.length];
				for (int i = 0; i < pdusObj.length; i++) {
					messages[i] = SmsMessage.createFromPdu((byte[])pdusObj[i]);
				}

				/* Feed the StringBuilder with all Messages found. */
				for (SmsMessage currentMessage : messages) {
					sb.append("Received compressed SMSnFrom: ");
					/* Sender-Number */
					sb.append(currentMessage.getDisplayOriginatingAddress());
					sb.append("n----Message----n");
					/* Actual Message-Content */
					sb.append(currentMessage.getDisplayMessageBody());
				}
			}
			/* Logger Debug-Output */
			Log.i(LOG_TAG, "[SMS] onReceiveIntent: " + sb);

			/* Show the Notification containing the Message. */
			Toast.makeText(context, sb.toString(), Toast.LENGTH_LONG).show();
		}
	}

}
