package ezhun.smsb.test.storage;

import junit.framework.Assert;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.test.ProviderTestCase2;
import ezhun.smsb.storage.SmsContentProvider;

public class SmsContentProviderTest extends ProviderTestCase2<SmsContentProvider> {

	public SmsContentProviderTest() {
		super(SmsContentProvider.class, SmsContentProvider.PROVIDER_NAME);
	}

	public void testOnCreate() {
		String[] dbList = this.getContext().databaseList();
		if(dbList.length < 1){
			fail("list of availbale databases is empty");
		}
		Assert.assertEquals(dbList[0], "test.sms.db");
	}

	public void testQuery() {
		fail("Not yet implemented");
	}

	public void testGetTypeUri() {
		fail("Not yet implemented");
	}

	public void testInsert() {
		String sender = "097 112 332 6";
		String message = "this is a test message";
		int received = (int) (System.currentTimeMillis() / 1000L); 
		ContentResolver c = this.getMockContentResolver();
		
		ContentValues values = new ContentValues();
		values.put(SmsContentProvider.SENDER, sender);
		values.put(SmsContentProvider.MESSAGE, message);
		values.put(SmsContentProvider.RECEIVED, received);
		values.put(SmsContentProvider.SYSTEM_FLAG_SPAM, 0);
		values.put(SmsContentProvider.USER_FLAG_NOT_SPAM, 0);
		values.put(SmsContentProvider.USER_FLAG_SPAM, 0);

		Uri uri = c.insert(SmsContentProvider.CONTENT_URI, values);
		String id = uri.getLastPathSegment();
		
		//now test they are added correctly
		String[] projection = new String[] {
				SmsContentProvider._ID,
                SmsContentProvider.SENDER,
                SmsContentProvider.MESSAGE,
                SmsContentProvider.RECEIVED,
                SmsContentProvider.SYSTEM_FLAG_SPAM,
                SmsContentProvider.USER_FLAG_NOT_SPAM,
                SmsContentProvider.USER_FLAG_SPAM};
		Cursor cur = c.query(SmsContentProvider.CONTENT_URI, projection, null, null, null);
		try {
			cur.moveToFirst();
			Assert.assertEquals(cur.getCount(), 1);
			Assert.assertEquals(cur.getString(cur.getColumnIndex(SmsContentProvider._ID)), id);
			Assert.assertEquals(cur.getString(cur.getColumnIndex(SmsContentProvider.SENDER)), sender);
			Assert.assertEquals(cur.getString(cur.getColumnIndex(SmsContentProvider.MESSAGE)), message);
			Assert.assertEquals(cur.getInt(cur.getColumnIndex(SmsContentProvider.RECEIVED)), received);
			Assert.assertEquals(cur.getInt(cur.getColumnIndex(SmsContentProvider.SYSTEM_FLAG_SPAM)), 0);
			Assert.assertEquals(cur.getInt(cur.getColumnIndex(SmsContentProvider.USER_FLAG_NOT_SPAM)), 0);
			Assert.assertEquals(cur.getInt(cur.getColumnIndex(SmsContentProvider.USER_FLAG_SPAM)), 0);
		} finally {
			cur.close();
		}
	}

	public void testDelete() {
		fail("Not yet implemented");
	}

	public void testUpdate() {
		fail("Not yet implemented");
	}
}
