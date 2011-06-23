package com.phdroid.smsb.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.TextPaint;
import android.text.format.DateUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.TextView;
import com.phdroid.smsb.R;
import com.phdroid.smsb.SmsPojo;
import com.phdroid.smsb.storage.IMessageProvider;
import com.phdroid.smsb.storage.MessageProviderHelper;
import com.phdroid.smsb.ui.EventInjectedActivity;
import com.phdroid.smsb.ui.HorizontalSwipeListener;
import com.phdroid.smsb.utility.DateUtilities;

/**
 * Show detailed sms message with several control functions.
 */
public class ViewMessageActivity extends EventInjectedActivity {
	private int mId = -1;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_message);

		Gallery gallery = (Gallery)findViewById(R.id.message_gallery);
		gallery.setAdapter(new SmsPojoSpinnerAdapter(this, GetMessageProvider().getMessages()));

		Bundle b = getIntent().getExtras();
		mId = b.getInt("id", -1);
		if (mId >= 0) {
			GetMessageProvider().read(mId);

			setTitle(String.format(
					"%s%s",
					getString(R.string.app_name),
					GetMessageProvider().getUnreadCount() > 0 ?
							String.format(" (%s)", Integer.toString(GetMessageProvider().getUnreadCount())) : ""));
		} else {
			//todo: throw something and log actions
			return;
		}


		Button btnDelete = (Button) findViewById(R.id.deleteButton);
		setButtonShadow(btnDelete);

		Button btnNotSpam = (Button) findViewById(R.id.notSpamButton);
		setButtonShadow(btnNotSpam);

		Button btnReply = (Button) findViewById(R.id.replyButton);
		setButtonShadow(btnReply);
	}

	private void setButtonShadow(Button button) {
		//button.setShadowLayer(9, 1, 1, Color.argb(196, 0, 0, 0));
	}


	protected IMessageProvider GetMessageProvider() {
		return MessageProviderHelper.getMessageProvider();
	}


	public void deleteClick(View view) {
		GetMessageProvider().delete(mId);
		finish();
	}

	public void notSpamClick(View view) {
		GetMessageProvider().notSpam(mId);
		//todo: move message to Android SMS storage
		finish();
	}

	public void replyClick(View view) {
		SmsPojo sms = GetMessageProvider().getMessage(mId);

		GetMessageProvider().notSpam(mId);
		//todo: move message to Android SMS storage
		finish();

		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.putExtra("address", sms.getSender());
		intent.setType("vnd.android-dir/mms-sms");
		startActivity(intent);
	}
}