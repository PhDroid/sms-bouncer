package com.phdroid.smsb.activity;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.phdroid.smsb.R;
import com.phdroid.smsb.SenderPojo;

import java.util.List;

public class SenderPojoArrayAdapter extends ArrayAdapter<SenderPojo> {
	Activity ctx;

	public SenderPojoArrayAdapter(Activity context, int textViewResourceId) {
		super(context, textViewResourceId);
		ctx = context;
	}

	public SenderPojoArrayAdapter(Activity context, int resource, int textViewResourceId) {
		super(context, resource, textViewResourceId);
		ctx = context;
	}

	public SenderPojoArrayAdapter(Activity context, int textViewResourceId, SenderPojo[] objects) {
		super(context, textViewResourceId, objects);
		ctx = context;
	}

	public SenderPojoArrayAdapter(Activity context, int resource, int textViewResourceId, SenderPojo[] objects) {
		super(context, resource, textViewResourceId, objects);
		ctx = context;
	}

	public SenderPojoArrayAdapter(Activity context, int textViewResourceId, List<SenderPojo> objects) {
		super(context, textViewResourceId, objects);
		ctx = context;
	}

	public SenderPojoArrayAdapter(Activity context, int resource, int textViewResourceId, List<SenderPojo> objects) {
		super(context, resource, textViewResourceId, objects);
		ctx = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = ctx.getLayoutInflater();
		View row = inflater.inflate(R.layout.editwhitelist_list_item, null);
		TextView sender = (TextView) row.findViewById(R.id.whitelist_sender);

		SenderPojo senderPojo = this.getItem(position);
		sender.setText(senderPojo.getSender());

		Button btnDelete = (Button) row.findViewById(R.id.whitelist_delete);
		btnDelete.setOnClickListener(deleteListener);
		btnDelete.setTag(senderPojo);

		return (row);
	}

	private View.OnClickListener deleteListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
//			RelativeLayout parentRow = (RelativeLayout) v.getParent();
//
//			TextView senderText = (TextView) parentRow.getChildAt(0);
//			Button btnChild = (Button) parentRow.getChildAt(1);
			Button btnChild = (Button) v;
			SenderPojo sender = (SenderPojo) btnChild.getTag();
			//todo: call delete sender
			//parentRow.refreshDrawableState();
		}
	};
}
