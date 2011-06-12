package com.phdroid.smsb.storage.dao;

import android.content.*;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

/**
 * SmsContentProvider is designed for CRUD actions with SMS.
 */
public class SmsContentProvider extends ContentProvider {
	private static final String TAG = "SmsContentProvider";
	private static final String DATABASE_NAME = "sms.db";
	private static final int DATABASE_VERSION = 3;
	private static final String TABLE_NAME = "sms";
	private static final int CODE_SMS_LIST = 1;
	public static final String TYPE_SMS_LIST = "vnd.android.cursor.dir/vnd.smsbouncer.sms ";
	private static final int CODE_SMS = 2;
	public static final String TYPE_SMS = "vnd.android.cursor.item/vnd.smsbouncer.sms ";


	public static final String PROVIDER_NAME = "com.phdroid.smsb.storage.dao.SmsContentProvider";
	public static final Uri CONTENT_URI = Uri.parse("content://" + PROVIDER_NAME + "/sms");
	public static final String _ID = "_id";
	public static final String SENDER = "sender";
	public static final String MESSAGE = "message";
	public static final String RECEIVED = "received";
    public static final String READ = "read";
	public static final String USER_FLAG_NOT_SPAM = "not_spam_user"; //did user say this sms is NOT spam

	private SQLiteDatabase smsDb;
	private static DatabaseHelper dbHelper;

	private synchronized DatabaseHelper getDatabaseHelper() {
		if (dbHelper == null) {
			Context context = getContext();
			dbHelper = new DatabaseHelper(context);
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
		sqlBuilder.setTables(TABLE_NAME);

		if (uriMatcher.match(uri) == CODE_SMS)
			//---if getting a particular sms---
			sqlBuilder.appendWhere(
					_ID + " = " + uri.getPathSegments().get(1));

		if (sortOrder == null || sortOrder.equals(""))
			sortOrder = RECEIVED;

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
			//---get all sms---
			case CODE_SMS_LIST:
				return TYPE_SMS_LIST;
			//---get a particular sms---
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
						_ID + " = " + id +
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
						_ID + " = " + uri.getPathSegments().get(1) +
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

	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			String sql = "CREATE TABLE " + TABLE_NAME + " (" +
					_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
					SENDER + " NVARCHAR(255)," +
					MESSAGE + " LONGTEXT," +
					RECEIVED + " INTEGER," +
					READ + " INTEGER," +
					USER_FLAG_NOT_SPAM + " INTEGER" +
					");";
			db.execSQL(sql);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion
					+ ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
			onCreate(db);
		}
	}


	private static final UriMatcher uriMatcher;

	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(PROVIDER_NAME, "sms", CODE_SMS_LIST);
		uriMatcher.addURI(PROVIDER_NAME, "sms/#", CODE_SMS);
	}
}
