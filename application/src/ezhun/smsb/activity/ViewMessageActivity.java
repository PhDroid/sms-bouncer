package ezhun.smsb.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import ezhun.smsb.R;
import android.app.Activity;
import android.os.Bundle;
import ezhun.smsb.SmsPojo;
import ezhun.smsb.exceptions.ArgumentException;
import ezhun.smsb.storage.IMessageProvider;
import ezhun.smsb.storage.MessageProviderHelper;

public class ViewMessageActivity extends Activity {

    IMessageProvider mProvider;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_message);

        Bundle b = getIntent().getExtras();
        int id = b.getInt("id", -1);
        if(id >= 0){
            SmsPojo sms =  GetMessageProvider().getMessage(id);

            TextView sender = (TextView) findViewById(R.id.senderTextView);
            TextView received = (TextView) findViewById(R.id.receivedTimeTextView);
            TextView message = (TextView) findViewById(R.id.messageTextView);

            sender.setText(sms.getSender());
            received.setText(String.valueOf(sms.getReceived()));
            message.setText(sms.getMessage());
        }
    }


    protected IMessageProvider GetMessageProvider() {
         if (mProvider == null){
             mProvider = MessageProviderHelper.getMessageProvider();
         }

         return mProvider;
    }
}