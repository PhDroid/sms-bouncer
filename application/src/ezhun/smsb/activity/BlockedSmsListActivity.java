package ezhun.smsb.activity;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import android.content.Intent;
import android.view.Menu;
import ezhun.smsb.R;
import ezhun.smsb.SmsPojo;
import ezhun.smsb.storage.IMessageProvider;
import ezhun.smsb.storage.MessageProviderHelper;
import ezhun.smsb.storage.TestMessageProvider;

import java.util.ArrayList;

public class BlockedSmsListActivity extends ListActivity {
    IMessageProvider mProvider;

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        ArrayList<SmsPojo> messages = GetMessageProvider().getMessages();

        setListAdapter(new SmsPojoArrayAdapter(this, R.layout.main_list_item, messages));
        ListView lv = getListView();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(BlockedSmsListActivity.this, ViewMessageActivity.class);
                Bundle b = new Bundle();
                b.putInt("id", position);// TODO: change with message ID
                intent.putExtras(b);
				startActivity(intent);
            }
        });

	}

    protected IMessageProvider GetMessageProvider() {
         if (mProvider == null){
             mProvider = MessageProviderHelper.getMessageProvider();
         }

         return mProvider;
    }

    @Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.list_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
			case R.id.settings_item:
				Intent intent = new Intent(BlockedSmsListActivity.this, SettingsActivity.class);
				startActivity(intent);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}