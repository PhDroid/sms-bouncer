package ezhun.smsb.content;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

/**
 * BlockedSmsContentProvider should provide CRUD operations over SMS messages.
 */
public class BlockedSmsContentProvider extends ContentProvider {
	@Override
	public boolean onCreate() {
		return false;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public String getType(Uri uri) {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public Uri insert(Uri uri, ContentValues contentValues) {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public int delete(Uri uri, String s, String[] strings) {
		return 0;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
		return 0;  //To change body of implemented methods use File | Settings | File Templates.
	}
}
