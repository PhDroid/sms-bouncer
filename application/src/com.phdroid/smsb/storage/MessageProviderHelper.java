package com.phdroid.smsb.storage;

public class MessageProviderHelper {
	private static IMessageProvider mProvider;
	public static IMessageProvider getMessageProvider(){
		if(mProvider == null){
			mProvider = new TestMessageProvider();
		}

		return mProvider;
	}

	public static void setMessageProvider(IMessageProvider provider){
		mProvider = provider;
	}
}
