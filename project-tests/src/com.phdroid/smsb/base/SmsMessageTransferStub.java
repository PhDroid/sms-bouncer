package com.phdroid.smsb.base;

import android.telephony.SmsMessage;
import com.phdroid.smsb.SmsPojo;
import com.phdroid.smsb.utility.SmsMessageTransferObject;

/**
 * Please, write short description of what this file is for.
 */
public class SmsMessageTransferStub extends SmsMessageTransferObject {
	private SmsPojo innerMessage = null;

	public SmsMessageTransferStub(SmsPojo message) {
		super(null);
		innerMessage = message;
	}

	@Override
	public String getSender() {
		return innerMessage.getSender();
	}

	@Override
	public boolean isRead() {
		return innerMessage.isRead();
	}

	@Override
	public String getMessage() {
		return innerMessage.getMessage();
	}

	@Override
	public long getReceived() {
		return innerMessage.getReceived();
	}
}
