package com.phdroid.smsb.widget;

import android.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class CheckableRelativeLayout extends RelativeLayout implements Checkable {
	private boolean mChecked;
	private ArrayList<Checkable> mCheckableViews;

	public CheckableRelativeLayout(Context context) {
		super(context);
		mCheckableViews = new ArrayList<Checkable>();
	}

	public CheckableRelativeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		mCheckableViews = new ArrayList<Checkable>();
	}

	public CheckableRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mCheckableViews = new ArrayList<Checkable>();
	}

	public void setChecked(boolean b) {
		mChecked = b;
		for (Checkable c : mCheckableViews) {
			c.setChecked(b);
		}
	}

	public boolean isChecked() {
		return mChecked;
	}

	public void toggle() {
		mChecked = !mChecked;
		for (Checkable c : mCheckableViews) {
			c.toggle();
		}
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		final int childCount = this.getChildCount();
		for (int i = 0; i < childCount; ++i) {
			findCheckableChildren(this.getChildAt(i));
		}
	}

	@Override
	protected int[] onCreateDrawableState(int extraSpace) {
		final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
		if (isChecked()) {
			mergeDrawableStates(drawableState, CheckedStateSet);
		}
		return drawableState;

	}

	private static final int[] CheckedStateSet = {
			R.attr.state_checked
	};

	private void findCheckableChildren(View v) {
		if (v instanceof Checkable) {
			mCheckableViews.add((Checkable) v);
		}

		if (v instanceof ViewGroup) {
			final ViewGroup vg = (ViewGroup) v;
			final int childCount = vg.getChildCount();
			for (int i = 0; i < childCount; ++i) {
				findCheckableChildren(vg.getChildAt(i));
			}
		}
	}
}
