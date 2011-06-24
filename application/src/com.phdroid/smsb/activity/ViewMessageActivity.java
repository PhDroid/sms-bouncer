package com.phdroid.smsb.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Gallery;
import com.phdroid.smsb.R;
import com.phdroid.smsb.SmsPojo;
import com.phdroid.smsb.storage.IMessageProvider;
import com.phdroid.smsb.storage.MessageProviderHelper;
import com.phdroid.smsb.ui.EventInjectedActivity;

/**
 * Show detailed sms message with several control functions.
 */
public class ViewMessageActivity extends EventInjectedActivity {
	private Gallery mGallery;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_message);

		mGallery = (Gallery)findViewById(R.id.message_gallery);
		mGallery.setAdapter(new SmsPojoSpinnerAdapter(this, GetMessageProvider().getMessages()));
		mGallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
				GetMessageProvider().read(i);
				updateTitle();
			}

			@Override
			public void onNothingSelected(AdapterView<?> adapterView) {
			}
		});

		Bundle b = getIntent().getExtras();
		int id = b.getInt("id", -1);
		if (id >= 0) {
			GetMessageProvider().read(id);
			mGallery.setSelection(id, false);

			updateTitle();
		} else {
			//todo: throw something and log actions
			return;
		}
	}

	private void updateTitle() {
		setTitle(String.format(
				"%s%s",
				getString(R.string.app_name),
				GetMessageProvider().getUnreadCount() > 0 ?
						String.format(" (%s)", Integer.toString(GetMessageProvider().getUnreadCount())) : ""));
	}

	protected IMessageProvider GetMessageProvider() {
		return MessageProviderHelper.getMessageProvider();
	}


	public void deleteClick(View view) {
		long id = mGallery.getSelectedItemId();
		GetMessageProvider().delete(id);
		finish();
	}

	public void notSpamClick(View view) {
		long id = mGallery.getSelectedItemId();
		GetMessageProvider().notSpam(id);
		//todo: move message to Android SMS storage
		finish();
	}

	public void replyClick(View view) {
		long id = mGallery.getSelectedItemId();
		SmsPojo sms = GetMessageProvider().getMessage(id);

		GetMessageProvider().notSpam(id);
		//todo: move message to Android SMS storage
		finish();

		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.putExtra("address", sms.getSender());
		intent.setType("vnd.android-dir/mms-sms");
		startActivity(intent);
	}
}