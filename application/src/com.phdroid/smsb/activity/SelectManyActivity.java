package com.phdroid.smsb.activity;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.*;
import com.phdroid.smsb.R;
import com.phdroid.smsb.SmsPojo;
import com.phdroid.smsb.activity.base.ActivityBase;
import com.phdroid.smsb.storage.IMessageProvider;
import com.phdroid.smsb.storage.MessageProviderHelper;

import java.util.ArrayList;
import java.util.List;


public class SelectManyActivity extends ActivityBase {
	View listFooter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_many);
		listFooter = findViewById(R.id.listFooter);
	}

	@Override
	protected void onStart() {
		super.onStart();

		List<SmsPojo> messages = getMessageProvider().getMessages();
		final ListView lv = (ListView)findViewById(R.id.messagesListView);
		lv.setAdapter(new SmsPojoArrayAdapter(this, R.layout.select_many_list_item, messages));
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				updateButtonsVisibility(lv);
			}
		});

		setTitle(R.string.app_name);
		setTitle(String.format(
					"%s (%s)",
					getTitle().toString(),
					Integer.toString(getMessageProvider().getUnreadCount())));
	}

	protected void onSaveInstanceState(Bundle bundle){
		super.onSaveInstanceState(bundle);
		bundle.putBoolean("buttons", listFooter.getVisibility() == View.VISIBLE);
	}

	protected void onRestoreInstanceState(Bundle bundle){
		super.onRestoreInstanceState(bundle);

		boolean b = bundle.getBoolean("buttons", false);
		if(b){
			listFooter.setVisibility(View.VISIBLE);
		} else {
			listFooter.setVisibility(View.GONE);
		}
	}

	private void updateButtonsVisibility(ListView lv) {
		int size = 0;
		for(int i =0; i < lv.getChildCount() ; i++){
			if (lv.isItemChecked(i)){
				size++;
			}
		}

		if(size > 0){
			listFooter.setVisibility(View.VISIBLE);
		} else {
			listFooter.setVisibility(View.GONE);
		}
	}

	protected IMessageProvider getMessageProvider() {
		 return MessageProviderHelper.getMessageProvider(getContentResolver());
	}

	public void deleteMessages(View view) {
		ListView lv = (ListView)findViewById(R.id.messagesListView);
		SparseBooleanArray positions = lv.getCheckedItemPositions();
		long[] ids = new long[positions.size()];
		for(int i=0; i<positions.size(); i++){
			ids[i] = positions.keyAt(i);
		}
		getMessageProvider().delete(ids);
		finish();
	}

	public void markMessagesAsNotSpam(View view) {
		ListView lv = (ListView)findViewById(R.id.messagesListView);
		SparseBooleanArray positions = lv.getCheckedItemPositions();
		long[] ids = new long[positions.size()];
		for(int i=0; i<positions.size(); i++){
			ids[i] = positions.keyAt(i);
		}
		getMessageProvider().notSpam(ids);
		finish();
	}
}
