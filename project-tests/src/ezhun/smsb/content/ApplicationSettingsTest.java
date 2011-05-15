package ezhun.smsb.content;

import ezhun.smsb.base.MockedContextTestBase;
import ezhun.smsb.storage.ApplicationSettings;
import ezhun.smsb.storage.DefaultApplicationSettings;
import ezhun.smsb.storage.DeleteAfter;
import junit.framework.Assert;

/**
 * Tests for ApplicationSettings class.
 */
public class ApplicationSettingsTest extends MockedContextTestBase {
	ApplicationSettings settings;

	private ApplicationSettings getSettings() {
		return settings;
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
		settings = new ApplicationSettings(getMockContext());
	}

	public void test_ApplicationSettings_create_settings_file_with_default_values() {
		Assert.assertEquals(DefaultApplicationSettings.DELETE_MESSAGES_AFTER_VALUE, getSettings().getDeleteAfter());
		Assert.assertEquals(DefaultApplicationSettings.DISPLAY_NOTIFICATION_VALUE, getSettings().getDisplayNotification());
	}

	public void test_ApplicationSettings_update_settings_file() {
		getSettings().setDeleteAfter(DeleteAfter.ThirtyDays);
		getSettings().setDisplayNotification(false);
		Assert.assertEquals(DeleteAfter.ThirtyDays, getSettings().getDeleteAfter());
		Assert.assertEquals(false, getSettings().getDisplayNotification());
	}
}
