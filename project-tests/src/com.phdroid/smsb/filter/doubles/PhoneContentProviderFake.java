package com.phdroid.smsb.filter.doubles;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Data;
import android.test.mock.MockContentProvider;

import java.util.ArrayList;
import java.util.Map;

/**
 * This file is responsible for all phone related data operations.
 */
public class PhoneContentProviderFake extends MockContentProvider {
	ArrayList<String> phoneList = new ArrayList<String>();

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		if (uri == Data.CONTENT_URI || uri == Phone.CONTENT_URI) {

			for (Map.Entry<String, Object> entry : values.valueSet()) {
				if (entry.getKey().equals(Phone.NUMBER)) {
					String phoneNumber = entry.getValue().toString();
					if (!phoneList.contains(phoneNumber)) {
						phoneList.add(phoneNumber);
					}
				}
			}
		}
		return Uri.parse(uri.toString() + "#1");
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		for (String arg : selectionArgs) {
			if (phoneList.contains(arg)) {
				return new PhoneListCursorFake(this.phoneList);
			}
		}

		return new PhoneListCursorFake(new ArrayList<String>());
	}

	@Override
	public void attachInfo(Context context, ProviderInfo info) {
	}
}
