package com.phdroid.smsb.activity.notify;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains notifications, provides them with IDs.
 */
public class NotificationContainer {
	private static NotificationContainer ourInstance = new NotificationContainer();

	public static NotificationContainer getInstance() {
		return ourInstance;
	}

	List<TrayNotification> items;
	final Object lock = new Object();

	private NotificationContainer() {
		items = new ArrayList<TrayNotification>();
	}

	public TrayNotification addNotification(String tickerText, String title, String message) {
		return addNotification(tickerText, title, message, System.currentTimeMillis());
	}

	public TrayNotification addNotification(String tickerText, String title, String message, long when) {
		synchronized (lock) {
			int id = items.size();
			TrayNotification n = new TrayNotification(id, tickerText, title, message, when);
			items.add(n);
			return n;
		}
	}
}
