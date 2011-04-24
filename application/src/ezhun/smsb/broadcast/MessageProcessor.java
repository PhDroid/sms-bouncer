package ezhun.smsb.broadcast;

import android.content.ContentResolver;
import android.content.ContentValues;
import ezhun.smsb.SmsPojo;
import ezhun.smsb.storage.SmsContentProvider;

public class MessageProcessor implements IMessageProcessor {
    @Override
    public int ProcessMessages(SmsPojo[] messages, ContentResolver resolver) {
        ContentValues[] values = new ContentValues[messages.length];
        for (int i=0; i< messages.length; i++){
            values[i] = messages[i].toContentValues();
        }
        resolver.bulkInsert(SmsContentProvider.CONTENT_URI, values);
        return messages.length;
    }
}
