package com.phdroid.smsb.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.phdroid.smsb.R;
import com.phdroid.smsb.SmsPojo;
import com.phdroid.smsb.activity.base.ActivityBase;
import com.phdroid.smsb.storage.ApplicationSettings;
import com.phdroid.smsb.storage.IMessageProvider;
import com.phdroid.smsb.storage.MessageProviderHelper;
import com.phdroid.smsb.storage.SmsAction;
import com.phdroid.smsb.storage.dao.SmsMessageSenderEntry;

import java.util.Hashtable;
import java.util.List;

public class EditWhitelistActivity extends ActivityBase {
	private ListView lv;

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		lv = (ListView) findViewById(R.id.messagesListView);
	}

	protected IMessageProvider getMessageProvider() {
		return MessageProviderHelper.getMessageProvider(new ApplicationSettings(this), getContentResolver());
	}

	@Override
	public void dataBind() {
		super.dataBind();
		List<SmsMessageSenderEntry> messages = getSession().getSenderWhiteList();
		SenderPojoArrayAdapter senderPojoArrayAdapter = new SenderPojoArrayAdapter(this, R.layout.main_list_item, messages);
		lv.setAdapter(senderPojoArrayAdapter);
		senderPojoArrayAdapter.notifyDataSetChanged();
		updateNoMessagesTextView();
		updateTitle();
		Log.v(this.getClass().getSimpleName(), "DataBind");
	}

	@Override
	protected void onStart() {
		super.onStart();

		dataBind();
		processUndoButton();
		updateTitle();
		updateNoMessagesTextView();
		Log.v(this.getClass().getSimpleName(), "Start");
	}

	private void processUndoButton() {
		Hashtable<SmsPojo, SmsAction> actions = getMessageProvider().getActionMessages();
		if (actions.size() > 0) {
			Button b = (Button) findViewById(R.id.undoButton);
			String action = "edited";
			if (!actions.contains(SmsAction.MarkedAsNotSpam)) {
				action = "deleted";
			}
			if (!actions.contains(SmsAction.Deleted)) {
				action = "marked as not spam";
			}
			b.setText(
					String.format(
							"%s message%s %s. (Undo)",
							Integer.toString(actions.size()),
							actions.size() > 1 ? "s were" : " was",
							action
					));
			LinearLayout l = (LinearLayout) findViewById(R.id.buttonLayout);
			l.setVisibility(View.VISIBLE);
		} else {
			LinearLayout l = (LinearLayout) findViewById(R.id.buttonLayout);
			l.setVisibility(View.GONE);
		}
	}

	private void updateTitle() {
		setTitle(R.string.app_name);
		setTitle(String.format(
				"%s%s",
				getTitle().toString(),
				getMessageProvider().getUnreadCount() > 0 ?
						String.format(" (%s)", Integer.toString(getMessageProvider().getUnreadCount())) : ""));
	}

	private void updateNoMessagesTextView() {
		List<SmsPojo> messages = getMessageProvider().getMessages();
		TextView noMessages = (TextView) findViewById(R.id.no_messages_info);
		if (messages.size() == 0) {
			noMessages.setVisibility(View.VISIBLE);
		} else {
			noMessages.setVisibility(View.GONE);
		}
	}

	protected void setMenuBackground() {
		getLayoutInflater().setFactory(new LayoutInflater.Factory() {

			@Override
			public View onCreateView(String name, Context context, AttributeSet attrs) {
				if (name.equalsIgnoreCase("com.android.internal.view.menu.IconMenuItemView")) {
					try {
						LayoutInflater f = getLayoutInflater();
						final View view = f.createView(name, null, attrs);
						new Handler().post(new Runnable() {
							public void run() {
								view.setBackgroundResource(R.drawable.menu_item);
							}
						});
						return view;
					}
					catch (InflateException e) { /*ignore*/ }
					catch (ClassNotFoundException e) { /*ignore*/ }
				}
				return null;
			}
		});
	}

	public void undo(View view) {
		getMessageProvider().undo();
		MessageProviderHelper.invalidCache();
		LinearLayout l = (LinearLayout) findViewById(R.id.buttonLayout);
		l.setVisibility(View.GONE);
		dataBind();
		updateTitle();
	}
}