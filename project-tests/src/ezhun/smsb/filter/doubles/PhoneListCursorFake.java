package ezhun.smsb.filter.doubles;

import android.content.ContentResolver;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

/**
 * Please, write short description of what this file is for.
 */
public class PhoneListCursorFake implements Cursor {
	List<String> phoneList;

	public PhoneListCursorFake(List<String> phoneList) {
		this.phoneList = phoneList;
	}

	public int getCount() {
		return this.phoneList.size();
	}

	public int getPosition() {
		throw new UnsupportedOperationException();
	}

	public boolean move(int i) {
		throw new UnsupportedOperationException();
	}

	public boolean moveToPosition(int i) {
		throw new UnsupportedOperationException();
	}

	public boolean moveToFirst() {
		throw new UnsupportedOperationException();
	}

	public boolean moveToLast() {
		throw new UnsupportedOperationException();
	}

	public boolean moveToNext() {
		throw new UnsupportedOperationException();
	}

	public boolean moveToPrevious() {
		throw new UnsupportedOperationException();
	}

	public boolean isFirst() {
		throw new UnsupportedOperationException();
	}

	public boolean isLast() {
		throw new UnsupportedOperationException();
	}

	public boolean isBeforeFirst() {
		throw new UnsupportedOperationException();
	}

	public boolean isAfterLast() {
		throw new UnsupportedOperationException();
	}

	public int getColumnIndex(String s) {
		throw new UnsupportedOperationException();
	}

	public int getColumnIndexOrThrow(String s) throws IllegalArgumentException {
		throw new UnsupportedOperationException();
	}

	public String getColumnName(int i) {
		throw new UnsupportedOperationException();
	}

	public String[] getColumnNames() {
		throw new UnsupportedOperationException();
	}

	public int getColumnCount() {
		throw new UnsupportedOperationException();
	}

	public byte[] getBlob(int i) {
		throw new UnsupportedOperationException();
	}

	public String getString(int i) {
		throw new UnsupportedOperationException();
	}

	public void copyStringToBuffer(int i, CharArrayBuffer charArrayBuffer) {
		throw new UnsupportedOperationException();
	}

	public short getShort(int i) {
		throw new UnsupportedOperationException();
	}

	public int getInt(int i) {
		throw new UnsupportedOperationException();
	}

	public long getLong(int i) {
		throw new UnsupportedOperationException();
	}

	public float getFloat(int i) {
		throw new UnsupportedOperationException();
	}

	public double getDouble(int i) {
		throw new UnsupportedOperationException();
	}

	public boolean isNull(int i) {
		throw new UnsupportedOperationException();
	}

	public void deactivate() {
		throw new UnsupportedOperationException();
	}

	public boolean requery() {
		throw new UnsupportedOperationException();
	}

	public void close() {
		throw new UnsupportedOperationException();
	}

	public boolean isClosed() {
		throw new UnsupportedOperationException();
	}

	public void registerContentObserver(ContentObserver contentObserver) {
		throw new UnsupportedOperationException();
	}

	public void unregisterContentObserver(ContentObserver contentObserver) {
		throw new UnsupportedOperationException();
	}

	public void registerDataSetObserver(DataSetObserver dataSetObserver) {
		throw new UnsupportedOperationException();
	}

	public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
		throw new UnsupportedOperationException();
	}

	public void setNotificationUri(ContentResolver contentResolver, Uri uri) {
		throw new UnsupportedOperationException();
	}

	public boolean getWantsAllOnMoveCalls() {
		throw new UnsupportedOperationException();
	}

	public Bundle getExtras() {
		throw new UnsupportedOperationException();
	}

	public Bundle respond(Bundle bundle) {
		throw new UnsupportedOperationException();
	}
}
