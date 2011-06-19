package com.phdroid.smsb.storage.cache;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Handler;
import com.phdroid.smsb.SmsPojo;
import com.phdroid.smsb.storage.dao.SmsContentProvider;
import com.phdroid.smsb.storage.dao.SmsMessageEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Side cache implementation class for SmsMessages
 */
public class SmsMessageCache {
	private class SmsContentObserver extends ContentObserver {
		public SmsContentObserver(Handler handler) {
			super(handler);
		}

		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
			SmsMessageCache.this.onDataChanged();
		}
	}

	public static abstract class OnDataChanged {
		public abstract void dataChanged();
	}

	private static SmsMessageCache ourInstance;

	public synchronized static SmsMessageCache getInstance(ContentResolver contentResolver) {
		if (ourInstance == null) {
			ourInstance = new SmsMessageCache(contentResolver);
		}
		return ourInstance;
	}

	private List<SmsPojo> items;
	private ContentResolver contentResolver;
	private OnDataChanged onDataChanged;

	private SmsMessageCache(ContentResolver contentResolver) {
		this.contentResolver = contentResolver;
		this.items = new ArrayList<SmsPojo>();
		this.contentResolver.registerContentObserver(SmsContentProvider.CONTENT_URI, true, new SmsContentObserver(new Handler()));
	}

	public synchronized void setOnDataChanged(OnDataChanged handler) {
		onDataChanged = handler;
	}

	public List<SmsPojo> getItems() {
		return items;
	}

	private synchronized void onDataChanged() {
		items.clear();
		if(onDataChanged != null) {
			onDataChanged.dataChanged();
		}
		getData();
	}

	private void getData() {
		Cursor c = contentResolver.query(SmsContentProvider.CONTENT_URI, null, null, null, null);
		try {
			bindCache(c);
		} finally {
			if (c != null && !c.isClosed()) {
				c.close();
			}
		}
	}

	protected void bindCache(Cursor cursor) {
		int size = cursor.getCount();
		if (size == 0) {
			return;
		}
		for (int i = 0; i < size; i++) {
			cursor.move(i);
			SmsMessageEntry item = new SmsMessageEntry(cursor);
			this.items.add(item);
		}
	}
}
