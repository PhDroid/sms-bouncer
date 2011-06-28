package com.phdroid.smsb.storage;

import android.content.ContentResolver;
import android.content.Context;
import com.phdroid.smsb.activity.base.ActivityBase;

public class MessageProviderHelper {
	private static IMessageProvider mProvider;
	public static IMessageProvider getMessageProvider(ActivityBase activity, Context context, ContentResolver contentResolver){
		if(mProvider == null){
			mProvider = new SmsMessageProvider(activity, context, contentResolver);
		}

		return mProvider;
	}

	public static void setMessageProvider(IMessageProvider provider){
		mProvider = provider;
	}
}
