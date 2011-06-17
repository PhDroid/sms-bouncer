package com.phdroid.smsb.storage.cache;

/**
 * Side cache implementation class for SmsMessages
 * */
public class SmsMessageCache {
    private static SmsMessageCache ourInstance = new SmsMessageCache();

    public static SmsMessageCache getInstance() {
        return ourInstance;
    }

    private SmsMessageCache() {
    }
}
