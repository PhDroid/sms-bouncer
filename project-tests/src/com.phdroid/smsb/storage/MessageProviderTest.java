package com.phdroid.smsb.storage;

import com.phdroid.smsb.SmsPojo;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Hashtable;

public class MessageProviderTest extends TestCase{
	IMessageProvider mProvider;
	// TODO: change indexes for real message provider
	private static final int MESSAGES_COUNT = 9;
	private static final int UNREAD_MESSAGE_ID = 0;
	private static final int READ_MESSAGE_ID = 3;
	private static final int OVER_THE_TOP_ID = 100;
	private static final int NEGATIVE_ID = -1;
	private static final int INITIAL_UNREAD_MESSAGES_COUNT = 6;

	@Override
	public void setUp(){
		// TODO: replace this with real message provider
		mProvider = new com.phdroid.smsb.storage.TestMessageProvider();
	}

	public void test_getMessages_returns_full_messages_list(){
		ArrayList<SmsPojo> messages = mProvider.getMessages();
		 assertEquals(MESSAGES_COUNT, messages.size());
		 assertEquals(mProvider.size(), messages.size());
	}

	public void test_getMessage_doesnt_throw_if_id_is_not_in_list(){
		SmsPojo sms = mProvider.getMessage(NEGATIVE_ID);
		assertEquals(null, sms);
		sms = mProvider.getMessage(OVER_THE_TOP_ID);
		assertEquals(null, sms);
	}

	public void test_read_marks_unread_message_as_read(){
		mProvider.read(UNREAD_MESSAGE_ID);
		assertEquals(true, mProvider.getMessage(UNREAD_MESSAGE_ID).wasRead());
	}

	public void test_read_leaves_read_message_as_it_was(){
		boolean b = mProvider.getMessage(READ_MESSAGE_ID).wasRead();
		mProvider.read(READ_MESSAGE_ID);
		assertEquals(b, mProvider.getMessage(READ_MESSAGE_ID).wasRead());
	}

	public void test_read_doesnt_throw_if_message_id_is_not_in_list(){
		mProvider.read(NEGATIVE_ID);
		mProvider.read(OVER_THE_TOP_ID);
		assertEquals(true, true);
	}

	public void test_delete_moves_message_to_action_messages_pool(){
		int size = mProvider.size();
		SmsPojo sms = mProvider.getMessage(READ_MESSAGE_ID);
		mProvider.delete(READ_MESSAGE_ID);
		assertEquals(size - 1, mProvider.size());
		Hashtable<SmsPojo, SmsAction> actionMessages = mProvider.getActionMessages();
		assertEquals(1, actionMessages.size());
		assertEquals(SmsAction.Deleted, actionMessages.get(sms));
	}

	public void test_delete_performs_actions_with_messages_before_deleting(){
		mProvider.delete(UNREAD_MESSAGE_ID);
		int size = mProvider.size();
		SmsPojo sms = mProvider.getMessage(READ_MESSAGE_ID);
		mProvider.delete(READ_MESSAGE_ID);
		assertEquals(size - 1, mProvider.size());
		Hashtable<SmsPojo, SmsAction> actionMessages = mProvider.getActionMessages();
		assertEquals(1, actionMessages.size());
		assertEquals(SmsAction.Deleted, actionMessages.get(sms));
	}

	public void test_delete_doesnt_throw_if_message_id_is_not_in_list(){
		int size = mProvider.size();
		mProvider.delete(NEGATIVE_ID);
		assertEquals(size, mProvider.size());
		mProvider.delete(OVER_THE_TOP_ID);
		assertEquals(size, mProvider.size());
	}

	public void test_bulk_delete_moves_message_to_action_messages_pool(){
		int size = mProvider.size();
		SmsPojo sms1 = mProvider.getMessage(READ_MESSAGE_ID), sms2 = mProvider.getMessage(UNREAD_MESSAGE_ID);
		mProvider.delete(new long[]{UNREAD_MESSAGE_ID, READ_MESSAGE_ID});
		assertEquals(size - 2, mProvider.size());
		Hashtable<SmsPojo, SmsAction> actionMessages = mProvider.getActionMessages();
		assertEquals(2, actionMessages.size());
		assertEquals(SmsAction.Deleted, actionMessages.get(sms1));
		assertEquals(SmsAction.Deleted, actionMessages.get(sms2));
	}

	public void test_bulk_delete_performs_actions_with_messages_before_deleting(){
		mProvider.delete(UNREAD_MESSAGE_ID);
		int size = mProvider.size();
		SmsPojo sms1 = mProvider.getMessage(READ_MESSAGE_ID), sms2 = mProvider.getMessage(UNREAD_MESSAGE_ID);
		mProvider.delete(new long[]{UNREAD_MESSAGE_ID, READ_MESSAGE_ID});
		assertEquals(size - 2, mProvider.size());
		Hashtable<SmsPojo, SmsAction> actionMessages = mProvider.getActionMessages();
		assertEquals(2, actionMessages.size());
		assertEquals(SmsAction.Deleted, actionMessages.get(sms1));
		assertEquals(SmsAction.Deleted, actionMessages.get(sms2));
	}

	public void test_bulk_delete_doesnt_throw_if_message_id_is_not_in_list(){
		int size = mProvider.size();
		mProvider.delete(new long[]{NEGATIVE_ID, OVER_THE_TOP_ID});
		assertEquals(size, mProvider.size());
	}

	public void test_delete_all_moves_all_messages_to_action_messages_pool(){
		int size = mProvider.size();
		mProvider.delete(new long[]{READ_MESSAGE_ID, UNREAD_MESSAGE_ID});
		assertEquals(size - 2, mProvider.size());
		Hashtable<SmsPojo, SmsAction> actionMessages = mProvider.getActionMessages();
		assertEquals(2, actionMessages.size());
		for(Object action : actionMessages.values().toArray()){
			assertEquals(SmsAction.Deleted, action);
		}
	}

	public void test_not_spam_moves_message_to_action_messages_pool(){
		int size = mProvider.size();
		SmsPojo sms = mProvider.getMessage(READ_MESSAGE_ID);
		mProvider.notSpam(READ_MESSAGE_ID);
		assertEquals(size - 1, mProvider.size());
		Hashtable<SmsPojo, SmsAction> actionMessages = mProvider.getActionMessages();
		assertEquals(1, actionMessages.size());
		assertEquals(SmsAction.MarkedAsNotSpam, actionMessages.get(sms));
	}

	public void test_not_spam_performs_actions_with_messages_before_deleting(){
		mProvider.delete(UNREAD_MESSAGE_ID);
		int size = mProvider.size();
		SmsPojo sms = mProvider.getMessage(READ_MESSAGE_ID);
		mProvider.notSpam(READ_MESSAGE_ID);
		assertEquals(size - 1, mProvider.size());
		Hashtable<SmsPojo, SmsAction> actionMessages = mProvider.getActionMessages();
		assertEquals(1, actionMessages.size());
		assertEquals(SmsAction.MarkedAsNotSpam, actionMessages.get(sms));
	}

	public void test_not_spam_doesnt_throw_if_message_id_is_not_in_list(){
		int size = mProvider.size();
		mProvider.notSpam(NEGATIVE_ID);
		assertEquals(size, mProvider.size());
		mProvider.notSpam(OVER_THE_TOP_ID);
		assertEquals(size, mProvider.size());
	}

	public void test_bulk_not_spam_moves_message_to_action_messages_pool(){
		int size = mProvider.size();
		SmsPojo sms1 = mProvider.getMessage(READ_MESSAGE_ID), sms2 = mProvider.getMessage(UNREAD_MESSAGE_ID);
		mProvider.notSpam(new long[]{UNREAD_MESSAGE_ID, READ_MESSAGE_ID});
		assertEquals(size - 2, mProvider.size());
		Hashtable<SmsPojo, SmsAction> actionMessages = mProvider.getActionMessages();
		assertEquals(2, actionMessages.size());
		assertEquals(SmsAction.MarkedAsNotSpam, actionMessages.get(sms1));
		assertEquals(SmsAction.MarkedAsNotSpam, actionMessages.get(sms2));
	}

	public void test_bulk_not_spam_performs_actions_with_messages_before_deleting(){
		mProvider.delete(UNREAD_MESSAGE_ID);
		int size = mProvider.size();
		SmsPojo sms1 = mProvider.getMessage(READ_MESSAGE_ID), sms2 = mProvider.getMessage(UNREAD_MESSAGE_ID);
		mProvider.notSpam(new long[]{UNREAD_MESSAGE_ID, READ_MESSAGE_ID});
		assertEquals(size - 2, mProvider.size());
		Hashtable<SmsPojo, SmsAction> actionMessages = mProvider.getActionMessages();
		assertEquals(2, actionMessages.size());
		assertEquals(SmsAction.MarkedAsNotSpam, actionMessages.get(sms1));
		assertEquals(SmsAction.MarkedAsNotSpam, actionMessages.get(sms2));
	}

	public void test_bulk_not_spam_doesnt_throw_if_message_id_is_not_in_list(){
		int size = mProvider.size();
		mProvider.notSpam(new long[]{NEGATIVE_ID, OVER_THE_TOP_ID});
		assertEquals(size, mProvider.size());
	}

	public void test_getUnreadCount_returns_unread_messages_count(){
		assertEquals(INITIAL_UNREAD_MESSAGES_COUNT, mProvider.getUnreadCount());
	}

	public void test_getUnreadCount_updates_unread_messages_count(){
		mProvider.read(UNREAD_MESSAGE_ID);
		assertEquals(INITIAL_UNREAD_MESSAGES_COUNT - 1, mProvider.getUnreadCount());
	}

	public void test_undo_returns_action_items_back(){
		int initialSize = mProvider.size();
		mProvider.delete(READ_MESSAGE_ID);
		mProvider.undo();
		assertEquals(initialSize, mProvider.size());
	}

	public void test_undo_returns_items_to_their_positions(){
		SmsPojo sms1 = mProvider.getMessage(READ_MESSAGE_ID), sms2 = mProvider.getMessage(UNREAD_MESSAGE_ID);
		mProvider.notSpam(new long[]{UNREAD_MESSAGE_ID, READ_MESSAGE_ID});
		mProvider.undo();
		assertEquals(sms1, mProvider.getMessage(READ_MESSAGE_ID));
		assertEquals(sms2, mProvider.getMessage(UNREAD_MESSAGE_ID));
	}

	public void test_performActions_clears_action_list(){
		SmsPojo sms1 = mProvider.getMessage(READ_MESSAGE_ID), sms2 = mProvider.getMessage(UNREAD_MESSAGE_ID);
		mProvider.notSpam(new long[]{UNREAD_MESSAGE_ID, READ_MESSAGE_ID});
		mProvider.performActions();
		assertEquals(0, mProvider.getActionMessages().size());
	}

	public void  test_getPreviousMessage_gets_previous_message(){
		SmsPojo original = mProvider.getMessage(READ_MESSAGE_ID);
		SmsPojo prev = mProvider.getMessage(READ_MESSAGE_ID - 1);
		SmsPojo sms = mProvider.getPreviousMessage(original);
		assertEquals(prev, sms);
	}

	public void  test_getPreviousMessage_returns_null_for_first_message(){
		SmsPojo original = mProvider.getMessage(0);
		SmsPojo sms = mProvider.getPreviousMessage(original);
		assertEquals(null, sms);
	}

	public void  test_getNextMessage_gets_next_message(){
		SmsPojo original = mProvider.getMessage(READ_MESSAGE_ID);
		SmsPojo next = mProvider.getMessage(READ_MESSAGE_ID + 1);
		SmsPojo sms = mProvider.getNextMessage(original);
		assertEquals(next, sms);
	}

	public void  test_getNextMessage_returns_null_for_last_message(){
		SmsPojo original = mProvider.getMessage(mProvider.size() - 1);
		SmsPojo sms = mProvider.getNextMessage(original);
		assertEquals(null, sms);
	}

	public void test_getIndex_gets_message_index(){
		SmsPojo sms = mProvider.getMessage(READ_MESSAGE_ID);
		assertEquals(READ_MESSAGE_ID, mProvider.getIndex(sms));
	}

	public void test_getIndex_return_minus_one_if_message_wasnt_found(){
		SmsPojo sms = new SmsPojo();
		assertEquals(-1, mProvider.getIndex(sms));
	}

	public void test_isFirstMessage_returns_true_for_first_message(){
		SmsPojo sms = mProvider.getMessage(0);
		assertEquals(true, mProvider.isFirstMessage(sms));
	}

	public void test_isFirstMessage_returns_false_for_not_first_message(){
		SmsPojo sms = mProvider.getMessage(1);
		assertEquals(false, mProvider.isFirstMessage(sms));
	}

	public void test_isLsstMessage_returns_true_for_last_message(){
		SmsPojo sms = mProvider.getMessage(mProvider.size() - 1);
		assertEquals(true, mProvider.isLastMessage(sms));
	}

	public void test_isLastMessage_returns_false_for_not_last_message(){
		SmsPojo sms = mProvider.getMessage(1);
		assertEquals(false, mProvider.isFirstMessage(sms));
	}
}
