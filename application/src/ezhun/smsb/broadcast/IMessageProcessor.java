package ezhun.smsb.broadcast;

import android.content.ContentResolver;
import ezhun.smsb.SmsPojo;
import ezhun.smsb.exceptions.ApplicationException;

public interface IMessageProcessor {
    /**
     * Processes incoming messages
     * @param messages Incoming messages
     * @param resolver Content resolver
     * @return Array of messages filtered as spam
     */
    public SmsPojo[] ProcessMessages(SmsPojo[] messages, ContentResolver resolver);
}
