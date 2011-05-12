package ezhun.smsb.activity;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import ezhun.smsb.R;
import ezhun.smsb.SmsPojo;
import ezhun.smsb.storage.IMessageProvider;
import ezhun.smsb.storage.MessageProviderHelper;

import java.util.ArrayList;


public class SelectManyActivity extends ListActivity {
    IMessageProvider mProvider;

    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        ArrayList<SmsPojo> messages = GetMessageProvider().getMessages();

        setListAdapter(new SmsPojoArrayAdapter(this, R.layout.select_many_list_item, messages));
        ListView lv = getListView();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(!view.isSelected()){
                    view.setSelected(true);
                    CheckBox cb = (CheckBox)view.findViewById(R.id.selectMessageCheckBox);
                    cb.setChecked(true);
                }
                else{
                    view.setSelected(false);
                    CheckBox cb = (CheckBox)view.findViewById(R.id.selectMessageCheckBox);
                    cb.setChecked(false);
                }
            }
        });

	}

    protected IMessageProvider GetMessageProvider() {
         if (mProvider == null){
             mProvider = MessageProviderHelper.getMessageProvider();
         }

         return mProvider;
    }
}