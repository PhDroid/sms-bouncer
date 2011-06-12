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
    private static final int DATABASE_VERSION = 3;


    DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + SmsContentProvider.TABLE_NAME + " (" +
                SmsContentProvider._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                SmsContentProvider.SENDER + " NVARCHAR(255)," +
                SmsContentProvider.MESSAGE + " LONGTEXT," +
                SmsContentProvider.RECEIVED + " INTEGER," +
                SmsContentProvider.READ + " INTEGER," +
                SmsContentProvider.USER_FLAG_NOT_SPAM + " INTEGER" +
                ");";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + SmsContentProvider.TABLE_NAME);
        onCreate(db);
    }
}
