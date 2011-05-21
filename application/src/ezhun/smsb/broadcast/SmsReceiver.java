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
				SmsPojo[] spamMessages = getMessageProcessor().ProcessMessages(messages, c);
				int spamMessageCount = spamMessages.length;
				mSpamMessagesCount += spamMessageCount;

				if (spamMessageCount > 0) {
					//aborting broadcast. Using it with a priority tag should prevent anyone to receive these spam messages.
					abortBroadcast();
					ApplicationSettings settings = new ApplicationSettings(context);

					if (settings.showDisplayNotification()) {
						String title;
						String message;

						switch (spamMessageCount) {
							case 1:
								//265 Anton prosil zapomnit' chislo
								title = String.format("Blocked message from %s", spamMessages[0].getSender());
								message = spamMessages[0].getMessage();
								break;
							case 2:
								title = "Blocked messages (2)";
								message = String.format("Blocked messages from %s and %s",
										spamMessages[0].getSender(),
										spamMessages[1].getSender());
								break;
							default:
								title = String.format("Blocked messages (%d)", spamMessageCount);
								message = String.format("Blocked messages from %s, %s and others",
										spamMessages[0].getSender(),
										spamMessages[1].getSender());

						}
						TrayNotificationManager t = new TrayNotificationManager(context);
						t.Notify("Sms-Bouncer",
								title,
								message);
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