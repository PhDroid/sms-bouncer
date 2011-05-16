package ezhun.smsb.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Debug;
import android.text.format.DateUtils;
import android.text.format.Time;
import android.text.method.DateTimeKeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import ezhun.smsb.R;
import ezhun.smsb.SmsPojo;

import java.security.Timestamp;
import java.util.Date;
import java.util.List;

public class SmsPojoArrayAdapter extends ArrayAdapter<SmsPojo> {
    Activity ctx;
    int mViewId;
    LayoutInflater mInflater;

    public SmsPojoArrayAdapter(Activity context, int textViewResourceId) {
        super(context, textViewResourceId);
        ctx = context;
        mViewId = textViewResourceId;
        mInflater = ctx.getLayoutInflater();
    }

    public SmsPojoArrayAdapter(Activity context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
        ctx = context;
        mViewId = textViewResourceId;
        mInflater = ctx.getLayoutInflater();
    }

    public SmsPojoArrayAdapter(Activity context, int textViewResourceId, SmsPojo[] objects) {
        super(context, textViewResourceId, objects);
        ctx = context;
        mViewId = textViewResourceId;
        mInflater = ctx.getLayoutInflater();
    }

    public SmsPojoArrayAdapter(Activity context, int resource, int textViewResourceId, SmsPojo[] objects) {
        super(context, resource, textViewResourceId, objects);
        ctx = context;
        mViewId = textViewResourceId;
        mInflater = ctx.getLayoutInflater();
    }

    public SmsPojoArrayAdapter(Activity context, int textViewResourceId, List<SmsPojo> objects) {
        super(context, textViewResourceId, objects);
        ctx = context;
        mViewId = textViewResourceId;
        mInflater = ctx.getLayoutInflater();
    }

    public SmsPojoArrayAdapter(Activity context, int resource, int textViewResourceId, List<SmsPojo> objects) {
        super(context, resource, textViewResourceId, objects);
        ctx = context;
        mViewId = textViewResourceId;
        mInflater = ctx.getLayoutInflater();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        SmsViewHolder holder;
        if(convertView == null){
            convertView = mInflater.inflate(mViewId, null);
            holder = new SmsViewHolder();
            holder.sender = (TextView) convertView.findViewById(R.id.senderTextView);
            holder.received = (TextView) convertView.findViewById(R.id.receivedTimeTextView);
            holder.message = (TextView) convertView.findViewById(R.id.messageTextView);

            convertView.setTag(holder);
        } else {
            holder = (SmsViewHolder) convertView.getTag();
        }

        SmsPojo sms = this.getItem(position);
        holder.sender.setText(sms.getSender());
        holder.received.setText(DateUtils.getRelativeDateTimeString(
                ctx,
                sms.getReceived(),
                DateUtils.MINUTE_IN_MILLIS,
                DateUtils.WEEK_IN_MILLIS,
                DateUtils.FORMAT_ABBREV_RELATIVE));
        holder.message.setText(sms.getMessage());

        holder.sender.setTypeface(holder.sender.getTypeface(), sms.wasRead() ? 0 : 1);
        holder.message.setTypeface(holder.message.getTypeface(), sms.wasRead() ? 0 : 1);

        return (convertView);

    }
}
