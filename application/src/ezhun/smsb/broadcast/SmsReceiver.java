package ezhun.smsb.broadcast;

import android.app.NotificationManager;
import android.app.Service;
import android.content.*;
import android.os.Bundle;
import android.telephony.SmsMessage;
import ezhun.smsb.SmsPojo;
import ezhun.smsb.exceptions.ApplicationException;

/**
 * SmsReceiver listens to broadcast event and triggers if SMS is received.
 */
public class SmsReceiver extends BroadcastReceiver {
	private static final String LOG_TAG = "ezhun.smsb";
	/* package */ static final String ACTION =
			"android.provider.Telephony.SMS_RECEIVED";

    protected int mSpamMessagesCount = 0;

	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(ACTION)) {
			/* The SMS-Messages are 'hiding' within the extras of the Intent. */
			Bundle bundle = intent.getExtras();
			if (bundle != null) {
				/* Get all messages contained in the Intent*/
				Object[] pdusObj = (Object[]) bundle.get("pdus");

				ContentResolver c = context.getContentResolver();
                SmsPojo[] messages = new SmsPojo[pdusObj.length];
                for (int i = 0; i < pdusObj.length; i++) {
                    SmsMessage msg = SmsMessage.createFromPdu((byte[])pdusObj[i]);
                    SmsPojo sms = new SmsPojo(msg);
                    messages[i] = sms;
                }
				int spamMessageCount = getMessageProcessor().ProcessMessages(messages, c);
                //someone gave 3 apples to Buratino. Buratino ate one.
				//how many apples does Buratino have?
				//Answer:Nobody knows.

				mSpamMessagesCount += spamMessageCount;

				//that's why I didn't use mSpamMessageCount. Somebody could change property from the outer world.
				if (spamMessageCount > 0) {
					//aborting broadcast. Using it with a priority tag should prevent anyone to receive these spam messages.
					abortBroadcast();
				}

                NotificationManager notifier = (NotificationManager)context.getSystemService(Service.NOTIFICATION_SERVICE);
			}
		}
	}

    protected IMessageProcessor getMessageProcessor(){
        return new MessageProcessor();
    }
}