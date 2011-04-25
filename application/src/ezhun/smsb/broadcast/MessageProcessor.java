package ezhun.smsb.broadcast;

import android.content.ContentResolver;
import android.content.ContentValues;
import ezhun.smsb.SmsPojo;
import ezhun.smsb.exceptions.ApplicationException;
import ezhun.smsb.filter.ISpamFilter;
import ezhun.smsb.filter.SmartSpamFilter;
import ezhun.smsb.storage.SmsContentProvider;

public class MessageProcessor implements IMessageProcessor {
    @Override
    public int ProcessMessages(SmsPojo[] messages, ContentResolver resolver) {
	    ISpamFilter spamFiler = new SmartSpamFilter(resolver);
	    ContentValues[] values = new ContentValues[messages.length];
        for (int i=0; i< messages.length; i++){
            SmsPojo message = messages[i];
	        boolean isSpam = false;
	        try {
		        isSpam = spamFiler.isSpam(message);
	        } catch (ApplicationException e) {
		        //todo: implement logging
	        }
	        if(isSpam) {
	            values[i] = message.toContentValues();
	        }
        }
        resolver.bulkInsert(SmsContentProvider.CONTENT_URI, values);
        return messages.length;
    }
}
