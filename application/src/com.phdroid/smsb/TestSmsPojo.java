package com.phdroid.smsb;

/**
 * Test replacement fo plain old java object for Sms message.
 */
public class TestSmsPojo extends SmsPojo {
	private String sender;
	private String message;
	private long received;
    private boolean read;
	private boolean markedNotSpamByUser;

    @Override
    public String getSender() {
        return sender;
    }

	@Override
	public long getSenderId() {
		return 0;
	}

	@Override
    public void setSender(String sender) {
        this.sender = sender;
    }

	@Override
	public long getId() {
		return 0;
	}

	@Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public long getReceived() {
        return received;
    }

    @Override
    public void setReceived(long received) {
        this.received = received;
    }

    @Override
    public boolean isRead() {
        return read;
    }

    @Override
    public void setRead(boolean read) {
        this.read = read;
    }

    @Override
    public boolean isMarkedNotSpamByUser() {
        return markedNotSpamByUser;
    }

    @Override
    public void setMarkedNotSpamByUser(boolean markedNotSpamByUser) {
        this.markedNotSpamByUser = markedNotSpamByUser;
    }
}
