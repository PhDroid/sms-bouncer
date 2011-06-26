package com.phdroid.smsb.storage;

import android.content.ContentResolver;

public class MessageProviderHelper {
	private static IMessageProvider mProvider;
	public static IMessageProvider getMessageProvider(ContentResolver contentResolver){
		if(mProvider == null){
			mProvider = new SmsMessageProvider(contentResolver);
		}

		return mProvider;
	}

	public static void setMessageProvider(IMessageProvider provider){
		mProvider = provider;
	}
}
