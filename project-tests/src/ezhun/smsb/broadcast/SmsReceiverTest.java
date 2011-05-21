package ezhun.smsb.broadcast;

import android.content.ContentResolver;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.test.AndroidTestCase;
import android.test.mock.MockContext;
import ezhun.smsb.SmsPojo;
import junit.framework.Assert;

import java.nio.charset.Charset;
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

        public SmsPojo[] ProcessMessages(SmsPojo[] messages, ContentResolver resolver) {
            mProcessMethodWasCalled = true;
            mMessagesCount = messages.length;
            ArrayList<SmsPojo> res = new ArrayList<SmsPojo>();
	        for(SmsPojo message : messages){
                if(message.getSender().equals("666")){
                    res.add(message);
                }
            }
	        SmsPojo[] resArray = new SmsPojo[res.size()];
            return res.toArray(resArray);
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

        public int getSpamMessagesCount(){
            return super.getSpamMessagesCount();
        }

        @Override
        protected IMessageProcessor getMessageProcessor(){
            return mProcessor;
        }

        @Override
        protected SmsPojo[] ConvertMessages(Object[] pdusObj){
            SmsPojo[] pojos = new SmsPojo[1];
            SmsPojo message = pojos[0] = new SmsPojo();
            if (((byte[])pdusObj[0])[0] == 49){
                // spam
                message.setMessage("How YOU doin'?");
                message.setSender("666");
            }
            else{
                // not spam
                message.setMessage("How YOU doin'?");
                message.setSender("555");
            }
            return pojos;
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

    public void testSmsReceiver_doesnt_process_messages_if_none_received() throws Exception {
        Intent intent = new Intent();
        intent.setAction(ACTION);
        mReceiver.onReceive(getContext(), intent);
        Assert.assertFalse(mReceiver.getProcessor().getProcessMethodWasCalled());
    }

    public void testSmsReceiver_processes_messages() throws Exception {
        Intent intent = new Intent();
        intent.setAction(ACTION);
        Object[] pdus = new Object[1];
        //from:666; message:How you doin'?
        pdus[0] = new byte[] {49, 50};
        intent.putExtra("pdus", pdus);

        mReceiver.onReceive(getContext(), intent);
        Assert.assertTrue(mReceiver.getProcessor().getProcessMethodWasCalled());
    }

    public void testSmsReceiver_dont_pass_spam_messages(){
        Intent intent = new Intent();
        intent.setAction(ACTION);
        Object[] pdus = new Object[1];
        //from:666; message:How you doin'?
        pdus[0] = new byte[] {49, 50};
        intent.putExtra("pdus", pdus);

        mReceiver.onReceive(getContext(), intent);
        Assert.assertEquals(1, mReceiver.getSpamMessagesCount()) ;
        Assert.assertEquals(1, mReceiver.getProcessor().getMessagesCount()) ;
    }

    public void testSmsReceiver_passes_non_spam_messages(){
        Intent intent = new Intent();
        intent.setAction(ACTION);
        Object[] pdus = new Object[1];
        //from:666; message:How you doin'?
        pdus[0] = new byte[] {50, 51};
        intent.putExtra("pdus", pdus);

        mReceiver.onReceive(getContext(), intent);
        Assert.assertEquals(0, mReceiver.getSpamMessagesCount()) ;
        Assert.assertEquals(1, mReceiver.getProcessor().getMessagesCount()) ;
    }
}
