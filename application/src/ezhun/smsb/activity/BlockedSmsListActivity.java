package ezhun.smsb.activity;

import android.app.Activity;
import android.app.ListActivity;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import ezhun.smsb.R;
import ezhun.smsb.SmsPojo;

import java.util.ArrayList;

public class BlockedSmsListActivity extends ListActivity {
	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        ArrayList<SmsPojo> messages = getMessages();

        setListAdapter(new SmsPojoArrayAdapter(this, R.layout.main_list_item, messages));
        ListView lv = getListView();

        /*lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          public void onItemClick(AdapterView<?> parent, View view,
              int position, long id) {
            // When clicked, show a toast with the TextView text
            Toast.makeText(getApplicationContext(), ((TextView) view).getText(),
                Toast.LENGTH_SHORT).show();
          }
        });*/

	}

    /*
     * TODO: replace this with real stuff
     */
    private ArrayList<SmsPojo> getMessages() {
        ArrayList<SmsPojo> messages = new ArrayList<SmsPojo>();
        SmsPojo sms = new SmsPojo();
        sms.setMessage("Hey you there! How you doin'?");
        sms.setSender("+380971122333");
        messages.add(sms);

        sms = new SmsPojo();
        sms.setMessage("Nova aktsia vid magazinu Target! Kupuy 2 kartopli ta otrymay 1 v podarunok!");
        sms.setSender("TARGET");
        messages.add(sms);

        sms = new SmsPojo();
        sms.setMessage("This is my new number. B. Obama.");
        sms.setSender("+1570333444555");
        messages.add(sms);

        sms = new SmsPojo();
        sms.setMessage("How about having some beer tonight? Stranger.");
        sms.setSender("+380509998887");
        sms.setRead(true);
        messages.add(sms);

        sms = new SmsPojo();
        sms.setMessage("Vash rakhunok na 9.05.2011 stanovyt 27,35 uah.");
        sms.setSender("KYIVSTAR");
        messages.add(sms);

        sms = new SmsPojo();
        sms.setMessage("Skydky v ALDO. Kupuy 1 ked ta otrymuy dryguy v podarunok!");
        sms.setSender("ALDO");
        messages.add(sms);

        sms = new SmsPojo();
        sms.setMessage("Big football tonight v taverne chili!");
        sms.setSender("+380991001010");
        sms.setRead(true);
        messages.add(sms);

        sms = new SmsPojo();
        sms.setMessage("15:43 ZABLOKOVANO 17,99 UAH, SUPERMARKET PORTAL");
        sms.setSender("PUMB");
        messages.add(sms);

        return messages;
    }

    @Override
	protected void onStart() {
		super.onStart();
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_menu, menu);
        return true;
    }
}