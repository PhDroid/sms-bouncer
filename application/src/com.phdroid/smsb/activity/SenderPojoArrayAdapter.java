package com.phdroid.smsb.activity;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import com.phdroid.smsb.R;
import com.phdroid.smsb.storage.dao.Session;
import com.phdroid.smsb.storage.dao.SmsMessageSenderEntry;

import java.util.ArrayList;
import java.util.List;

public class SenderPojoArrayAdapter extends ArrayAdapter<SmsMessageSenderEntry> {
	private Activity context;
	private List<OnDeleteSenderListener> deleteListeners;

	public SenderPojoArrayAdapter(Activity context, int textViewResourceId, List<SmsMessageSenderEntry> objects) {
		super(context, textViewResourceId, objects);
		this.context = context;
		this.deleteListeners = new ArrayList<OnDeleteSenderListener>();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View row = inflater.inflate(R.layout.editwhitelist_list_item, null);
		TextView sender = (TextView) row.findViewById(R.id.whitelist_sender);

		SmsMessageSenderEntry senderPojo = this.getItem(position);
		sender.setText(senderPojo.getValue());

		Button btnDelete = (Button) row.findViewById(R.id.whitelist_delete);
		btnDelete.setOnClickListener(deleteListener);
		btnDelete.setTag(senderPojo);

		return (row);
	}

	public void attachOnDeleteListener(OnDeleteSenderListener listener){
		deleteListeners.add(listener);
	}

	public void removeOnDeleteListener(OnDeleteSenderListener listener){
		deleteListeners.remove(listener);
	}

	private void raiseOnDeleteEvent(SmsMessageSenderEntry sender){
		for (OnDeleteSenderListener listener: this.deleteListeners) {
			listener.onDeleteSender(sender);
		}
	}

	private View.OnClickListener deleteListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Button btnChild = (Button) v;
			SmsMessageSenderEntry sender = (SmsMessageSenderEntry) btnChild.getTag();
			SenderPojoArrayAdapter.this.raiseOnDeleteEvent(sender);
		}
	};
}
