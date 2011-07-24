package com.phdroid.smsb.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.phdroid.smsb.R;
import com.phdroid.smsb.activity.base.ActivityBase;
import com.phdroid.smsb.storage.ApplicationSettings;
import com.phdroid.smsb.storage.ISenderProvider;
import com.phdroid.smsb.storage.MessageProviderHelper;
import com.phdroid.smsb.storage.dao.SmsMessageSenderEntry;

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
		//todo: use own list view without undo button
		LinearLayout l = (LinearLayout) findViewById(R.id.buttonLayout);
		l.setVisibility(View.GONE);
	}

	@Override
	public void dataBind() {
		super.dataBind();
		List<SmsMessageSenderEntry> messages = getSenderProvider().getWhiteList();
		SenderPojoArrayAdapter senderPojoArrayAdapter = new SenderPojoArrayAdapter(this, R.layout.main_list_item, messages);
		lv.setAdapter(senderPojoArrayAdapter);
		senderPojoArrayAdapter.notifyDataSetChanged();
		Log.v(this.getClass().getSimpleName(), "DataBind");

		senderPojoArrayAdapter.attachOnDeleteListener(new OnDeleteSenderListener() {
			@Override
			public void onDeleteSender(SmsMessageSenderEntry sender) {
				EditWhitelistActivity.this.getSession().deleteFromWhiteList(sender);
				EditWhitelistActivity.this.dataBind();
			}
		});
	}

	private ISenderProvider getSenderProvider() {
		return MessageProviderHelper.getSenderProvider(new ApplicationSettings(this), getContentResolver());
	}

	@Override
	protected void onStart() {
		super.onStart();

		dataBind();
		Log.v(this.getClass().getSimpleName(), "Start");
	}
}