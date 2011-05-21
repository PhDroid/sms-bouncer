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
	private Context context;
	private int icon;

	public TrayNotifier(Context context) {
		this.context = context;
		this.icon = R.drawable.icon;
	}

	private Context getContext() {
		return context;
	}

	private int getIcon() {
		return icon;
	}

	public void Notify(TrayNotification notificationItem) {
		// Get a reference to the NotificationManager:
		NotificationManager mNotificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
		//Instantiate the Notification:
		Notification notification = new Notification(getIcon(),
				notificationItem.getTickerText(),
				notificationItem.getWhen());

		//Define the Notification's expanded message and Intent:
		Intent notificationIntent = new Intent(getContext(), BlockedSmsListActivity.class);
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);

		notification.setLatestEventInfo(
				getContext(),
				notificationItem.getTitle(),
				notificationItem.getMessage(),
				contentIntent);

		//Pass the Notification to the NotificationManager:
		mNotificationManager.notify(notificationItem.getId(), notification);
		//That's it. Your user has now been notified.
	}
}