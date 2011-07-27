package com.phdroid.blackjack.ui.notify;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import com.phdroid.smsb.R;
import com.phdroid.smsb.activity.BlockedSmsListActivity;

/**
 * Handles tray notification.
 */
public class TrayNotificationManager {
	private Context context;
	private int icon;

	public TrayNotificationManager(Context context) {
		this.context = context;
		this.icon = R.drawable.icon;
	}

	private Context getContext() {
		return context;
	}

	private int getIcon() {
		return icon;
	}

	public TrayNotification createNotification(String tickerText, String title, String message, long when) {
		return NotificationContainer.getInstance().addNotification(tickerText, title, message, when);
	}

	public void notify(TrayNotification notificationItem, Class activity) {
		// Get a reference to the NotificationManager:
		NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
		//Instantiate the Notification:
		Notification notification = new Notification(getIcon(),
				notificationItem.getTickerText(),
				notificationItem.getWhen());

		//Define the Notification's expanded message and Intent:
		Intent notificationIntent = new Intent(getContext(), activity.getClass());
		PendingIntent contentIntent = PendingIntent.getActivity(getContext(), 0, notificationIntent, 0);

		notification.setLatestEventInfo(
				getContext(),
				notificationItem.getTitle(),
				notificationItem.getMessage(),
				contentIntent);

		//Pass the Notification to the NotificationManager:
		notificationManager.notify(notificationItem.getId(), notification);
		//That's it. Your user has now been notified.
	}

	public void cancel(TrayNotification notificationItem) {
		NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.cancel(notificationItem.getId());
	}
}
