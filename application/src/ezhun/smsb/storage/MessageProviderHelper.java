package ezhun.smsb.storage;

public class MessageProviderHelper {
    public static IMessageProvider getMessageProvider(){
        return new TestMessageProvider();
    }
}
