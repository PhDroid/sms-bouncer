package com.phdroid.smsb.activity;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.*;
import com.phdroid.smsb.R;
import com.phdroid.smsb.SmsPojo;
import com.phdroid.smsb.storage.IMessageProvider;
import com.phdroid.smsb.storage.MessageProviderHelper;

import java.util.ArrayList;


public class SelectManyActivity extends Activity {
    View listFooter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.select_many);
        listFooter = findViewById(R.id.listFooter);
        //ColorDrawable c = new ColorDrawable(R.color.red_button_color_shift);
        Button button = (Button)findViewById(R.id.deleteButton);
        button.getBackground().setColorFilter(0xFFFF9999, PorterDuff.Mode.MULTIPLY);
        button = (Button)findViewById(R.id.notSpamButton);
        button.getBackground().setColorFilter(0xFF99FF99, PorterDuff.Mode.MULTIPLY);

	}

    @Override
	protected void onStart() {
		super.onStart();

        ArrayList<SmsPojo> messages = GetMessageProvider().getMessages();
        final ListView lv = (ListView)findViewById(R.id.messagesListView);
        lv.setAdapter(new SmsPojoArrayAdapter(this, R.layout.select_many_list_item, messages));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int size = 0;
                for(int i =0; i < lv.getChildCount() ; i++){
                    if (lv.isItemChecked(i)){
                        size++;
                    }
                }

                if(size > 0){
                    listFooter.setVisibility(View.VISIBLE);
                } else {
                    listFooter.setVisibility(View.GONE);
                }
            }
        });

        setTitle(R.string.app_name);
        setTitle(String.format(
                    "%s (%s)",
                    getTitle().toString(),
                    Integer.toString(GetMessageProvider().getUnreadCount())));
    }

    protected IMessageProvider GetMessageProvider() {
         return MessageProviderHelper.getMessageProvider();
    }

    public void deleteMessages(View view) {
        ListView lv = (ListView)findViewById(R.id.messagesListView);
        SparseBooleanArray positions = lv.getCheckedItemPositions();
        long[] ids = new long[positions.size()];
        for(int i=0; i<positions.size(); i++){
            ids[i] = positions.keyAt(i);
        }
        GetMessageProvider().delete(ids);
        finish();
    }

    public void markMessagesAsNotSpam(View view) {
        ListView lv = (ListView)findViewById(R.id.messagesListView);
        SparseBooleanArray positions = lv.getCheckedItemPositions();
        long[] ids = new long[positions.size()];
        for(int i=0; i<positions.size(); i++){
            ids[i] = positions.keyAt(i);
        }
        GetMessageProvider().notSpam(ids);
        finish();
    }
}