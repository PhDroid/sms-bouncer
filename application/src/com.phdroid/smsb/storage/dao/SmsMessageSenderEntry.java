package com.phdroid.smsb.storage.dao;

/**
 * DAO for SmsSender
 */
public class SmsMessageSenderEntry {
    public static final String _ID = "_id";
    public static final String VALUE = "value";
    public static final String IN_WHITE_LIST = "white_list";

    private int id;
    private String value;
    private boolean inWhiteList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
