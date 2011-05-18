package ezhun.smsb.activity;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RelativeLayout;
import ezhun.smsb.R;
import ezhun.smsb.SmsPojo;
import ezhun.smsb.storage.IMessageProvider;
import ezhun.smsb.storage.MessageProviderHelper;

import java.util.ArrayList;


public class SelectManyActivity extends Activity {
    View listFooter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.select_many);
        listFooter = findViewById(R.id.listFooter);
	}

    @Override
	protected void onStart() {
		super.onStart();

        ArrayList<SmsPojo> messages = GetMessageProvider().getMessages();
        final ListView lv = (ListView)findViewById(R.id.messagesListView);
        lv.setAdapter(new SmsPojoArrayAdapter(this, R.layout.select_many_list_item, messages));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(lv.getCheckedItemPositions().size() > 0){
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

    //@Override
    public void onListItemClick(ListView lv, View view, int position, long id){
        view.setSelected(!view.isSelected());
        //CheckBox cb = (CheckBox)view.findViewById(R.id.selectMessageCheckBox);
        //cb.setChecked(!cb.isChecked());
    }


    protected IMessageProvider GetMessageProvider() {
         return MessageProviderHelper.getMessageProvider();
    }
}