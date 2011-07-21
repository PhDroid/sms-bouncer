package com.phdroid.smsb.storage.dao;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * DAO for SmsSender
 */
public class SmsMessageSenderEntry {
    public static final String _ID = "_id";
    public static final String VALUE = "value";
    public static final String IN_WHITE_LIST = "white_list";

    private long id;
    private String value;
    private boolean inWhiteList;

    public SmsMessageSenderEntry(String value) {
        this.value = value;
    }

    public SmsMessageSenderEntry(Cursor c) {
        this.id = c.getInt(c.getColumnIndex(SmsMessageSenderEntry._ID));
        this.value = c.getString(c.getColumnIndex(SmsMessageSenderEntry.VALUE));
        this.inWhiteList = c.getInt(c.getColumnIndex(SmsMessageSenderEntry.IN_WHITE_LIST)) == 1;
	}

    public ContentValues toContentValues() {
		ContentValues values = new ContentValues();
		values.put(VALUE, this.getValue());
		values.put(IN_WHITE_LIST, this.isInWhiteList());
		return values;
	}

    public long getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isInWhiteList() {
        return inWhiteList;
    }

    public void setInWhiteList(boolean inWhiteList) {
        this.inWhiteList = inWhiteList;
    }
}
