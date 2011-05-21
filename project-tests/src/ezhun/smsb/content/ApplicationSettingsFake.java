package ezhun.smsb.content;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import ezhun.smsb.storage.ApplicationSettings;

import javax.crypto.Cipher;

/**
 * Please, write short description of what this file is for.
 */
public class ApplicationSettingsFake extends ApplicationSettings {
	static final String TEST_FILE_NAME = "test.ezhun.smsb.settings";
	private SharedPreferences p;

	public ApplicationSettingsFake(Context context) {
		super(context);
		this.p = context.getSharedPreferences(TEST_FILE_NAME, Context.MODE_PRIVATE);
	}

	@Override
	protected SharedPreferences getPreferencesProvider() {
		return p;
	}
}
