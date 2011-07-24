package com.phdroid.smsb.activity;

import com.phdroid.smsb.storage.dao.SmsMessageSenderEntry;

import java.util.EventListener;

/**
 * Event raised when delete button is clicked.
 */
public abstract class OnDeleteSenderListener implements EventListener {
	public abstract void onDeleteSender(SmsMessageSenderEntry sender);
}
