package com.phdroid.smsb.storage;

import com.phdroid.smsb.SmsPojo;

import java.util.Hashtable;
import java.util.List;

public interface IMessageProvider {
	List<SmsPojo> getMessages();

	Hashtable<SmsPojo, SmsAction> getActionMessages();

	SmsPojo getMessage(long id);

	void invalidateCache();

	int size();

	void read(long id);

	void read(SmsPojo sms);

	void delete(long id);

	void delete(long[] ids);

	void deleteAll();

	void notSpam(long id);

	void notSpam(long[] ids);

	int getUnreadCount();

	void undo();

	void performActions();

	int getIndex(SmsPojo message);

	SmsPojo getMessageByOrdinal(int index);
}
