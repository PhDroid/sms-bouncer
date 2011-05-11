package ezhun.smsb.storage;

import ezhun.smsb.SmsPojo;

import java.util.ArrayList;

public interface IMessageProvider {
    ArrayList<SmsPojo> getMessages();

    SmsPojo getMessage(int id);
}
