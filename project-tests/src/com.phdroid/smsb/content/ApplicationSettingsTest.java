package com.phdroid.smsb.content;

import com.phdroid.smsb.base.MockedContextTestBase;
import com.phdroid.smsb.storage.ApplicationSettings;
import com.phdroid.smsb.storage.DefaultApplicationSettings;
import com.phdroid.smsb.storage.DeleteAfter;
import junit.framework.Assert;

/**
 * Tests for ApplicationSettings class.
 */
public class ApplicationSettingsTest extends MockedContextTestBase {
	ApplicationSettings settings;

	public void test_ApplicationSettings_create_settings_file_with_default_values() {
		settings = new ApplicationSettings(getContext());
		Assert.assertEquals(DefaultApplicationSettings.DELETE_MESSAGES_AFTER_VALUE, settings.getDeleteAfter());
		Assert.assertEquals(DefaultApplicationSettings.DISPLAY_NOTIFICATION_VALUE, settings.showDisplayNotification());
	}

	public void test_ApplicationSettings_update_settings_file() {
		settings = new ApplicationSettings(getContext());
		settings.setDeleteAfter(DeleteAfter.ThirtyDays);
		settings.setDisplayNotification(false);
		Assert.assertEquals(DeleteAfter.ThirtyDays, settings.getDeleteAfter());
		Assert.assertEquals(false, settings.showDisplayNotification());
	}

	public void setUp() throws Exception {
		super.setUp();
	}

	public void tearDown() throws Exception {
		super.tearDown();
	}
}
