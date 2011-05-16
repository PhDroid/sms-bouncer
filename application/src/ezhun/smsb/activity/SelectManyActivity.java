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


public class SelectManyActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.select_many);
	}

    @Override
	protected void onStart() {
		super.onStart();

        ArrayList<SmsPojo> messages = GetMessageProvider().getMessages();
        ListView lv = (ListView)findViewById(R.id.messagesListView);
        lv.setAdapter(new SmsPojoArrayAdapter(this, R.layout.select_many_list_item, messages));
        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

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