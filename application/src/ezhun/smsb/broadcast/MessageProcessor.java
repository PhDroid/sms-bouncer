package ezhun.smsb.broadcast;

import android.content.ContentResolver;
import android.content.ContentValues;
import ezhun.smsb.SmsPojo;
import ezhun.smsb.exceptions.ApplicationException;
import ezhun.smsb.filter.ISpamFilter;
import ezhun.smsb.filter.SmartSpamFilter;
import ezhun.smsb.storage.SmsContentProvider;

import java.util.ArrayList;

public class MessageProcessor implements IMessageProcessor {
    @Override
    public int ProcessMessages(SmsPojo[] messages, ContentResolver resolver) {
	    ISpamFilter spamFiler = new SmartSpamFilter(resolver);
	    ArrayList<ContentValues> values = new ArrayList<ContentValues>();
	    for (SmsPojo message : messages) {
		    boolean isSpam = false;
		    try {
			    isSpam = spamFiler.isSpam(message);
		    } catch (ApplicationException e) {
			    //todo: implement logging
		    }
		    if (isSpam) {
			    values.add(message.toContentValues());
		    }
	    }
	    if (values.size() == 0) {
		    return 0;
	    } else {
			ContentValues[] valuesArray = new ContentValues[values.size()];
            resolver.bulkInsert(SmsContentProvider.CONTENT_URI, values.toArray(valuesArray));
            return messages.length;
	    }
    }
}
