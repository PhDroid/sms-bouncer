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
    private int mId = -1;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_message);

        Bundle b = getIntent().getExtras();
        mId = b.getInt("id", -1);
        if(mId >= 0){
            SmsPojo sms =  GetMessageProvider().getMessage(mId);

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

            GetMessageProvider().read(mId);

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

    public void deleteMessage(View view) {
        GetMessageProvider().delete(mId);
        finish();
    }

    public void markMessageAsNotSpam(View view) {
        GetMessageProvider().notSpam(mId);
        finish();
    }

}