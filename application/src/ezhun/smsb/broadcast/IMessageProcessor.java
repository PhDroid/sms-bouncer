package ezhun.smsb.broadcast;

import android.content.ContentResolver;
import ezhun.smsb.SmsPojo;
import ezhun.smsb.exceptions.ApplicationException;

public interface IMessageProcessor {
    /**
     * Processes incoming messages
     * @param messages Incoming messages
     * @param resolver Content resolver
     * @return The number of messages filtered as spam
     */
    public int ProcessMessages(SmsPojo[] messages, ContentResolver resolver);
}
