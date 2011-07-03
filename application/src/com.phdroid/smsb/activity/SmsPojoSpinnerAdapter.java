package com.phdroid.smsb.activity;

import android.app.Activity;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.phdroid.smsb.R;
import com.phdroid.smsb.SmsPojo;
import com.phdroid.smsb.utility.DateUtilities;

import java.util.List;

public class SmsPojoSpinnerAdapter extends BaseAdapter {

	private Activity mActivity;
	private List<SmsPojo> mMessages;
	private LayoutInflater mInflater;

	public SmsPojoSpinnerAdapter(Activity activity, List<SmsPojo> messages){
		mActivity = activity;
		mInflater = mActivity.getLayoutInflater();
		mMessages = messages;
	}

	@Override
	public int getCount() {
		return mMessages.size();
	}

	@Override
	public Object getItem(int i) {
		return mMessages.get(i);
	}

	@Override
	public long getItemId(int i) {
		return mMessages.get(i).getId();
	}

	@Override
	public View getView(int i, View convertView, ViewGroup viewGroup) {
		if(convertView == null){
			convertView = mInflater.inflate(R.layout.detailed_message_item, null);
		}

		TextView sender = (TextView) convertView.findViewById(R.id.senderTextView);
		TextView received = (TextView) convertView.findViewById(R.id.receivedTimeTextView);
		TextView message = (TextView) convertView.findViewById(R.id.messageTextView);

		SmsPojo sms = mMessages.get(i);

		sender.setText(sms.getSender());
		received.setText(DateUtilities.getRelativeDateString(sms, mActivity));
		message.setText(sms.getMessage());

		return convertView;
	}
}
