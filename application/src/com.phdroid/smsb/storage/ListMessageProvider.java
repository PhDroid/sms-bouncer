package com.phdroid.smsb.storage;

import android.content.ContentResolver;
import com.phdroid.smsb.SmsPojo;
import com.phdroid.smsb.storage.cache.SmsMessageCache;
import com.phdroid.smsb.storage.dao.SmsMessageEntry;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class ListMessageProvider implements IMessageProvider {
	private final Object lock = new Object();
	private ArrayList<SmsPojo> mList;
	private ArrayList<SmsMessageEntry> cachedSmsList;
	private Hashtable<SmsPojo, SmsAction> mActions;
	private int mUnreadCount = 0;
	private ContentResolver contentResolver;

	public ListMessageProvider(ContentResolver contentResolver) {
		this.contentResolver = contentResolver;
	}

	private ContentResolver getContentResolver() {
		return contentResolver;
	}

	private List<SmsPojo> getSmsList() {
		synchronized (lock) {

			if (mList != null) {
				return mList;
			}

			List<SmsMessageEntry> smsDataList = SmsMessageCache.getInstance(getContentResolver()).getItems();
			SmsMessageCache.getInstance(getContentResolver()).setOnDataChanged(new SmsMessageCache.OnDataChanged() {
				@Override
				public void dataChanged() {
					synchronized (lock) {
						mList = null;
					}
				}
			});
			mList = new ArrayList<SmsPojo>(smsDataList.size());
			for (SmsMessageEntry smsData : smsDataList) {
				mList.add(new SmsPojo(smsData));
			}
			return mList;
		}
	}

	public int size() {
		return getSmsList().size();
	}

	public List<SmsPojo> getMessages() {
		return getSmsList();
	}

	public Hashtable<SmsPojo, SmsAction> getActionMessages() {
		return mActions;
	}

	public void delete(long id) {
		mActions.clear();
		SmsPojo sms = get(id);
		if (sms != null) {
			mActions.put(sms, SmsAction.Deleted);
			mList.remove((int) id);
		}
	}

	public void delete(long[] ids) {
		mActions.clear();
		for (long id = ids.length - 1; id >= 0; id--) {
			SmsPojo sms = get(ids[(int) id]);
			if (sms != null) {
				if (!sms.isRead())
					mUnreadCount--;
				mActions.put(sms, SmsAction.Deleted);
				mList.remove((int) id);
			}
		}
	}

	public void deleteAll() {
		for (SmsPojo sms : mList) {
			mActions.put(sms, SmsAction.Deleted);
		}
		mList.clear();
	}

	public void notSpam(long id) {
		mActions.clear();
		SmsPojo sms = get(id);
		if (sms != null) {
			mActions.put(sms, SmsAction.MarkedAsNotSpam);
			mList.remove((int) id);
		}
	}

	public void notSpam(long[] ids) {
		mActions.clear();
		for (long id = ids.length - 1; id >= 0; id--) {
			SmsPojo sms = get(ids[(int) id]);
			if (sms != null) {
				if (!sms.isRead())
					mUnreadCount--;
				mActions.put(sms, SmsAction.MarkedAsNotSpam);
				mList.remove((int) id);
			}
		}
	}

	@Override
	public int getIndex(SmsPojo message) {
		return mList.indexOf(message);
	}

	@Override
	public boolean isFirstMessage(SmsPojo message) {
		return mList.indexOf(message) == 0;
	}

	@Override
	public boolean isLastMessage(SmsPojo message) {
		return mList.indexOf(message) == mList.size() - 1;
	}

	@Override
	public SmsPojo getPreviousMessage(SmsPojo message) {
		int index = mList.indexOf(message);
		if (index <= 0) {
			return null;
		}
		return mList.get(--index);
	}

	@Override
	public SmsPojo getNextMessage(SmsPojo message) {
		int index = mList.indexOf(message);
		if (index >= mList.size() - 1) {
			return null;
		}
		return mList.get(++index);
	}

	public SmsPojo getMessage(long id) {
		return get(id);
	}

	public void read(long id) {
		SmsPojo smsPojo = get(id);
		if (smsPojo != null && !smsPojo.isRead()) {
			smsPojo.setRead(true);
			mUnreadCount--;
		}
	}

	public int getUnreadCount() {
		return mUnreadCount;
	}

	public void undo() {
		for (SmsPojo sms : mActions.keySet()) {
			mList.add(0, sms);
		}
		mActions.clear();

		mUnreadCount = 0;
		for (SmsPojo sms : mList) {
			if (!sms.isRead()) {
				mUnreadCount++;
			}
		}
	}

	public void performActions() {
		mActions.clear();
	}

	private SmsPojo get(long id) {
		if (id < 0) return null;
		if (id > mList.size()) return null;
		return mList.get((int) id);
	}
}
