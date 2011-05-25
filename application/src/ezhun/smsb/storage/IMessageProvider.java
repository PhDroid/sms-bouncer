package ezhun.smsb.storage;

import ezhun.smsb.SmsPojo;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

public interface IMessageProvider {
    ArrayList<SmsPojo> getMessages();

    Hashtable<SmsPojo, SmsAction> getActionMessages();

    SmsPojo getMessage(long id);

    void read(long id);

    void delete(long id);

    void delete(long[] ids);

    void deleteAll();

    void notSpam(long id);

    void notSpam(long[] ids);

    int getUnreadCount();

    void undo();

    void performActions();
}
