package com.phdroid.smsb;

import android.content.ContentResolver;
import com.phdroid.smsb.storage.dao.DaoMaster;
import com.phdroid.smsb.storage.dao.SmsMessageEntry;
import com.phdroid.smsb.storage.dao.SmsMessageSenderEntry;

/**
 * Plain old java object for Sms message.
 */
public class SmsPojo {
	private SmsMessageEntry innerEntry;
    private DaoMaster daoMaster;

    protected SmsPojo() {
    }

	public SmsPojo(ContentResolver contentResolver, SmsMessageEntry entry) {
        innerEntry = entry;
        this.daoMaster = new DaoMaster(contentResolver);
	}

	public String getSender() {
        SmsMessageSenderEntry sender = this.daoMaster.getSenderById(innerEntry.getSenderId());
		return sender.getValue();
	}

	public void setSender(String sender) {
		SmsMessageSenderEntry senderEntry = this.daoMaster.insertOrSelectSender(sender);
        innerEntry.setSenderId(senderEntry.getId());
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
