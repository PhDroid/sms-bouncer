package com.phdroid.smsb.storage;

import com.phdroid.smsb.SmsPojo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;

public class TestMessageProvider implements IMessageProvider{
    ArrayList<SmsPojo> mList;
    Hashtable<SmsPojo, SmsAction> mActions;
    int mUnreadCount = 0;

    public TestMessageProvider(){
        mActions = new Hashtable<SmsPojo, SmsAction>();
        mList = new ArrayList<SmsPojo>();
        Calendar c = Calendar.getInstance();

        SmsPojo sms = new SmsPojo();
        sms.setMessage("Hey you there! How you doin'?");
        sms.setSender("+380971122333");
        sms.setReceived(c.getTime().getTime());
        mList.add(sms);

        sms = new SmsPojo();
        sms.setMessage("Nova aktsia vid magazinu Target! Kupuy 2 kartopli ta otrymay 1 v podarunok!");
        sms.setSender("TARGET");
        c.add(Calendar.SECOND, -30);
        sms.setReceived(c.getTime().getTime());
        mList.add(sms);

        sms = new SmsPojo();
        sms.setMessage("This is my new number. B. Obama.");
        sms.setSender("+1570333444555");
        c.add(Calendar.MINUTE, -5);
        sms.setReceived(c.getTime().getTime());
        mList.add(sms);

        sms = new SmsPojo();
        sms.setMessage("How about having some beer tonight? Stranger.");
        sms.setSender("+380509998887");
        sms.setRead(true);
        c.add(Calendar.MINUTE, -30);
        sms.setReceived(c.getTime().getTime());
        mList.add(sms);

        sms = new SmsPojo();
        sms.setMessage("Vash rakhunok na 9.05.2011 stanovyt 27,35 uah.");
        sms.setSender("KYIVSTAR");
        c.add(Calendar.MINUTE, -20);
        sms.setReceived(c.getTime().getTime());
        mList.add(sms);

        sms = new SmsPojo();
        sms.setMessage("Skydky v ALDO. Kupuy 1 ked ta otrymuy dryguy v podarunok!");
        sms.setSender("ALDO");
        c.add(Calendar.HOUR, -2);
        sms.setReceived(c.getTime().getTime());
        mList.add(sms);

        sms = new SmsPojo();
        sms.setMessage("Big football tonight v taverne chili!");
        sms.setSender("+380991001010");
        sms.setRead(true);
        c.add(Calendar.HOUR, -30);
        sms.setReceived(c.getTime().getTime());
        mList.add(sms);

        sms = new SmsPojo();
        sms.setMessage("15:43 ZABLOKOVANO 17,99 UAH, SUPERMARKET PORTAL");
        sms.setSender("PUMB");
        c.add(Calendar.HOUR, -400);
        sms.setReceived(c.getTime().getTime());
        mList.add(sms);

        sms = new SmsPojo();
        sms.setMessage("Vash rakhunok na 8.05.2011 stanovyt 24 uah.");
        sms.setSender("KYIVSTAR");
        sms.setRead(true);
        c.add(Calendar.MINUTE, -20);
        sms.setReceived(c.getTime().getTime());
        mList.add(sms);

		sms = new SmsPojo();
        sms.setMessage("SCHET 12345 popolnenie na 123 uha 11.05.2011 12:45");
        sms.setSender("PUMB");
        sms.setRead(true);
        c.add(Calendar.MINUTE, -20);
        sms.setReceived(c.getTime().getTime());
        mList.add(sms);

        mUnreadCount = 6;
    }

    public ArrayList<SmsPojo> getMessages() {
         return mList;
    }

    public Hashtable<SmsPojo, SmsAction> getActionMessages(){
         return mActions;
    }

    public void delete(long id){
        mActions.clear();
        SmsPojo sms = get(id);
        mActions.put(sms, SmsAction.Deleted);
        mList.remove((int)id);
    }

    public void delete(long[] ids) {
        mActions.clear();
        for(long id = ids.length - 1; id >= 0; id --){
            SmsPojo sms = get(id);
            if(!sms.wasRead())
                mUnreadCount--;
            mActions.put(sms, SmsAction.Deleted);
            mList.remove((int)id);
        }
    }

    public void deleteAll() {
        for(SmsPojo sms : mList){
            mActions.put(sms, SmsAction.Deleted);
        }
        mList.clear();
    }

    public void notSpam(long id){
        mActions.clear();
        SmsPojo sms = get(id);
        mActions.put(sms, SmsAction.MarkedAsNotSpam);
        mList.remove((int)id);
    }

    public void notSpam(long[] ids) {
        mActions.clear();
        for(long id = ids.length - 1; id >= 0; id --){
            SmsPojo sms = get(id);
            if(!sms.wasRead())
                mUnreadCount--;
            mActions.put(sms, SmsAction.MarkedAsNotSpam);
            mList.remove((int)id);
        }
    }

    public SmsPojo getMessage(long id) {
        return get(id);
    }

    public void read(long id){
        if (!get(id).wasRead()){
            get(id).setRead(true);
            mUnreadCount --;
        }
    }

    public int getUnreadCount(){
        return mUnreadCount;
    }

    public void undo(){
        for(SmsPojo sms : mActions.keySet()){
            mList.add(0, sms);
        }
        mActions.clear();

        mUnreadCount = 0;
        for(SmsPojo sms : mList){
            if(!sms.wasRead()){
                mUnreadCount++;
            }
        }
    }

    public void performActions() {
        mActions.clear();
    }

	public int getIndex(SmsPojo message) {
		return mList.indexOf(message);
	}

	public SmsPojo getPreviousMessage(SmsPojo message) {
		int index = mList.indexOf(message);
		if (index <= 0) {
			return null;
		}
		return mList.get(--index);
	}

	public SmsPojo getNextMessage(SmsPojo message) {
		int index = mList.indexOf(message);
		if (index >= mList.size()-1) {
			return null;
		}
		return mList.get(++index);
	}

	private SmsPojo get(long id){
        return mList.get((int)id);
    }

	public boolean isFirstMessage(SmsPojo message) {
		return mList.indexOf(message) == 0;
	}

	public boolean isLastMessage(SmsPojo message) {
		return mList.indexOf(message) == mList.size()-1;
	}
}
