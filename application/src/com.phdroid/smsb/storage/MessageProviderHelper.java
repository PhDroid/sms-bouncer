package com.phdroid.smsb.storage;

import android.content.ContentResolver;
import com.phdroid.smsb.storage.dao.Session;

public class MessageProviderHelper {
	private static IMessageProvider mProvider;

	public static IMessageProvider getMessageProvider(ApplicationSettings settings, ContentResolver contentResolver) {
		if (mProvider == null) {
			Session session = new Session(settings, contentResolver);
			mProvider = new SmsMessageController(session);
		}

		return mProvider;
	}

	public static void setMessageProvider(IMessageProvider provider) {
		mProvider = provider;
	}

	public static void invalidCache() {
		mProvider.invalidateCache();
	}
}
