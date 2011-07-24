package com.phdroid.smsb.application;

import java.util.EventListener;

/**
 * EventListener for incoming sms.
 */
public abstract class NewSmsEventListener implements EventListener {
	public abstract void onNewSms(NewSmsEvent newSmsEvent);
}
