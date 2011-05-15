package ezhun.smsb.content;

import ezhun.smsb.base.MockedContextTestBase;
import ezhun.smsb.storage.ApplicationSettings;
import ezhun.smsb.storage.DefaultApplicationSettings;
import junit.framework.Assert;

/**
 * Tests for ApplicationSettings class.
 */
public class ApplicationSettingsTest extends MockedContextTestBase {
	public void test_ApplicationSettings_create_settings_file_with_default_values() {
		ApplicationSettings s = new ApplicationSettings(getMockContext());
		Assert.assertEquals(DefaultApplicationSettings.DELETE_MESSAGES_AFTER_VALUE, s.getDeleteAfter());
		Assert.assertEquals(DefaultApplicationSettings.DISPLAY_NOTIFICATION_VALUE, s.getDisplayNotification());
	}
}
