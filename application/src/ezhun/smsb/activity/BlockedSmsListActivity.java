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

import java.util.ArrayList;

public class BlockedSmsListActivity extends ListActivity {
	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
    }

    protected IMessageProvider GetMessageProvider() {
         return MessageProviderHelper.getMessageProvider();
    }

    @Override
	protected void onStart() {
		super.onStart();

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
            case R.id.select_many_item:
                Intent smIntent = new Intent(BlockedSmsListActivity.this, SelectManyActivity.class);
				startActivity(smIntent);
                return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}