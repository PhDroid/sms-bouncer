package com.phdroid.smsb.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.phdroid.smsb.R;
import com.phdroid.smsb.storage.ApplicationSettings;
import com.phdroid.smsb.storage.DeleteAfter;

/**
 * Settings of the SMS bouncer application.
 */
public class SettingsActivity extends Activity {
	private ApplicationSettings settings;

	public ApplicationSettings getApplicationSettings() {
		if (settings == null) {
			settings = new ApplicationSettings(this);
		}
		return settings;
	}

	private CompoundButton.OnCheckedChangeListener displayNotificationListener = new CompoundButton.OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
			getApplicationSettings().setDisplayNotification(b);
		}
	};

	private AdapterView.OnItemSelectedListener clearAfterListener = new AdapterView.OnItemSelectedListener() {
		@Override
		public void onItemSelected(AdapterView adapterView, View view, int i, long l) {
			DeleteAfter item = (DeleteAfter) adapterView.getAdapter().getItem(i);
			getApplicationSettings().setDeleteAfter(item);
			Toast.makeText(getApplicationContext(), item.toString(), Toast.LENGTH_SHORT).show();
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

		//Edit whitelist button init
		Button btnEditWhitelist = (Button) findViewById(R.id.btnEditWhitelist);
		btnEditWhitelist.setOnClickListener(editWhitelistListener);

		//Spinner init
		Spinner ddlClearWhitelist = (Spinner) findViewById(R.id.ddlClear);
		ClearAfterSpinnerAdapter adapter = new ClearAfterSpinnerAdapter(getApplicationContext(), R.layout.spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		ddlClearWhitelist.setAdapter(adapter);

		int index = getItemIndex(adapter);

		ddlClearWhitelist.setSelection(index, false);
		ddlClearWhitelist.setOnItemSelectedListener(clearAfterListener);

		//checkbox init
		CheckBox cbDisplayNotification = (CheckBox) findViewById(R.id.cbNotification);
		cbDisplayNotification.setChecked(getApplicationSettings().showDisplayNotification());
		cbDisplayNotification.setOnCheckedChangeListener(displayNotificationListener);

	}

	private int getItemIndex(ClearAfterSpinnerAdapter adapter) {
		int index = 0;
		for (int i = 0; i < adapter.getCount(); i++) {
			DeleteAfter item = (DeleteAfter) adapter.getItem(i);
			if (item == getApplicationSettings().getDeleteAfter()) {
				index = i;
				break;
			}
		}
		return index;
	}
}