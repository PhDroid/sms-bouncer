package ezhun.smsb.broadcast;

import android.content.ContentResolver;
import android.content.Intent;
import android.test.AndroidTestCase;
import android.test.mock.MockContext;
import ezhun.smsb.SmsPojo;
import junit.framework.Assert;

import java.util.ArrayList;
import java.util.List;

public class SmsReceiverTest extends AndroidTestCase {

    private TestSmsReceiver mReceiver;
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

    private class TestMessageProcessor implements IMessageProcessor{
        private int mMessagesCount;
        private boolean mProcessMethodWasCalled = false;

        public int ProcessMessages(SmsPojo[] messages, ContentResolver resolver) {
            mProcessMethodWasCalled = true;
            mMessagesCount = messages.length;
            int spamCount = 0;
            for(SmsPojo message : messages){
                if(message.getSender() == "666"){
                    spamCount++;
                }
            }
            return spamCount;
        }

        public int getMessagesCount(){
            return mMessagesCount;
        }

        public boolean getProcessMethodWasCalled(){
            return mProcessMethodWasCalled;
        }
    }

    private class TestSmsReceiver extends SmsReceiver{
        private TestMessageProcessor mProcessor;

        public TestSmsReceiver(){
            mProcessor = new TestMessageProcessor();
        }

        @Override
        protected IMessageProcessor getMessageProcessor(){
            return mProcessor;
        }

        public TestMessageProcessor getProcessor(){
            return mProcessor;
        }
    }

    @Override
    protected void setUp(){
        mReceiver = new TestSmsReceiver();
        mContext = new TestContext();
    }

    public void testSmsReceiver_processes_messages() throws Exception {
        Intent intent = new Intent();
        intent.setAction(ACTION);
        mReceiver.onReceive(getContext(), intent);
        Assert.assertTrue(mReceiver.getProcessor().getProcessMethodWasCalled());
    }

    public void testSmsReceiver_dont_pass_spam_messages(){

    }

    public void testSmsReceiver_passes_non_spam_messages(){

    }
}
