package com.phdroid.smsb.storage.dao;

import android.content.*;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import com.phdroid.smsb.SmsPojo;

/**
 * SmsContentProvider is designed for CRUD actions with SMS.
 */
public class SmsContentProvider extends ContentProvider {
	public static final String TABLE_NAME = "sms";
	public static final String VIEW_NAME = "v_sms";
	private static final int CODE_SMS_LIST = 1;
	public static final String TYPE_SMS_LIST = "vnd.android.cursor.dir/vnd.smsbouncer.sms ";
	private static final int CODE_SMS = 2;
	public static final String TYPE_SMS = "vnd.android.cursor.item/vnd.smsbouncer.sms ";

	public static final String PROVIDER_NAME = "com.phdroid.smsb.storage.dao.SmsContentProvider";
	public static final Uri CONTENT_URI = Uri.parse("content://" + PROVIDER_NAME + "/sms");

	public static Uri getItemUri(long id) {
		Uri.Builder b = SmsContentProvider.CONTENT_URI.buildUpon();
		b.appendPath(Long.toString(id));
		return b.build();
	}

	public static Uri getItemUri(SmsPojo sms) {
		return getItemUri(sms.getId());
	}

	private SQLiteDatabase smsDb;
	private static DatabaseOpenHelper dbHelper;

	private synchronized DatabaseOpenHelper getDatabaseHelper() {
		if (dbHelper == null) {
			Context context = getContext();
			dbHelper = new DatabaseOpenHelper(context);
		}
		return dbHelper;
	}

	//override the onCreate() method to open a connection to the database when the content provider is started
	@Override
	public boolean onCreate() {
		smsDb = getDatabaseHelper().getWritableDatabase();
		return (smsDb != null);
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder sqlBuilder = new SQLiteQueryBuilder();
//        sqlBuilder.setTables(TABLE_NAME + " inner join " + SenderContentProvider.TABLE_NAME +
//		        " on " + SenderContentProvider.TABLE_NAME + "." + SmsMessageSenderEntry._ID + " = " + TABLE_NAME + "." + SmsMessageEntry.SENDER_ID);
		sqlBuilder.setTables(VIEW_NAME);

		if (uriMatcher.match(uri) == CODE_SMS)
			//---if getting a particular sms---
			sqlBuilder.appendWhere(
					SmsMessageEntry._ID + " = " + uri.getPathSegments().get(1));

		if (sortOrder == null || sortOrder.equals(""))
			sortOrder = SmsMessageEntry.RECEIVED + " DESC";

		Cursor c = sqlBuilder.query(
				smsDb,
				projection,
				selection,
				selectionArgs,
				null,
				null,
				sortOrder);

		//---register to watch a content URI for changes---
		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
	}

	@Override
	public String getType(Uri uri) {
		switch (uriMatcher.match(uri)) {
			//---getSms all sms---
			case CODE_SMS_LIST:
				return TYPE_SMS_LIST;
			//---getSms a particular sms---
			case CODE_SMS:
				return TYPE_SMS;
			default:
				throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		//---add a new book---
		long rowID = smsDb.insert(
				TABLE_NAME, "", values);

		//---if added successfully---
		if (rowID > 0) {
			Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
			getContext().getContentResolver().notifyChange(_uri, null);
			return _uri;
		}
		throw new SQLException("Failed to insert row into " + uri);
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int count;
		switch (uriMatcher.match(uri)) {
			case CODE_SMS_LIST:
				count = smsDb.delete(
						TABLE_NAME,
						selection,
						selectionArgs);
				break;
			case CODE_SMS:
				String id = uri.getPathSegments().get(1);
				count = smsDb.delete(
						TABLE_NAME,
						SmsMessageEntry._ID + " = " + id +
								(!TextUtils.isEmpty(selection) ? " AND (" +
										selection + ')' : ""),
						selectionArgs);
				break;
			default:
				throw new IllegalArgumentException(
						"Unknown URI " + uri
				);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		int count;
		switch (uriMatcher.match(uri)) {
			case CODE_SMS_LIST:
				count = smsDb.update(
						TABLE_NAME,
						values,
						selection,
						selectionArgs);
				break;
			case CODE_SMS:
				count = smsDb.update(
						TABLE_NAME,
						values,
						SmsMessageEntry._ID + " = " + uri.getPathSegments().get(1) +
								(!TextUtils.isEmpty(selection) ? " AND (" +
										selection + ')' : ""),
						selectionArgs);
				break;
			default:
				throw new IllegalArgumentException(
						"Unknown URI " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	private static final UriMatcher uriMatcher;

	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(PROVIDER_NAME, "sms", CODE_SMS_LIST);
		uriMatcher.addURI(PROVIDER_NAME, "sms/#", CODE_SMS);
	}
}
