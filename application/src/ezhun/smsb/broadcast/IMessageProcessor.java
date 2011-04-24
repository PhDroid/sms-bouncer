package ezhun.smsb.broadcast;

import android.content.ContentResolver;
import ezhun.smsb.SmsPojo;

/**
 * Created by IntelliJ IDEA.
 * User: unkot
 * Date: 21.04.11
 * Time: 16:57
 * To change this template use File | Settings | File Templates.
 */
public interface IMessageProcessor {
    /**
     * Processes incoming messages
     * @param messages Incoming messages
     * @param resolver Content resolver
     * @return The number of messages filtered as spam
     */
    public int ProcessMessages(SmsPojo[] messages, ContentResolver resolver);
}
