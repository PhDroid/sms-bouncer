package ezhun.smsb.activity;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.ListView;
import ezhun.smsb.R;

public class BlockedSmsListActivityTest extends ActivityInstrumentationTestCase2<BlockedSmsListActivity> {
	private BlockedSmsListActivity mActivity;
	private ListView mList;
	private SmsPojoArrayAdapter mAdapter;
	private static final int ADAPTER_COUNT = 9;

	public BlockedSmsListActivityTest(){
		super("ezhun.smsb.activity.BlockedSmsListActivity", BlockedSmsListActivity.class);
	}

	public BlockedSmsListActivityTest(String pkg, Class activityClass) {
		super(pkg, activityClass);
	}

	public BlockedSmsListActivityTest(Class activityClass) {
		super(activityClass);
	}

	public void setUp() throws Exception {
		super.setUp();
		setActivityInitialTouchMode(false);
		mActivity = getActivity();
		mList = (ListView)mActivity.findViewById(R.id.messagesListView);
		mAdapter = (SmsPojoArrayAdapter) mList.getAdapter();
	}

	public void testPreConditions() {
		assertTrue(mAdapter != null);
		assertEquals(mAdapter.getCount(),ADAPTER_COUNT);
	}

	public void testUndoButtonIsInvisibleAtStartup(){
		View v = mActivity.findViewById(R.id.buttonLayout);
		assertEquals(v.getVisibility(), View.GONE);
	}
}
