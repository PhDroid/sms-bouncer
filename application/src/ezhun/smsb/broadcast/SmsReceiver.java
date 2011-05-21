package ezhun.smsb.broadcast;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import ezhun.smsb.SmsPojo;
import ezhun.smsb.activity.notify.TrayNotificationManager;
import ezhun.smsb.storage.ApplicationSettings;

/**
 * SmsReceiver listens to broadcast event and triggers if SMS is received.
 */
public class SmsReceiver extends BroadcastReceiver {
	private static final String LOG_TAG = "ezhun.smsb";
	/* package */ static final String ACTION =
			"android.provider.Telephony.SMS_RECEIVED";

	private int mSpamMessagesCount = 0;

	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(ACTION)) {
			/* The SMS-Messages are 'hiding' within the extras of the Intent. */
			Bundle bundle = intent.getExtras();
			if (bundle != null) {
				/* Get all messages contained in the Intent*/
				Object[] pdusObj = (Object[]) bundle.get("pdus");

				ContentResolver c = context.getContentResolver();
				SmsPojo[] messages = ConvertMessages(pdusObj);
				int spamMessageCount = getMessageProcessor().ProcessMessages(messages, c);
				mSpamMessagesCount += spamMessageCount;

				if (spamMessageCount > 0) {
					//aborting broadcast. Using it with a priority tag should prevent anyone to receive these spam messages.
					abortBroadcast();
					ApplicationSettings settings = new ApplicationSettings(context);

					if (settings.getDisplayNotification()) {
						TrayNotificationManager t = new TrayNotificationManager(context);
						t.Notify("Sms-Bouncer",
								String.format("Blocked %s messages", spamMessageCount),
								"Blocked messages from:");
					}
				}
			}
		}
	}

	protected SmsPojo[] ConvertMessages(Object[] pdusObj) {
		SmsPojo[] messages = new SmsPojo[pdusObj.length];
		for (int i = 0; i < pdusObj.length; i++) {
			SmsMessage msg = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
			SmsPojo sms = new SmsPojo(msg);
			messages[i] = sms;
		}
		return messages;
	}

	protected IMessageProcessor getMessageProcessor() {
		return new MessageProcessor();
	}

	protected int getSpamMessagesCount() {
		return mSpamMessagesCount;
	}
}