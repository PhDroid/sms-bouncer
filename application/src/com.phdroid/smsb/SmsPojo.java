package com.phdroid.smsb;

import android.content.ContentResolver;
import com.phdroid.smsb.exceptions.NotSupportedMethodException;
import com.phdroid.smsb.storage.dao.DaoMaster;
import com.phdroid.smsb.storage.dao.SmsMessageEntry;
import com.phdroid.smsb.storage.dao.SmsMessageSenderEntry;

/**
 * Plain old java object for Sms message.
 */
public class SmsPojo {
	private SmsMessageEntry innerEntry;

    protected SmsPojo() {
    }

	public SmsPojo(SmsMessageEntry entry) {
        innerEntry = entry;
	}

	public String getSender() {
        return innerEntry.getSender();
	}

	public void setSender(String sender) throws NotSupportedMethodException {
		throw new NotSupportedMethodException();
	}

    public boolean isRead(){
        return innerEntry.isRead();
    }

    public void setRead(boolean r){
        innerEntry.setRead(r);
    }

	public String getMessage() {
		return innerEntry.getMessage();
	}

	public void setMessage(String message) {
		innerEntry.setMessage(message);
	}

	public long getReceived() {
		return innerEntry.getReceived();
	}

	public void setReceived(long received) {
		innerEntry.setReceived(received);
	}

	public boolean isMarkedNotSpamByUser() {
		return innerEntry.isMarkedNotSpamByUser();
	}

	public void setMarkedNotSpamByUser(boolean markedNotSpamByUser) {
		innerEntry.setMarkedNotSpamByUser(markedNotSpamByUser);
	}

    @Override
    public String toString(){
        return getMessage();
    }
}
