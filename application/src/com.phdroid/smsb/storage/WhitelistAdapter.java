package com.phdroid.smsb.storage;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides whitelist read operations.
 */
public class WhitelistAdapter {
	private Context context;

	public WhitelistAdapter(Context context) {
		this.context = context;
	}

	private Context getContext() {
		return context;
	}

	private Cursor getWhitelistData(){
		String[] data = {"True"};
		return getContext().getContentResolver().query(SmsContentProvider.CONTENT_URI,
				null,
				SmsContentProvider.USER_FLAG_NOT_SPAM + " = :1",
				data,
				null);
	}

	public List<String> getWhitelist() {
		ArrayList<String> res = new ArrayList<String>();
		Cursor whitelist = getWhitelistData();
		try {
			whitelist.moveToFirst();
			int count = whitelist.getCount();
			for (int i = 0; i < count; i++) {
				String sender = whitelist.getString(whitelist.getColumnIndexOrThrow(SmsContentProvider.SENDER));
				if (!res.contains(sender)) {
					//todo:check address book
					res.add(sender);
				}
				whitelist.moveToNext();
			}
		} finally {
			whitelist.close();
		}
		return null;
	}
}
