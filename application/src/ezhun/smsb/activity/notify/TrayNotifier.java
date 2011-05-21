package ezhun.smsb.activity.notify;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import ezhun.smsb.R;
import ezhun.smsb.activity.BlockedSmsListActivity;

/**
 * Handles tray notification.
 */
public class TrayNotifier {
	public void Notify(Context context) {
		// Get a reference to the NotificationManager:
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(ns);
		//Instantiate the Notification:
		int icon = R.drawable.icon;
		CharSequence tickerText = "Hello";
		long when = System.currentTimeMillis();

		Notification notification = new Notification(icon, tickerText, when);
		//Define the Notification's expanded message and Intent:
		CharSequence contentTitle = "My notification";
		CharSequence contentText = "Hello World!";
		Intent notificationIntent = new Intent(context, BlockedSmsListActivity.class);
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);

		notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
		//Pass the Notification to the NotificationManager:
		final int HELLO_ID = 1;

		mNotificationManager.notify(HELLO_ID, notification);
		//That's it. Your user has now been notified.
	}
}
