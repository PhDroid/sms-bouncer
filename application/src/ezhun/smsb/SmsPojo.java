package ezhun.smsb;

import android.content.ContentValues;
import android.telephony.SmsMessage;
import ezhun.smsb.storage.SmsPojoAdapter;

/**
 * Plain old java object for Sms message.
 */
public class SmsPojo {
	private String sender;
	private String message;
	private long received;
    //todo: store to DB
	private boolean read;
	private boolean markedSpamBySystem;
	private boolean markedSpamByUser;
	private boolean markedNotSpamByUser;

	public SmsPojo() {
	}

	public SmsPojo(SmsMessage msg) {
		sender = msg.getOriginatingAddress();
		message = msg.getMessageBody();
		received = msg.getTimestampMillis();
        read = false;
	}

	public ContentValues toContentValues() {
		return SmsPojoAdapter.toContentValues(this);
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

    public boolean  wasRead(){
        return read;
    }

    public void setRead(boolean r){
        read = r;
    }

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getReceived() {
		return received;
	}

	public void setReceived(long received) {
		this.received = received;
	}

	public boolean isMarkedSpamBySystem() {
		return markedSpamBySystem;
	}

	public void setMarkedSpamBySystem(boolean markedSpamBySystem) {
		this.markedSpamBySystem = markedSpamBySystem;
	}

	public boolean isMarkedSpamByUser() {
		return markedSpamByUser;
	}

	public void setMarkedSpamByUser(boolean markedSpamByUser) {
		this.markedSpamByUser = markedSpamByUser;
	}

	public boolean isMarkedNotSpamByUser() {
		return markedNotSpamByUser;
	}

	public void setMarkedNotSpamByUser(boolean markedNotSpamByUser) {
		this.markedNotSpamByUser = markedNotSpamByUser;
	}

    @Override
    public String toString(){
        return getMessage();
    }
}
