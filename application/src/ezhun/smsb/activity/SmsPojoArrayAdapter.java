package ezhun.smsb.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.text.format.DateUtils;
import android.text.format.Time;
import android.text.method.DateTimeKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import ezhun.smsb.R;
import ezhun.smsb.SmsPojo;

import java.security.Timestamp;
import java.util.Date;
import java.util.List;

public class SmsPojoArrayAdapter extends ArrayAdapter<SmsPojo> {
    Activity ctx;
    int mViewId;

    public SmsPojoArrayAdapter(Activity context, int textViewResourceId) {
        super(context, textViewResourceId);
        ctx = context;
        mViewId = textViewResourceId;
    }

    public SmsPojoArrayAdapter(Activity context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
        ctx = context;
        mViewId = textViewResourceId;
    }

    public SmsPojoArrayAdapter(Activity context, int textViewResourceId, SmsPojo[] objects) {
        super(context, textViewResourceId, objects);
        ctx = context;
        mViewId = textViewResourceId;
    }

    public SmsPojoArrayAdapter(Activity context, int resource, int textViewResourceId, SmsPojo[] objects) {
        super(context, resource, textViewResourceId, objects);
        ctx = context;
        mViewId = textViewResourceId;
    }

    public SmsPojoArrayAdapter(Activity context, int textViewResourceId, List<SmsPojo> objects) {
        super(context, textViewResourceId, objects);
        ctx = context;
        mViewId = textViewResourceId;
    }

    public SmsPojoArrayAdapter(Activity context, int resource, int textViewResourceId, List<SmsPojo> objects) {
        super(context, resource, textViewResourceId, objects);
        ctx = context;
        mViewId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = ctx.getLayoutInflater();
        View row = inflater.inflate(mViewId, null);
        TextView sender = (TextView) row.findViewById(R.id.senderTextView);
        TextView received = (TextView) row.findViewById(R.id.receivedTimeTextView);
        TextView message = (TextView) row.findViewById(R.id.messageTextView);

        SmsPojo sms = this.getItem(position);
        sender.setText(sms.getSender());
        received.setText(DateUtils.getRelativeDateTimeString(
                ctx,
                sms.getReceived(),
                DateUtils.MINUTE_IN_MILLIS,
                DateUtils.WEEK_IN_MILLIS,
                DateUtils.FORMAT_ABBREV_RELATIVE));
        message.setText(sms.getMessage());

        if(!sms.wasRead()){
            sender.setTypeface(sender.getTypeface(), 1);
            message.setTypeface(message.getTypeface(), 1);
        }

        return (row);

    }
}
