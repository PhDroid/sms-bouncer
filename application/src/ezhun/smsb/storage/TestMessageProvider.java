package ezhun.smsb.storage;

import android.telephony.SmsManager;
import ezhun.smsb.SmsPojo;

import java.util.*;

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

        mUnreadCount = 6;
    }

    public ArrayList<SmsPojo> getMessages() {
         return mList;
    }

    public Hashtable<SmsPojo, SmsAction> getActionMessages(){
         return mActions;
    }

    public void delete(int id){
        mActions.clear();
        SmsPojo sms = mList.get(id);
        mActions.put(sms, SmsAction.Deleted);
        mList.remove(id);
    }

    public void notSpam(int id){
        mActions.clear();
        SmsPojo sms = mList.get(id);
        mActions.put(sms, SmsAction.MarkedAsNotSpam);
        mList.remove(id);
    }

    public SmsPojo getMessage(int id) {
        return mList.get(id);
    }

    public void read(int id){
        if (!mList.get(id).wasRead()){
            mList.get(id).setRead(true);
             mUnreadCount --;
        }
    }

    public int getUnreadCount(){
        return mUnreadCount;
    }
}
