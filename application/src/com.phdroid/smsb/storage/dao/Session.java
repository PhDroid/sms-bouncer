package com.phdroid.smsb.storage.dao;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.telephony.SmsMessage;
import com.phdroid.smsb.SmsPojo;
import com.phdroid.smsb.storage.ApplicationSettings;
import com.phdroid.smsb.storage.SenderProvider;
import com.phdroid.smsb.storage.SmsAction;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;

/**
 * CRUD operations manager.
 */
public class Session {
	private ContentResolver contentResolver;
	private ApplicationSettings settings;

	public ContentResolver getContentResolver() {
		return contentResolver;
	}

	public Session(ApplicationSettings settings, ContentResolver contentResolver) {
		this.contentResolver = contentResolver;
		this.settings = settings;
	}

	public List<SmsMessageSenderEntry> getSenderList(boolean whiteListOnly) {
		List<SmsMessageSenderEntry> senders = new ArrayList<SmsMessageSenderEntry>();

		Cursor cursor = contentResolver.query(
				SenderContentProvider.CONTENT_URI,
				null,
				whiteListOnly ? SmsMessageSenderEntry.IN_WHITE_LIST + " = 1" : null,
				null,
				null
		);

		try {
			int size = cursor.getCount();
			if (size == 0) {
				return senders;
			}
			for (int i = 0; i < size; i++) {
				cursor.moveToPosition(i);
				SmsMessageSenderEntry item = new SmsMessageSenderEntry(cursor);
				senders.add(item);
			}
		} finally {
			if (cursor != null && !cursor.isClosed()) {
				cursor.close();
			}
		}
		return senders;
	}

	public List<SmsMessageSenderEntry> getSenderWhiteList() {
		return getSenderList(true);
	}

	public List<SmsPojo> getSmsList() {
		List<SmsPojo> items = new ArrayList<SmsPojo>();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR, this.settings.getDeleteAfter().index() * -24);

		Cursor cursor = contentResolver.query(
				SmsContentProvider.CONTENT_URI,
				null,
				SmsMessageEntry.ACTION + " is null and " + SmsMessageEntry.RECEIVED + " > :1",
				new String[]{String.valueOf(cal.getTimeInMillis())},
				null);
		try {
			int size = cursor.getCount();
			if (size == 0) {
				return items;
			}
			for (int i = 0; i < size; i++) {
				cursor.moveToPosition(i);
				SmsMessageEntry item = new SmsMessageEntry(cursor);
				items.add(item);
			}
		} finally {
			if (cursor != null && !cursor.isClosed()) {
				cursor.close();
			}
		}
		return items;
	}

	public int deleteOldSmsList() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR, this.settings.getDeleteAfter().index() * -24);

		return this.contentResolver.delete(
				SmsContentProvider.CONTENT_URI,
				SmsMessageEntry.RECEIVED + " < :1",
				new String[]{String.valueOf(cal.getTimeInMillis())});
	}

	public void setAction(SmsPojo sms, SmsAction action) {
		SmsMessageEntry entry = (SmsMessageEntry) sms;
		ContentValues values = entry.toContentValues();
		values.put(SmsMessageEntry.ACTION, action.index());

		contentResolver.update(SmsContentProvider.CONTENT_URI,
				values,
				SmsMessageEntry._ID + " = :1",
				new String[]{String.valueOf(sms.getId())});
	}

	public void setWhiteList(long id, boolean isInWhiteList) {
		ContentValues values = new ContentValues();
		values.put(SmsMessageSenderEntry.IN_WHITE_LIST, isInWhiteList);

		contentResolver.update(SenderContentProvider.CONTENT_URI,
				values,
				SmsMessageSenderEntry._ID + " = :1",
				new String[]{String.valueOf(id)});
	}

	public void undoActions() {
		ContentValues values = new ContentValues();
		values.put(SmsMessageEntry.ACTION, (String) null);

		contentResolver.update(SmsContentProvider.CONTENT_URI,
				values,
				null,
				null);
	}

	public Hashtable<SmsPojo, SmsAction> getActions() {
		Hashtable<SmsPojo, SmsAction> items = new Hashtable<SmsPojo, SmsAction>();
		Cursor cursor = contentResolver.query(
				SmsContentProvider.CONTENT_URI,
				null,
				SmsMessageEntry.ACTION + " is not null",
				null,
				null);
		try {
			int size = cursor.getCount();
			if (size == 0) {
				return items;
			}
			for (int i = 0; i < size; i++) {
				cursor.moveToPosition(i);
				SmsMessageEntry item = new SmsMessageEntry(cursor);
				int actionValue = cursor.getInt(cursor.getColumnIndex(SmsMessageEntry.ACTION));
				// TODO: replace with id-based approach.
				SmsAction action = actionValue == 0 ? SmsAction.MarkedAsNotSpam : SmsAction.Deleted;
				items.put(item, action);
			}
		} finally {
			if (cursor != null && !cursor.isClosed()) {
				cursor.close();
			}
		}
		return items;
	}

	public SmsPojo getSms(long id) {
		Cursor cursor = contentResolver.query(SmsContentProvider.getItemUri(id), null, null, null, null);
		try {
			int size = cursor.getCount();
			if (size == 1) {
				cursor.moveToFirst();
				return new SmsMessageEntry(cursor);
			} else {
				return null;
			}
		} finally {
			if (cursor != null && !cursor.isClosed()) {
				cursor.close();
			}
		}
	}

	public Uri putSmsToSystemLog(SmsPojo sms) {
		ContentValues values = new ContentValues();
		values.put(SmsHelper.ADDRESS, sms.getSender());
		values.put(SmsHelper.DATE, sms.getReceived());
		values.put(SmsHelper.READ, sms.isRead() ? 1 : 0);
		values.put(SmsHelper.STATUS, -1);
		values.put(SmsHelper.TYPE, 2);
		values.put(SmsHelper.BODY, sms.getMessage());
		return contentResolver.insert(Uri.parse("content://sms"), values);
	}

	public boolean delete(SmsPojo sms) {
		return deleteMessages(new SmsPojo[]{sms}) == 1;
	}

	public int deleteMessages(SmsPojo[] smsList) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < smsList.length; i++) {
			sb.append(smsList[i].getId());
			if (i != smsList.length - 1){
				sb.append(",");
			}
		}

		return contentResolver.delete(
				SmsContentProvider.CONTENT_URI,
				SmsMessageEntry._ID + " IN ( :1 )",
				new String[]{sb.toString()});
	}

	public int purgeSenders(SQLiteDatabase db){
		Cursor c = db.rawQuery(
				String.format(
						"SELECT %s FROM %s WHERE %s NOT IN (SELECT DISTINCT %s FROM %s)",
						SmsMessageSenderEntry._ID ,
						SenderContentProvider.TABLE_NAME,
						SmsMessageEntry.SENDER_ID,
						SmsContentProvider.TABLE_NAME),
				null);

		if(c.moveToFirst()){
			final String[] ids = new String[c.getCount()];
			int index = 0;
			do{
				ids[index] = Long.toString(c.getLong(0));
				index++;
			}
			while (c.moveToNext());
			return contentResolver.delete(
					SmsContentProvider.CONTENT_URI,
					SmsMessageSenderEntry._ID + " IN (:1)",
					ids);
		}

		return 0;
	}

	public boolean update(SmsPojo sms) {
		SmsMessageEntry smsEntry = (SmsMessageEntry) sms;
		int res = contentResolver.update(
				SmsContentProvider.CONTENT_URI,
				smsEntry.toContentValues(),
				SmsMessageEntry._ID + " = :1",
				new String[]{String.valueOf(sms.getId())});
		return res == 1;
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

	public SmsMessageSenderEntry getSenderById(long id) {
		//todo: replace by URI build
		String[] values = {(new Long(id)).toString()};
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

		SmsMessageEntry res = new SmsMessageEntry(sender, message);
		insertMessage(res);
		return res;
	}

	public void insertMessage(SmsMessageEntry message) {
		this.contentResolver.insert(SmsContentProvider.CONTENT_URI, message.toContentValues());
	}
}
