package com.phdroid.smsb.storage;

import android.content.ContentResolver;
import android.content.Context;
import com.phdroid.smsb.SmsPojo;
import com.phdroid.smsb.application.ApplicationController;
import com.phdroid.smsb.application.NewSmsEvent;
import com.phdroid.smsb.application.NewSmsEventListener;
import com.phdroid.smsb.storage.dao.Session;

import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

public class SmsMessageProvider implements IMessageProvider {
	private Hashtable<SmsPojo, SmsAction> actions;
	private int unreadCount;
	private List<SmsPojo> data;
	private Session session;

	public SmsMessageProvider(Context context, ContentResolver contentResolver) {
		this.actions = new Hashtable<SmsPojo, SmsAction>();
		this.session = new Session(contentResolver);
		ApplicationController app = (ApplicationController)context.getApplicationContext();
		app.attachNewSmsListener(new NewSmsEventListener() {
			@Override
			public void onNewSms(NewSmsEvent newSmsEvent) {
				dataBind();
			}
		});
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
			if (!sms.isRead()) {
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
		return actions;
	}

	public void delete(long id) {
		performActions();
		SmsPojo sms = get(id);
		if (sms != null) {
			actions.put(sms, SmsAction.Deleted);
			getSmsList().remove(sms);
		}
	}

	public void delete(long[] ids) {
		performActions();
		for (long id = ids.length - 1; id >= 0; id--) {
			SmsPojo sms = get(ids[(int) id]);
			if (sms != null) {
				if (!sms.isRead())
					unreadCount--;
				actions.put(sms, SmsAction.Deleted);
				getSmsList().remove(sms);
			}
		}
	}

	public void deleteAll() {
		List<SmsPojo> smsPojoList = getSession().getSmsList();
		for (SmsPojo sms : smsPojoList) {
			actions.put(sms, SmsAction.Deleted);
			getSmsList().remove(sms);
		}
	}

	public void notSpam(long id) {
		performActions();
		SmsPojo sms = get(id);
		if (sms != null) {
			actions.put(sms, SmsAction.MarkedAsNotSpam);
			getSmsList().remove(sms);
		}
	}

	public void notSpam(long[] ids) {
		performActions();
		for (long id = ids.length - 1; id >= 0; id--) {
			SmsPojo sms = get(ids[(int) id]);
			if (sms != null) {
				if (!sms.isRead())
					unreadCount--;
				actions.put(sms, SmsAction.MarkedAsNotSpam);
				getSmsList().remove(sms);
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
			actions.put(smsPojo, SmsAction.Read);
			unreadCount--;
		}
	}

	public int getUnreadCount() {
		return unreadCount;
	}

	public void undo() {
		List<SmsPojo> smsPojoList = getSmsList();
		for (SmsPojo sms : actions.keySet()) {
			smsPojoList.add(0, sms);
			if (!sms.isRead()) {
				unreadCount++;
			}
		}
		data = smsPojoList;
		actions.clear();
	}

	public void performActions() {
		for (SmsPojo sms : actions.keySet()) {
			SmsAction action = actions.get(sms);
			switch (action) {
				case Deleted:
					getSession().delete(sms);
					break;
				case MarkedAsNotSpam:
					getSession().delete(sms);
					break;
				case Read:
					getSession().update(sms);
					break;
				default:
			}
		}
		actions.clear();
	}

	private SmsPojo get(long id) {
		return getSession().getSms(id);
	}
}
