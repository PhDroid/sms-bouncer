package ezhun.smsb.activity;

import android.text.format.DateUtils;
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
            received.setText(DateUtils.getRelativeDateTimeString(
                    this,
                    sms.getReceived(),
                    DateUtils.MINUTE_IN_MILLIS,
                    DateUtils.WEEK_IN_MILLIS,
                    DateUtils.FORMAT_ABBREV_RELATIVE));
            message.setText(sms.getMessage());

            GetMessageProvider().read(id);

            setTitle(R.string.app_name);
            setTitle(String.format(
                    "%s (%s)",
                    getTitle().toString(),
                    Integer.toString(GetMessageProvider().getUnreadCount())));
        }
    }


    protected IMessageProvider GetMessageProvider() {
        return MessageProviderHelper.getMessageProvider();
    }
}