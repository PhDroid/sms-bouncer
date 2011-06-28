package com.phdroid.smsb.application;

import com.phdroid.smsb.SmsPojo;

import java.util.EventObject;

/**
 * Event that is raised when new sms comes.
 */
public class NewSmsEvent extends EventObject {
	private SmsPojo sms;

	public SmsPojo getSms() {
		return sms;
	}

	public NewSmsEvent(Object sender, SmsPojo sms) {
		super(sender);
		this.sms = sms;
	}
}
