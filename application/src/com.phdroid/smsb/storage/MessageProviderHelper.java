package com.phdroid.smsb.storage;

import android.content.ContentResolver;
import com.phdroid.smsb.storage.dao.Session;

public class MessageProviderHelper {
	private static IMessageProvider mMessageProvider;
	private static ISenderProvider mSenderProvider;

	public static IMessageProvider getMessageProvider(ApplicationSettings settings, ContentResolver contentResolver) {
		InitProviders(settings, contentResolver);
		return mMessageProvider;
	}

	public static ISenderProvider getSenderProvider(ApplicationSettings settings, ContentResolver contentResolver) {
		InitProviders(settings, contentResolver);
		return mSenderProvider;
	}

	private static void InitProviders(ApplicationSettings settings, ContentResolver contentResolver) {
		if (mMessageProvider == null || mSenderProvider == null) {
			Session session = new Session(settings, contentResolver);
			if(mMessageProvider == null){
				mMessageProvider = new SmsMessageController(session);
			}
			if(mSenderProvider == null){
				mSenderProvider = new SenderProvider(session);
			}
		}
	}

	public static void setMessageProvider(IMessageProvider provider) {
		mMessageProvider = provider;
	}

	public static void setSenderProvider(ISenderProvider provider) {
		mSenderProvider = provider;
	}

	public static void invalidCache() {
		mMessageProvider.invalidateCache();
	}
}
