package ezhun.smsb.storage;

import ezhun.smsb.SmsPojo;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

public interface IMessageProvider {
    ArrayList<SmsPojo> getMessages();

    Hashtable<SmsPojo, SmsAction> getActionMessages();

    SmsPojo getMessage(int id);

    void read(int id);

    void delete(int id);

    void notSpam(int id);

    int getUnreadCount();

    void undo();
}
