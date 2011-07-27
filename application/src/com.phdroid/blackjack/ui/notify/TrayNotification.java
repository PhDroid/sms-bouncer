package com.phdroid.blackjack.ui.notify;

/**
 * UI notification object.
 */
public class TrayNotification {
	private int id;
	private String tickerText;
	private String title;
	private String message;
	private long when;

	TrayNotification(int id, String tickerText, String title, String message, long when) {
		this.id = id;
		this.tickerText = tickerText;
		this.title = title;
		this.message = message;
		this.when = when;
	}

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getMessage() {
		return message;
	}

	public String getTickerText() {
		return tickerText;
	}

	public long getWhen() {
		return when;
	}

	public void setTickerText(String tickerText) {
		this.tickerText = tickerText;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setWhen(long when) {
		this.when = when;
	}
}
