package com.phdroid.test.blackjack;

import android.app.Activity;
import android.app.Instrumentation;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import com.phdroid.test.blackjack.exceptions.ViewNotFoundException;
import junit.framework.Assert;

/**
 * A facade for testing operations.
 */
public class Solo {
	private Instrumentation instrumentation;
	private Sleeper sleeper;
	private ActivityUtils utils;

	public Solo(Instrumentation instrumentation, Activity testingActivity) {
		this.instrumentation = instrumentation;
		this.sleeper = new Sleeper();
		utils = new ActivityUtils(instrumentation, testingActivity, sleeper);
	}

	public Instrumentation getInstrumentation() {
		return instrumentation;
	}

	public Activity getCurrentActivity() {
		return utils.getCurrentActivity();
	}

	public void assertCurrentActivity(String message, Class activityClass) {
		sleeper.sleep();
		Assert.assertTrue(message, activityClass == getCurrentActivity().getClass());
	}

	public boolean waitForActivity(String name, int timeout)
	{
		return utils.waitForActivity(name, timeout);
	}

	public CheckBox getCheckBox(int id) throws ViewNotFoundException {
		View v = getCurrentActivity().findViewById(id);
		if (v == null) {
			throw new ViewNotFoundException();
		}
		return (CheckBox) v;
	}

	public boolean isCheckBoxChecked(int id) throws ViewNotFoundException {
		CheckBox c = getCheckBox(id);
		return c.isChecked();
	}

	public void clickOnCheckBox(int id) throws ViewNotFoundException {
		CheckBox c = getCheckBox(id);
		c.performClick();
	}

	public Spinner getSpinner(int id) throws ViewNotFoundException {
		View v = getCurrentActivity().findViewById(id);
		if (v == null) {
			throw new ViewNotFoundException();
		}
		return (Spinner) v;
	}

	public void clickOnSpinner(int id) throws ViewNotFoundException {
		final Spinner s = getSpinner(id);
		getCurrentActivity().runOnUiThread(
            new Runnable() {
                public void run() {
					s.requestFocus();
	                //s.performClick();
                }
            }
		);
	}

	public void selectSpinnerItem(int id, final int position) throws ViewNotFoundException {
		final Spinner s = getSpinner(id);
		getCurrentActivity().runOnUiThread(new Runnable() {
			public void run() {
				s.setSelection(position);
			}
		});
	}

	public void selectSpinnerItem(int id, Object item) throws ViewNotFoundException {
		final Spinner s = getSpinner(id);
		SpinnerAdapter adapter = s.getAdapter();
		for (int i = 0; i < adapter.getCount(); i++) {
			if (adapter.getItem(i).equals(item)) {
				final int index = i;
				getCurrentActivity().runOnUiThread(new Runnable() {
                    public void run() {
						s.setSelection(index);
                    }
                });
				break;
			}
		}
	}

	public Button getButton(int id) throws ViewNotFoundException {
		View v = getCurrentActivity().findViewById(id);
		if (v == null) {
			throw new ViewNotFoundException();
		}
		return (Button) v;
	}

	public void clickOnButton(int id) throws ViewNotFoundException {
		Button b = getButton(id);
		b.performClick();
	}

	@Override
	public void finalize() throws Throwable {
		utils.finalize();
		super.finalize();
	}
}
