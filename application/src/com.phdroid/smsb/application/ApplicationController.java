package com.phdroid.smsb.application;

import android.app.Application;
import com.phdroid.smsb.SmsPojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Inheritor of Application. Accessed via getApplicationContext()
 */
public class ApplicationController extends Application {
	private List<NewSmsEventListener> listeners;

	public void attachNewSmsListener(NewSmsEventListener listener) {
		this.listeners.add(listener);
	}

	public void detachNewSmsListener(NewSmsEventListener listener) {
		this.listeners.remove(listener);
	}

	public void raiseNewSmsEvent(SmsPojo sms) {
		for (NewSmsEventListener listener : this.listeners) {
			listener.onNewSms(sms);
		}
	}

	public ApplicationController() {
		this.listeners = new ArrayList<NewSmsEventListener>();
	}
}
