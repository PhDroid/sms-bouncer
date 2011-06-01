package ezhun.smsb.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import ezhun.smsb.R;
import ezhun.smsb.SmsPojo;
import ezhun.smsb.storage.IMessageProvider;
import ezhun.smsb.storage.MessageProviderHelper;

/**
 * Show detailed sms message with several control functions.
 */
public class ViewMessageActivity extends Activity {
	private int mId = -1;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_message);

		Button btnDelete = (Button) findViewById(R.id.deleteButton);
		btnDelete.getBackground().setColorFilter(ActivityConstants.COLOR_RED, PorterDuff.Mode.MULTIPLY);
		btnDelete.setOnClickListener(new DeleteListener());
		Button btnNotSpam = (Button) findViewById(R.id.notSpamButton);
		btnNotSpam.getBackground().setColorFilter(ActivityConstants.COLOR_GREEN, PorterDuff.Mode.MULTIPLY);
		btnNotSpam.setOnClickListener(new NotSpamListener());
		Button btnReply = (Button) findViewById(R.id.replyButton);
		btnReply.getBackground().setColorFilter(ActivityConstants.COLOR_GREEN, PorterDuff.Mode.MULTIPLY);
		btnReply.setOnClickListener(new ReplyListener());
		Button btnPrev = (Button) findViewById(R.id.prevButton);
		btnPrev.setOnClickListener(new GoToPreviousListener());
		Button btnNext = (Button) findViewById(R.id.nextButton);
		btnNext.setOnClickListener(new GoToNextListener());


		Bundle b = getIntent().getExtras();
		mId = b.getInt("id", -1);
		if (mId >= 0) {
			SmsPojo sms = GetMessageProvider().getMessage(mId);

			TextView sender = (TextView) findViewById(R.id.senderTextView);
			TextView received = (TextView) findViewById(R.id.receivedTimeTextView);
			TextView message = (TextView) findViewById(R.id.messageTextView);

			sender.setText(sms.getSender());
			received.setText(DateUtils.getRelativeDateTimeString(
					this,
					sms.getReceived(),
					DateUtils.MINUTE_IN_MILLIS,
					DateUtils.WEEK_IN_MILLIS,
					DateUtils.FORMAT_ABBREV_RELATIVE));
			message.setText(sms.getMessage());

			GetMessageProvider().read(mId);

			setTitle(R.string.app_name);
			setTitle(String.format(
					"%s (%s)",
					getTitle().toString(),
					Integer.toString(GetMessageProvider().getUnreadCount())));
		} else {
			//todo: throw something and log actions
		}
	}


	protected IMessageProvider GetMessageProvider() {
		return MessageProviderHelper.getMessageProvider();
	}


	public class DeleteListener implements View.OnClickListener {
		@Override
		public void onClick(View view) {
			GetMessageProvider().delete(mId);
			finish();
		}
	}

	public class NotSpamListener implements View.OnClickListener {
		@Override
		public void onClick(View view) {
			GetMessageProvider().notSpam(mId);
			finish();
		}
	}

	public class ReplyListener implements View.OnClickListener {
		@Override
		public void onClick(View view) {
			SmsPojo sms = GetMessageProvider().getMessage(mId);

			GetMessageProvider().notSpam(mId);
			finish();

			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.putExtra("address", sms.getSender());
			intent.setType("vnd.android-dir/mms-sms");
			startActivity(intent);
		}
	}

	public class GoToPreviousListener implements View.OnClickListener {
		@Override
		public void onClick(View view) {
			IMessageProvider p = GetMessageProvider();
			SmsPojo current = p.getMessage(mId);
			SmsPojo target = p.getPreviousMessage(current);
			if (target == null) {
				return;
			}

			Intent intent = new Intent(ViewMessageActivity.this, ViewMessageActivity.class);
            Bundle b = new Bundle();
            b.putInt("id", p.getIndex(target));
                intent.putExtras(b);
				startActivity(intent);

			finish();
		}
	}

	public class GoToNextListener implements View.OnClickListener {
		@Override
		public void onClick(View view) {
			IMessageProvider p = GetMessageProvider();
			SmsPojo current = p.getMessage(mId);
			SmsPojo target = p.getNextMessage(current);
			if (target == null) {
				return;
			}

			Intent intent = new Intent(ViewMessageActivity.this, ViewMessageActivity.class);
            Bundle b = new Bundle();
            b.putInt("id", p.getIndex(target));
                intent.putExtras(b);
				startActivity(intent);

			finish();
		}
	}
}