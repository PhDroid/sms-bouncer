package com.phdroid.smsb.storage.dao;

import android.content.ContentResolver;
import android.database.Cursor;
import android.telephony.SmsMessage;

/**
 * Facade for DAO operations
 */
public class DaoMaster {
    private ContentResolver contentResolver;

    public ContentResolver getContentResolver() {
        return contentResolver;
    }

    public DaoMaster(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    public SmsMessageSenderEntry insertOrSelectSender(String sender) {
        //todo:check for transactions support in SQLite
        String[] values = {sender};
        Cursor c = null;
        try {
            c = contentResolver.query(SenderContentProvider.CONTENT_URI, null, SmsMessageSenderEntry.VALUE + " = :1", values, null);
            if (c.getCount() != 0) {
                c.moveToFirst();
                return new SmsMessageSenderEntry(c);

            } else {
                SmsMessageSenderEntry s = new SmsMessageSenderEntry(sender);
                contentResolver.insert(SenderContentProvider.CONTENT_URI, s.toContentValues());
                c = contentResolver.query(SenderContentProvider.CONTENT_URI, null, SmsMessageSenderEntry.VALUE + " = :1", values, null);
                c.moveToFirst();
                return new SmsMessageSenderEntry(c);

            }
        } finally {
            if (c != null) {
                c.close();
            }
        }
    }

    public SmsMessageSenderEntry getSenderById(int id) {
        //todo: replace by URI build
        String[] values = {(new Integer(id)).toString()};
        Cursor c = null;
        try {
            c = contentResolver.query(SenderContentProvider.CONTENT_URI, null, SmsMessageSenderEntry._ID + " = :1", values, null);
            c.moveToFirst();
            return new SmsMessageSenderEntry(c);
        } finally {
            if (c != null) {
                c.close();
            }
        }
    }

    public SmsMessageEntry insertMessage(SmsMessage message) {
        String senderText = message.getOriginatingAddress();
        SmsMessageSenderEntry sender = this.insertOrSelectSender(senderText);

        return new SmsMessageEntry(sender, message);
    }
}
