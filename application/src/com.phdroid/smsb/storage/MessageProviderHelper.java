package com.phdroid.smsb.storage;

import android.content.ContentResolver;
import android.content.Context;

public class MessageProviderHelper {
	private static IMessageProvider mProvider;
	public static IMessageProvider getMessageProvider(Context context, ContentResolver contentResolver){
		if(mProvider == null){
			mProvider = new SmsMessageProvider(context, contentResolver);
		}

		return mProvider;
	}

	public static void setMessageProvider(IMessageProvider provider){
		mProvider = provider;
	}
}
