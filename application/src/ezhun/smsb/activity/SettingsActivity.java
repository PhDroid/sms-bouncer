package ezhun.smsb.activity;

import android.app.Activity;
import android.content.Intent;
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

	private AdapterView.OnItemSelectedListener clearAfterListener = new AdapterView.OnItemSelectedListener() {
		@Override
		public void onItemSelected(AdapterView adapterView, View view, int i, long l) {
			Toast.makeText(getApplicationContext(),"You've got an event",Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onNothingSelected(AdapterView<?> adapterView) {
			//throw new NotImplementedException();
		}
	};

	private View.OnClickListener editWhitelistListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(SettingsActivity.this, EditWhitelistActivity.class);
				startActivity(intent);
		}
	};


	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);

		Button btnEditWhitelist = (Button) findViewById(R.id.btnEditWhitelist);
		btnEditWhitelist.setOnClickListener(editWhitelistListener);

		Spinner ddlClearWhitelist = (Spinner) findViewById(R.id.ddlDisplayNotification);
		ddlClearWhitelist.setAdapter(new ClearAfterSpinnerAdapter(getApplicationContext()));
		ddlClearWhitelist.setOnItemSelectedListener(clearAfterListener);

		CheckBox cbDisplayNotification = (CheckBox) findViewById(R.id.cbNotification);
		cbDisplayNotification.setOnCheckedChangeListener(displayNotificationListener);
	}
}