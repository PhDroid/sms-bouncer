package com.phdroid.smsb.activity;

import android.content.Context;
import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import com.phdroid.android.test.blackjack.Solo;
import com.phdroid.android.test.blackjack.exceptions.ViewNotFoundException;
import com.phdroid.smsb.R;
import com.phdroid.smsb.base.util.ResourceInjectedContext;
import com.phdroid.smsb.storage.ApplicationSettings;
import com.phdroid.smsb.storage.DeleteAfter;
import junit.framework.Assert;

/**
 * A test for settings activity..
 */
public class SettingsActivityTest extends ActivityUnitTestCase<SettingsActivity> {
	private Solo solo;
	private ApplicationSettings settings;
	private Context context;

	public ApplicationSettings getApplicationSettings() {
		if (settings == null) {
			settings = new ApplicationSettings(getContext());
		}
		return settings;
	}

	public SettingsActivityTest() {
		super(SettingsActivity.class);
	}

	private Context getContext() {
		if (context == null) {
			context = new ResourceInjectedContext(getInstrumentation().getTargetContext());
		}

		return context;
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();

		solo = new Solo(getInstrumentation(), getActivity());
		if (this instanceof ActivityUnitTestCase) {
			setActivityContext(getContext());
			//setApplication(new MockContextApplication(getContext()));
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
		context = null;
		solo = null;
		settings = null;
		super.tearDown();
	}

	public void testPreConditions() {
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
		Spinner deleteAfterSpinner = solo.getSpinner(R.id.ddlClear);
		Assert.assertEquals(R.id.ddlClear, deleteAfterSpinner.getId());

		DeleteAfter item = (DeleteAfter) deleteAfterSpinner.getSelectedItem();

		Assert.assertEquals(getApplicationSettings().getDeleteAfter().index(), item.index());
	}

	public void testSettings_DeleteAfterSpinner_change() throws ViewNotFoundException {
		Spinner deleteAfterSpinner = solo.getSpinner(R.id.ddlClear);
		DeleteAfter initialSelectedItem = (DeleteAfter) deleteAfterSpinner.getSelectedItem();

		SpinnerAdapter adapter = deleteAfterSpinner.getAdapter();
		solo.clickOnSpinner(R.id.ddlClear);

		DeleteAfter shouldBeSelected = null;
		for (int i = 0; i < adapter.getCount(); i++) {
			DeleteAfter adapterItem = (DeleteAfter) adapter.getItem(i);
			if (initialSelectedItem.index() != adapterItem.index()) {
				shouldBeSelected = adapterItem;
				solo.selectSpinnerItem(R.id.ddlClear, adapterItem);
				break;
			}
		}

		Assert.assertNotNull(shouldBeSelected);
		Assert.assertTrue(shouldBeSelected.index() != initialSelectedItem.index());

		DeleteAfter newSelectedItem = (DeleteAfter) deleteAfterSpinner.getSelectedItem();
		Assert.assertTrue(newSelectedItem.index() == shouldBeSelected.index());
		// looks like Spinner doesn't send proper events in ActivityUnitTestCase
		// Assert.assertTrue(newSelectedItem.index() == getApplicationSettings().getDeleteAfter().index());
	}

	public void testSettings_EditWhitelistButton_click() throws ViewNotFoundException {
		solo.assertCurrentActivity("Expected activity: Settings", SettingsActivity.class);
		solo.clickOnButton(R.id.btnEditWhitelist);
		solo.waitForActivity(EditWhitelistActivity.class.getSimpleName(), 1000);

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
