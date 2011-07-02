package com.phdroid.smsb.storage;

import android.content.ContentResolver;
import android.content.Context;
import com.phdroid.smsb.activity.base.ActivityBase;
import com.phdroid.smsb.storage.dao.Session;

public class MessageProviderHelper {
	private static IMessageProvider mProvider;
	public static IMessageProvider getMessageProvider(ContentResolver contentResolver){
		if(mProvider == null){
			mProvider = new SmsMessageController(new Session(contentResolver));
		}

		return mProvider;
	}

	public static void setMessageProvider(IMessageProvider provider){
		mProvider = provider;
	}

	public static void invalidCache(){
		mProvider.invalidateCache();
	}
}
