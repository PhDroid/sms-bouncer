package com.phdroid.smsb.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.*;
import android.widget.*;
import android.content.Intent;
import android.view.Menu;
import com.phdroid.smsb.R;
import com.phdroid.smsb.SmsPojo;
import com.phdroid.smsb.storage.IMessageProvider;
import com.phdroid.smsb.storage.MessageProviderHelper;
import com.phdroid.smsb.storage.SmsAction;

import java.util.Hashtable;
import java.util.List;

public class BlockedSmsListActivity extends Activity {

	private SmsPojoArrayAdapter smsPojoArrayAdapter;

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	protected IMessageProvider getMessageProvider() {
		 return MessageProviderHelper.getMessageProvider(this.getContentResolver());
	}

	@Override
	protected void onStart() {
		super.onStart();

		processUndoButton();

		List<SmsPojo> messages = getMessageProvider().getMessages();
		ListView lv = (ListView)findViewById(R.id.messagesListView);
		smsPojoArrayAdapter = new SmsPojoArrayAdapter(this, R.layout.main_list_item, messages);
		lv.setAdapter(smsPojoArrayAdapter);

		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(BlockedSmsListActivity.this, ViewMessageActivity.class);
				Bundle b = new Bundle();
				b.putInt("id", position);// TODO: change with message ID
				intent.putExtras(b);
				getMessageProvider().performActions();
				startActivity(intent);
			}
		});

		updateTitle();
	}

	private void processUndoButton() {
		Hashtable<SmsPojo, SmsAction> actions = getMessageProvider().getActionMessages();
		if(actions.size() > 0){
			Button b = (Button)findViewById(R.id.undoButton);
			String action = "edited";
			if(!actions.contains(SmsAction.MarkedAsNotSpam)){
				action = "deleted";
			}
			if(!actions.contains(SmsAction.Deleted)){
				action = "marked as not spam";
			}
			b.setText(
				String.format(
						"%s message%s %s. (Undo)",
						Integer.toString(actions.size()),
						actions.size() > 1 ? "s were" : " was",
						action
					));
			LinearLayout l = (LinearLayout)findViewById(R.id.buttonLayout);
			l.setVisibility(View.VISIBLE);
		}
		else{
			LinearLayout l = (LinearLayout)findViewById(R.id.buttonLayout);
			l.setVisibility(View.GONE);
		}
	}

	private void updateTitle() {
		setTitle(R.string.app_name);
		setTitle(String.format(
					"%s%s",
					getTitle().toString(),
					getMessageProvider().getUnreadCount() > 0 ?
						String.format(" (%s)", Integer.toString(getMessageProvider().getUnreadCount())) : ""));
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		if(getMessageProvider().getMessages().size() == 0){
			MenuItem item = menu.findItem(R.id.delete_all_item);
			item.setEnabled(false);
			item = menu.findItem(R.id.select_many_item);
			item.setEnabled(false);
		}
		else {
			MenuItem item = menu.findItem(R.id.delete_all_item);
			item.setEnabled(true);
			item = menu.findItem(R.id.select_many_item);
			item.setEnabled(true);
		}

		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.list_menu, menu);
		setMenuBackground();
		return true;
	}

	protected void setMenuBackground(){
		getLayoutInflater().setFactory( new LayoutInflater.Factory() {

			@Override
			public View onCreateView ( String name, Context context, AttributeSet attrs ) {
				if ( name.equalsIgnoreCase( "com.android.internal.view.menu.IconMenuItemView" ) ) {
					try {
						LayoutInflater f = getLayoutInflater();
						final View view = f.createView( name, null, attrs );
						new Handler().post( new Runnable() {
							public void run () {
								view.setBackgroundResource( R.drawable.menu_item);
							}
						} );
						return view;
					}
					catch ( InflateException e ) { /*ignore*/ }
					catch ( ClassNotFoundException e ) { /*ignore*/ }
				}
				return null;
			}
		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
			case R.id.settings_item:
				Intent intent = new Intent(BlockedSmsListActivity.this, SettingsActivity.class);
				getMessageProvider().performActions();
				startActivity(intent);
				return true;
			case R.id.select_many_item:
				Intent smIntent = new Intent(BlockedSmsListActivity.this, SelectManyActivity.class);
				getMessageProvider().performActions();
				startActivity(smIntent);
				return true;
			case R.id.delete_all_item:
				getMessageProvider().performActions();
				getMessageProvider().deleteAll();
				smsPojoArrayAdapter.notifyDataSetChanged();
				processUndoButton();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	public void undo(View view) {
		getMessageProvider().undo();
		LinearLayout l = (LinearLayout)findViewById(R.id.buttonLayout);
		l.setVisibility(View.GONE);
		smsPojoArrayAdapter.notifyDataSetChanged();
		updateTitle();
	}
}
