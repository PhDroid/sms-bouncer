package com.phdroid.smsb.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.phdroid.smsb.storage.*;
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

	public void deleteSender(View view){

	}

	public void undo(View view) {
		LinearLayout l = (LinearLayout) findViewById(R.id.buttonLayout);
		l.setVisibility(View.GONE);
		dataBind();
	}
}