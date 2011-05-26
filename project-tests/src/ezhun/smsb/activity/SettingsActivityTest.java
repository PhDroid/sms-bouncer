package ezhun.smsb.activity;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import ezhun.android.test.blackjack.Solo;
import ezhun.android.test.blackjack.exceptions.ViewNotFoundException;
import ezhun.smsb.R;
import ezhun.smsb.base.util.MockedContextInstrumentation;
import ezhun.smsb.storage.ApplicationSettings;
import ezhun.smsb.storage.DeleteAfter;
import junit.framework.Assert;

/**
 * A test for settings activity..
 */
public class SettingsActivityTest extends ActivityUnitTestCase<SettingsActivity> {
	private Solo solo;
	private ApplicationSettings settings;

	public ApplicationSettings getApplicationSettings() {
		if (settings == null) {
			settings = new ApplicationSettings(getContext());
		}
		return settings;
	}

	public SettingsActivityTest() {
		super(SettingsActivity.class);
	}

	private Instrumentation instrumentation;

	private Context getContext() {
		return getInstrumentation().getTargetContext();
	}

	@Override
	public void injectInstrumentation(Instrumentation instrumentation) {
		this.instrumentation = new MockedContextInstrumentation(instrumentation);
	}

	@Override
	public Instrumentation getInstrumentation() {
		return instrumentation;
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();

		solo = new Solo(getInstrumentation(), getActivity());
		if (this instanceof ActivityUnitTestCase) {
			Intent start = new Intent(getContext(), SettingsActivity.class);
			startActivity(start, null, null);
		}
	}

	@Override
	public void tearDown() throws Exception {
		if (!(this instanceof ActivityUnitTestCase)) {
			try {
				solo.finalize();
			} catch (Throwable e) {

				e.printStackTrace();
			}
			getActivity().finish();

		}
		super.tearDown();
	}

	public void testSettings_currentActivity() {
		solo.assertCurrentActivity("Expected activity: Settings", SettingsActivity.class);
	}

	public void testSettings_DisplayCheckbox_initial_state() throws ViewNotFoundException {
		boolean isChecked = solo.isCheckBoxChecked(R.id.cbNotification);
		boolean shouldBeChecked = getApplicationSettings().showDisplayNotification();
		Assert.assertEquals(shouldBeChecked, isChecked);
	}

	public void testSettings_DisplayCheckbox_change() throws ViewNotFoundException {
		boolean isCheckedInitial = solo.isCheckBoxChecked(R.id.cbNotification);
		solo.clickOnCheckBox(R.id.cbNotification);
		boolean isCheckedChanged = solo.isCheckBoxChecked(R.id.cbNotification);
		Assert.assertTrue(isCheckedInitial != isCheckedChanged);
		Assert.assertEquals(isCheckedChanged, getApplicationSettings().showDisplayNotification());
	}

	public void testSettings_DeleteAfterSpinner_initial_state() throws ViewNotFoundException {
		Spinner deleteAfterSpinner = solo.getSpinner(R.id.ddlDisplayNotification);
		Assert.assertEquals(R.id.ddlDisplayNotification, deleteAfterSpinner.getId());

		DeleteAfter item = (DeleteAfter) deleteAfterSpinner.getSelectedItem();

		Assert.assertEquals(getApplicationSettings().getDeleteAfter().index(), item.index());
	}

	public void testSettings_DeleteAfterSpinner_change() throws ViewNotFoundException {
		Spinner deleteAfterSpinner = solo.getSpinner(R.id.ddlDisplayNotification);
		DeleteAfter item = (DeleteAfter) deleteAfterSpinner.getSelectedItem();

		SpinnerAdapter adapter = deleteAfterSpinner.getAdapter();
		solo.clickOnSpinner(R.id.ddlDisplayNotification);

		DeleteAfter shouldBeSelected = null;
		for (int i = 0; i < adapter.getCount(); i++) {
			DeleteAfter adapterItem = (DeleteAfter) adapter.getItem(i);
			if (item.index() != adapterItem.index()) {
				shouldBeSelected = adapterItem;
				solo.selectSpinnerItem(R.id.ddlDisplayNotification, item);
				break;
			}
		}

		Assert.assertNotNull(shouldBeSelected);
		Assert.assertTrue(shouldBeSelected.index() != item.index());

		DeleteAfter reallySelected = (DeleteAfter) deleteAfterSpinner.getSelectedItem();
		Assert.assertTrue(reallySelected.index() == shouldBeSelected.index());
		Assert.assertEquals(reallySelected.index(), getApplicationSettings().getDeleteAfter().index());
	}

	public void testSettings_EditWhitelistButton_click() throws ViewNotFoundException {
		solo.assertCurrentActivity("Expected activity: Settings", SettingsActivity.class);
		solo.clickOnButton(R.id.btnEditWhitelist);
		solo.waitForActivity(EditWhitelistActivity.class.getSimpleName(), 1000);
		String expected = EditWhitelistActivity.class.getCanonicalName();
		String actual = getStartedActivityIntent().getComponent().getClassName();

		if ((this instanceof ActivityUnitTestCase)) {
			Assert.assertEquals(
					EditWhitelistActivity.class.getCanonicalName(),
					getStartedActivityIntent().getComponent().getClassName()
			);
		} else {
			solo.assertCurrentActivity("Expected activity: EditWhiteList", EditWhitelistActivity.class);
		}
	}
}
