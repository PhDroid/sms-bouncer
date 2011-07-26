package com.phdroid.smsb.storage;

import android.content.ContentResolver;
import android.telephony.SmsMessage;
import com.phdroid.smsb.SmsPojo;
import com.phdroid.smsb.TestSmsPojo;
import com.phdroid.smsb.storage.dao.Session;
import com.phdroid.smsb.storage.dao.SmsMessageEntry;
import com.phdroid.smsb.storage.dao.SmsMessageSenderEntry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;

public class TestSession extends Session {
	ArrayList<SmsPojo> mList;
	Hashtable<SmsPojo, SmsAction> mActions;
	int mUnreadCount = 0;

	public TestSession(ApplicationSettings settings, ContentResolver contentResolver) {
		super(settings, contentResolver);

		mActions = new Hashtable<SmsPojo, SmsAction>();
		mList = new ArrayList<SmsPojo>();
		Calendar c = Calendar.getInstance();

		TestSmsPojo sms = new TestSmsPojo();
		sms.setMessage("Hey you there! How you doin'?");
		sms.setSender("+380971122333");
		sms.setReceived(c.getTime().getTime());
		mList.add(sms);

		sms = new TestSmsPojo();
		sms.setMessage("Nova aktsia vid magazinu Target! Kupuy 2 kartopli ta otrymay 1 v podarunok!");
		sms.setSender("TARGET");
		c.add(Calendar.SECOND, -30);
		sms.setReceived(c.getTime().getTime());
		mList.add(sms);

		sms = new TestSmsPojo();
		sms.setMessage("This is my new number. B. Obama.");
		sms.setSender("+1570333444555");
		c.add(Calendar.MINUTE, -5);
		sms.setReceived(c.getTime().getTime());
		mList.add(sms);

		sms = new TestSmsPojo();
		sms.setMessage("How about having some beer tonight? Stranger.");
		sms.setSender("+380509998887");
		sms.setRead(true);
		c.add(Calendar.MINUTE, -30);
		sms.setReceived(c.getTime().getTime());
		mList.add(sms);

		sms = new TestSmsPojo();
		sms.setMessage("Vash rakhunok na 9.05.2011 stanovyt 27,35 uah.");
		sms.setSender("KYIVSTAR");
		c.add(Calendar.MINUTE, -20);
		sms.setReceived(c.getTime().getTime());
		mList.add(sms);

		sms = new TestSmsPojo();
		sms.setMessage("Skydky v ALDO. Kupuy 1 ked ta otrymuy dryguy v podarunok!");
		sms.setSender("ALDO");
		c.add(Calendar.HOUR, -2);
		sms.setReceived(c.getTime().getTime());
		mList.add(sms);

		sms = new TestSmsPojo();
		sms.setMessage("Big football tonight v taverne chili!");
		sms.setSender("+380991001010");
		sms.setRead(true);
		c.add(Calendar.HOUR, -30);
		sms.setReceived(c.getTime().getTime());
		mList.add(sms);

		sms = new TestSmsPojo();
		sms.setMessage("15:43 ZABLOKOVANO 17,99 UAH, SUPERMARKET PORTAL");
		sms.setSender("PUMB");
		c.add(Calendar.HOUR, -400);
		sms.setReceived(c.getTime().getTime());
		mList.add(sms);

		sms = new TestSmsPojo();
		sms.setMessage("Vash rakhunok na 8.05.2011 stanovyt 24 uah.");
		sms.setSender("KYIVSTAR");
		sms.setRead(true);
		c.add(Calendar.MINUTE, -20);
		sms.setReceived(c.getTime().getTime());
		mList.add(sms);

		sms = new TestSmsPojo();
		sms.setMessage("SCHET 12345 popolnenie na 123 uha 11.05.2011 12:45");
		sms.setSender("PUMB");
		sms.setRead(true);
		c.add(Calendar.MINUTE, -20);
		sms.setReceived(c.getTime().getTime());
		mList.add(sms);

		mUnreadCount = 6;
	}

	private SmsPojo get(long id) {
		if(id < 0) return null;
		if(id > mList.size()) return null;
		return mList.get((int) id);
	}

	@Override
	public ContentResolver getContentResolver() {
		return super.getContentResolver();
	}

	@Override
	public List<SmsPojo> getSmsList() {
		return mList;
	}

	@Override
	public SmsPojo getSms(long id) {
		return get(id);
	}

	@Override
	public boolean delete(SmsPojo sms) {
		if (sms != null){
			mList.remove(sms);
		}
		return true;
	}

	@Override
	public boolean update(SmsPojo sms) {
		return true;
	}

	@Override
	public SmsMessageSenderEntry insertOrSelectSender(String sender) {
		return super.insertOrSelectSender(sender);	//To change body of overridden methods use File | Settings | File Templates.
	}

	public SmsMessageSenderEntry getSenderById(int id) {
		return super.getSenderById(id);	//To change body of overridden methods use File | Settings | File Templates.
	}
}
