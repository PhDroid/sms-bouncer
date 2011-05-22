package ezhun.smsb.broadcast;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PermissionInfo;
import android.util.Pair;
import ezhun.smsb.base.MockedContextTestBase;
import ezhun.smsb.broadcast.doubles.BroadcastCallRegistration;
import ezhun.smsb.broadcast.doubles.OtherBroadcastReceiverMock;
import ezhun.smsb.broadcast.doubles.SmsBroadcastReceiverMock;
import junit.framework.Assert;

import java.util.List;

/**
 * Testing notification is blocked.
 */
public class BlockNotificationTest extends MockedContextTestBase {
	private static final String ANDROID_PROVIDER_TELEPHONY_SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
	private static final String ANDROID_PERMISSION_RECEIVE_SMS = "android.permission.RECEIVE_SMS";

	BroadcastReceiver first = new SmsBroadcastReceiverMock();
	BroadcastReceiver second = new OtherBroadcastReceiverMock();

	@Override
	public void setUp() throws Exception {
		super.setUp();

		try {
			PermissionInfo permissionInfo = new PermissionInfo();
			permissionInfo.name = ANDROID_PERMISSION_RECEIVE_SMS;
			getContext().getPackageManager().addPermission(permissionInfo);
		} catch (Exception e) {
			//do nothing
		}

		IntentFilter priorityFilter = new IntentFilter(ANDROID_PROVIDER_TELEPHONY_SMS_RECEIVED);
		priorityFilter.setPriority(100);
		getContext().registerReceiver(first, priorityFilter);

		IntentFilter intentFilter = new IntentFilter(ANDROID_PROVIDER_TELEPHONY_SMS_RECEIVED);
		getContext().registerReceiver(second, intentFilter);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();

		getContext().unregisterReceiver(first);
		getContext().unregisterReceiver(second);
	}

	public synchronized void testBroadcast_setUp_correctly() throws Exception {
		BroadcastCallRegistration.getInstance().clear();

		Intent intent = new Intent(ANDROID_PROVIDER_TELEPHONY_SMS_RECEIVED);
		getContext().sendOrderedBroadcast(intent, ANDROID_PERMISSION_RECEIVE_SMS);
		wait(300);
		Assert.assertEquals(2, BroadcastCallRegistration.getInstance().size());
	}

	public synchronized void testBroadcast_priority() throws Exception {
		BroadcastCallRegistration.getInstance().clear();

		Intent intent = new Intent(ANDROID_PROVIDER_TELEPHONY_SMS_RECEIVED);
		getContext().sendOrderedBroadcast(intent, ANDROID_PERMISSION_RECEIVE_SMS);
		wait(300);
		List<Pair<BroadcastReceiver, Intent>> itemsList = BroadcastCallRegistration.getInstance().getItems();

		Assert.assertEquals(2, BroadcastCallRegistration.getInstance().size());
		Assert.assertTrue("Priority broadcast receiver comes first", itemsList.get(0).first instanceof SmsBroadcastReceiverMock);
		Assert.assertTrue("Non-priority broadcast receiver comes second", itemsList.get(1).first instanceof OtherBroadcastReceiverMock);
	}
}
