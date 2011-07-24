package com.phdroid.smsb.activity;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import com.phdroid.smsb.storage.DeleteAfter;

import java.util.ArrayList;
import java.util.List;

/**
 * Please, write short description of what this file is for.
 */
public class ClearAfterSpinnerAdapter extends ArrayAdapter implements SpinnerAdapter {
	ArrayList<DeleteAfter> items;
	Context context;

	private void init(Context context) {
		this.context = context;
		this.items = new ArrayList<DeleteAfter>();
		this.items.add(DeleteAfter.SevenDays);
		this.items.add(DeleteAfter.FourteenDays);
		this.items.add(DeleteAfter.ThirtyDays);
	}

	public ClearAfterSpinnerAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
		init(context);
	}

	public ClearAfterSpinnerAdapter(Context context, int resource, int textViewResourceId) {
		super(context, resource, textViewResourceId);
		init(context);
	}

	public ClearAfterSpinnerAdapter(Context context, int textViewResourceId, Object[] objects) {
		super(context, textViewResourceId, objects);
		init(context);
	}

	public ClearAfterSpinnerAdapter(Context context, int resource, int textViewResourceId, Object[] objects) {
		super(context, resource, textViewResourceId, objects);
		init(context);
	}

	public ClearAfterSpinnerAdapter(Context context, int textViewResourceId, List objects) {
		super(context, textViewResourceId, objects);
		init(context);
	}

	public ClearAfterSpinnerAdapter(Context context, int resource, int textViewResourceId, List objects) {
		super(context, resource, textViewResourceId, objects);
		init(context);
	}

	private ArrayList<DeleteAfter> getItems() {
		return items;
	}

	public Context getContext() {
		return context;
	}

	@Override
	public int getCount() {
		return getItems().size();
	}

	@Override
	public Object getItem(int i) {
		return getItems().get(i);
	}

	@Override
	public long getItemId(int i) {
		return ((DeleteAfter) getItem(i)).index();
	}

	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {
		TextView text = new TextView(getContext());
		text.setText(getItem(i).toString());
		return text;
	}
}
