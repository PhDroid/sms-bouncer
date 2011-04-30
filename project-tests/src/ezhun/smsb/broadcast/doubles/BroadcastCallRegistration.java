package ezhun.smsb.broadcast.doubles;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Please, write short description of what this file is for.
 */
public class BroadcastCallRegistration {
	private static BroadcastCallRegistration ourInstance = new BroadcastCallRegistration();
	private ArrayList<Pair<BroadcastReceiver, Intent>> callItems;


	public static BroadcastCallRegistration getInstance() {
		return ourInstance;
	}

	public void registerCall(BroadcastReceiver from, Intent intent){
		Pair<BroadcastReceiver, Intent> p = new Pair<BroadcastReceiver, Intent>(from, intent);
		callItems.add(p);
	}

	public int size(){
		return callItems.size();
	}

	public void clear(){
		callItems.clear();
	}

	public List<Pair<BroadcastReceiver, Intent>> getItems() {
		return callItems;
	}

	private BroadcastCallRegistration() {
		callItems = new ArrayList<Pair<BroadcastReceiver, Intent>>();
	}
}
