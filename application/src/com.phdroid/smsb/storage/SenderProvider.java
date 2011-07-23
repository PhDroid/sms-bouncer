package com.phdroid.smsb.storage;

import android.database.sqlite.SQLiteDatabase;
import com.phdroid.smsb.storage.dao.Session;
import com.phdroid.smsb.storage.dao.SmsMessageSenderEntry;

import java.util.List;

public class SenderProvider implements ISenderProvider{

	private final Session mSession;

	public SenderProvider(Session session){
		mSession = session;
	}

	/**
	 * Gets message sender from storage
	 *
	 * @param id Sender ID
	 * @return Message sender
	 */
	@Override
	public SmsMessageSenderEntry get(long id) {
		return mSession.getSenderById(id);
	}

	/**
	 * Puts message sender to storage
	 *
	 * @param sender Message sender
	 * @return Sender
	 */
	@Override
	public SmsMessageSenderEntry put(String sender) {
		return mSession.insertOrSelectSender(sender);
	}

	/**
	 * Gets white list.
	 *
	 * @return Senders white list.
	 */
	@Override
	public List<SmsMessageSenderEntry> getWhiteList() {
		return mSession.getSenderWhiteList();
	}

	/**
	 * Puts sender to senders white list
	 *
	 * @param senderId Message sender ID
	 */
	@Override
	public void putToWhiteList(long senderId) {
		mSession.setWhiteList(senderId, true);
	}

	/**
	 * Deletes sender from senders white list
	 *
	 * @param sender Message sender
	 * @param db target database
	 * @return True if everything went OK, false if there was an error.
	 */
	@Override
	public void deleteFromWhiteList(SmsMessageSenderEntry sender, SQLiteDatabase db) {
		mSession.setWhiteList(sender.getId(), true);
		mSession.purgeSenders(db);
	}
}
