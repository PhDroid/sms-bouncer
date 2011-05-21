package ezhun.smsb.activity;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import ezhun.smsb.storage.ApplicationSettings;
import ezhun.smsb.storage.DeleteAfter;

import java.util.ArrayList;

/**
 * Please, write short description of what this file is for.
 */
public class ClearAfterSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {
	ArrayList<String> items;
	Context context;

	public ClearAfterSpinnerAdapter(Context context) {
		this.context = context;
		this.items = new ArrayList<String>();
		this.items.add(DeleteAfter.SevenDays.toString());
		this.items.add(DeleteAfter.FourteenDays.toString());
		this.items.add(DeleteAfter.ThirtyDays.toString());
	}

	private ArrayList<String> getItems() {
		return items;
	}

	private Context getContext() {
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
		return i;
	}

	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {
		TextView text = new TextView(getContext());
        text.setText((String)getItem(i));
        return text;
	}
}
