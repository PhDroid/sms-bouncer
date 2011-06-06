package com.phdroid.smsb.storage;

import com.phdroid.smsb.SmsPojo;

import java.util.ArrayList;
import java.util.Hashtable;

public interface IMessageProvider {
    ArrayList<SmsPojo> getMessages();

    Hashtable<SmsPojo, SmsAction> getActionMessages();

    SmsPojo getMessage(long id);

	int size();

    void read(long id);

    void delete(long id);

    void delete(long[] ids);

    void deleteAll();

    void notSpam(long id);

    void notSpam(long[] ids);

    int getUnreadCount();

    void undo();

    void performActions();

	SmsPojo getPreviousMessage(SmsPojo message);

	SmsPojo getNextMessage(SmsPojo message);

	int getIndex(SmsPojo message);

	boolean isFirstMessage(SmsPojo message);

	boolean isLastMessage(SmsPojo message);
}
