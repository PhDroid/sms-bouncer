package com.phdroid.smsb.activity;

import android.app.ListActivity;
import android.os.Bundle;
import com.phdroid.smsb.R;
import com.phdroid.smsb.SenderPojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity designed for Whitelist operations.
 */
public class EditWhitelistActivity extends ListActivity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.editwhitelist);

		setListAdapter(new SenderPojoArrayAdapter(this, R.layout.main_list_item, getSenders()));
	}

	//TODO: replace dummy method
	private List<SenderPojo> getSenders() {
		ArrayList<SenderPojo> senders = new ArrayList<SenderPojo>();
		SenderPojo sender = new SenderPojo();
        sender.setSender("+380971122333");
        senders.add(sender);

        sender = new SenderPojo();
        sender.setSender("TARGET");
        senders.add(sender);

        sender = new SenderPojo();
        sender.setSender("+1570333444555");
        senders.add(sender);

        sender = new SenderPojo();
        sender.setSender("+380509998887");
        senders.add(sender);

        sender = new SenderPojo();
        sender.setSender("KYIVSTAR");
        senders.add(sender);

        sender = new SenderPojo();
        sender.setSender("ALDO");
        senders.add(sender);

        sender = new SenderPojo();
        sender.setSender("+380991001010");
        senders.add(sender);

        sender = new SenderPojo();
        sender.setSender("PUMB");
        senders.add(sender);

		return senders;
	}
}