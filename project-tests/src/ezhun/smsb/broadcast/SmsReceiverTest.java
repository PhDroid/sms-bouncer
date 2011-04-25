package ezhun.smsb.broadcast;

import android.content.Intent;
import android.test.AndroidTestCase;
import android.test.mock.MockContext;

import java.util.ArrayList;
import java.util.List;

public class SmsReceiverTest extends AndroidTestCase {

    private SmsReceiver mReceiver;
    private TestContext mContext;
    static final String ACTION = "android.provider.Telephony.SMS_RECEIVED";

    private class TestContext extends MockContext{
        private List<Intent> mReceivedIntents = new ArrayList<Intent>();

        @Override
        public String getPackageName()
        {
           return "ezhun.smsb.test";
        }

        @Override
        public void startActivity(Intent xiIntent)
        {
           mReceivedIntents.add(xiIntent);
        }

        public List<Intent> getReceivedIntents()
        {
           return mReceivedIntents;
        }
    }

    @Override
    protected void setUp(){
        mReceiver = new SmsReceiver();
        mContext = new TestContext();
    }

    public void testSmsReceiver_receives_messages() throws Exception {
        Intent intent = new Intent();
        intent.setAction(ACTION);

        mReceiver.onReceive(getContext(), intent);
        assertEquals(1, mContext.getReceivedIntents().size());
        assertNull(mReceiver.getResultData());

        Intent receivedIntent = mContext.getReceivedIntents().get(0);
        assertNull(receivedIntent.getAction());
        assertEquals("01234567890", receivedIntent.getStringExtra("phoneNum"));
        assertTrue((receivedIntent.getFlags() & Intent.FLAG_ACTIVITY_NEW_TASK) != 0);
    }

    public void testSmsReceiver_dont_pass_spam_messages(){

    }

    public void testSmsReceiver_passes_non_spam_messages(){

    }
}
