package ezhun.smsb.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import ezhun.smsb.R;

/**
 * Settings of the SMS bouncer application.
 */
public class SettingsActivity extends Activity {
	private CompoundButton.OnCheckedChangeListener displayNotificationListener = new CompoundButton.OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
			// do something when the button is clicked
		}
	};

	private AdapterView.OnItemClickListener clearAfterListener = new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
			// do something when the button is clicked
		}
	};

	private View.OnClickListener editWhitelistListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			// do something when the button is clicked
		}
	};


	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);

		Button btnEditWhitelist = (Button) findViewById(R.id.btnEditWhitelist);
		btnEditWhitelist.setOnClickListener(editWhitelistListener);

		Spinner ddlClearWhitelist = (Spinner) findViewById(R.id.ddlDisplayNotification);
//		ddlClearWhitelist.setOnItemClickListener(clearAfterListener);

		CheckBox cbDisplayNotification = (CheckBox) findViewById(R.id.cbNotification);
		cbDisplayNotification.setOnCheckedChangeListener(displayNotificationListener);
	}
}