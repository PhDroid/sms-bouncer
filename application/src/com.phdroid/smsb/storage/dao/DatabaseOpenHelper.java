package com.phdroid.smsb.storage.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Encapsulates database create and upgrade functions
 */
public class DatabaseOpenHelper extends SQLiteOpenHelper {
	private static final String TAG = "SmsContentProvider";
	private static final String DATABASE_NAME = "sms.db";
	private static final int DATABASE_VERSION = 10;


	DatabaseOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE " + SenderContentProvider.TABLE_NAME + " (" +
				SmsMessageSenderEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
				SmsMessageSenderEntry.VALUE + " NVARCHAR(255)," + //todo:check for unique constraint
				SmsMessageSenderEntry.IN_WHITE_LIST + " INTEGER" +
				");";
		db.execSQL(sql);

		sql = "CREATE TABLE " + SmsContentProvider.TABLE_NAME + " (" +
				SmsMessageEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
				SmsMessageEntry.SENDER_ID + " INTEGER REFERENCES " + SenderContentProvider.TABLE_NAME + " ON DELETE CASCADE," +
				SmsMessageEntry.MESSAGE + " LONGTEXT," +
				SmsMessageEntry.RECEIVED + " NUMERIC," +
				SmsMessageEntry.READ + " INTEGER," +
				SmsMessageEntry.ACTION + " INTEGER" +
				");";
		db.execSQL(sql);

		sql = "CREATE VIEW " + SmsContentProvider.VIEW_NAME + " AS " +
				"select " +
				"sms." + SmsMessageEntry._ID + "," +
				"sms." + SmsMessageEntry.SENDER_ID + "," +
				"sender." + SmsMessageSenderEntry.VALUE + " as " + SmsMessageEntry.SENDER + "," +
				"sms." + SmsMessageEntry.MESSAGE + "," +
				"sms." + SmsMessageEntry.RECEIVED + "," +
				"sms." + SmsMessageEntry.READ + "," +
				"sms." + SmsMessageEntry.ACTION +
				" from " +
				SmsContentProvider.TABLE_NAME + " as sms " +
				" inner join " +
				SenderContentProvider.TABLE_NAME + " as sender " +
				" on " +
				"sender." + SmsMessageSenderEntry._ID + " = " + "sms." + SmsMessageEntry.SENDER_ID;
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		db.execSQL("DROP VIEW IF EXISTS " + SmsContentProvider.VIEW_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + SmsContentProvider.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + SenderContentProvider.TABLE_NAME);
		onCreate(db);
	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);
		if (!db.isReadOnly()) {
			// Enable foreign key constraints
			db.execSQL("PRAGMA foreign_keys=ON;");
		}
	}
}
