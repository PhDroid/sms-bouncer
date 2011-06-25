package com.phdroid.smsb.storage;

import android.content.ContentResolver;
import com.phdroid.smsb.SmsPojo;
import com.phdroid.smsb.storage.dao.Session;

import java.sql.Date;
import java.util.Hashtable;
import java.util.List;

public class ListMessageProvider implements IMessageProvider {
	private Hashtable<SmsPojo, SmsAction> mActions;
	private int unreadCount;
	private List<SmsPojo> data;
	private Session session;

	public ListMessageProvider(ContentResolver contentResolver) {
		this.session = new Session(contentResolver);
	}

	private Session getSession() {
		return session;
	}

	private List<SmsPojo> getSmsList() {
		if (data == null) {
			dataBind();
		}
		return data;
	}

	private void dataBind() {
		int unread = 0;
		List<SmsPojo> smsPojoList = getSession().getSmsList();
		for (SmsPojo sms : smsPojoList) {
			if(!sms.isRead()) {
				unread++;
			}
		}
		unreadCount = unread;
		data = smsPojoList;
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
			getSession().delete(getSession().getSms(id));
		}
	}

	public void delete(long[] ids) {
		mActions.clear();
		for (long id = ids.length - 1; id >= 0; id--) {
			SmsPojo sms = get(ids[(int) id]);
			if (sms != null) {
				if (!sms.isRead())
					unreadCount--;
				mActions.put(sms, SmsAction.Deleted);
				getSession().delete(getSession().getSms(id));
			}
		}
	}

	public void deleteAll() {
		List<SmsPojo> smsPojoList = getSession().getSmsList();
		for (SmsPojo sms : smsPojoList) {
			mActions.put(sms, SmsAction.Deleted);
			getSession().delete(sms);
		}
	}

	public void notSpam(long id) {
		mActions.clear();
		SmsPojo sms = get(id);
		if (sms != null) {
			mActions.put(sms, SmsAction.MarkedAsNotSpam);
			getSession().delete(sms);
		}
	}

	public void notSpam(long[] ids) {
		mActions.clear();
		for (long id = ids.length - 1; id >= 0; id--) {
			SmsPojo sms = get(ids[(int) id]);
			if (sms != null) {
				if (!sms.isRead())
					unreadCount--;
				mActions.put(sms, SmsAction.MarkedAsNotSpam);
				getSession().delete(sms);
			}
		}
	}

	@Override
	public int getIndex(SmsPojo message) {
		return getMessages().indexOf(message);
	}

	@Override
	public boolean isFirstMessage(SmsPojo message) {
		return getIndex(message) == 0;
	}

	@Override
	public boolean isLastMessage(SmsPojo message) {
		return getIndex(message) == size() - 1;
	}

	@Override
	public SmsPojo getPreviousMessage(SmsPojo message) {
		List<SmsPojo> smsPojoList = getSmsList();
		int index = smsPojoList.indexOf(message);
		if (index <= 0) {
			return null;
		}
		return smsPojoList.get(--index);
	}

	@Override
	public SmsPojo getNextMessage(SmsPojo message) {
		List<SmsPojo> smsPojoList = getSmsList();
		int index = smsPojoList.indexOf(message);
		if (index >= smsPojoList.size() - 1) {
			return null;
		}
		return smsPojoList.get(++index);
	}

	public SmsPojo getMessage(long id) {
		return get(id);
	}

	public void read(long id) {
		SmsPojo smsPojo = get(id);
		if (smsPojo != null && !smsPojo.isRead()) {
			smsPojo.setRead(true);
			//TODO: add action setRead and save to DB after perform actions
			unreadCount--;
		}
	}

	public int getUnreadCount() {
		return unreadCount;
	}

	public void undo() {
		List<SmsPojo> smsPojoList = getSmsList();
		for (SmsPojo sms : mActions.keySet()) {
			smsPojoList.add(0, sms);
			if (!sms.isRead()) {
				unreadCount++;
			}
		}
		data = smsPojoList;
		mActions.clear();
	}

	public void performActions() {
		//TODO: insert logic here
		mActions.clear();
	}

	private SmsPojo get(long id) {
		return getSession().getSms(id);
	}
}
