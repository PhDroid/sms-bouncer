package com.phdroid.smsb.storage;

public enum SmsAction {
	MarkedAsNotSpam (0),
	Deleted (1),
	Read (2);

	private final int index;

	SmsAction(int index) {
		this.index = index;
	}

	public int index() {
		return index;
	}
}
