package com.phdroid.smsb.storage;

import android.database.sqlite.SQLiteDatabase;
import com.phdroid.smsb.storage.dao.SmsMessageSenderEntry;

import java.util.List;

public interface ISenderProvider {
	/**
	 * Gets message sender from storage
	 *
	 * @param id Sender ID
	 * @return Message sender
	 */
	SmsMessageSenderEntry get(long id);

	/**
	 * Puts message sender to storage
	 *
	 * @param sender Message sender
	 * @return Sender
	 */
	SmsMessageSenderEntry put(String sender);

	/**
	 * Gets white list.
	 *
	 * @return Senders white list.
	 */
	List<SmsMessageSenderEntry> getWhiteList();

	/**
	 * Puts sender to senders white list
	 *
	 * @param senderId Message sender ID
	 * @return True if everything went OK, false if there was an error.
	 */
	void putToWhiteList(long senderId);

	/**
	 * Deletes sender from senders white list
	 *
	 * @param sender Message sender
	 * @return True if everything went OK, false if there was an error.
	 */
	void deleteFromWhiteList(SmsMessageSenderEntry sender, SQLiteDatabase db);
}
