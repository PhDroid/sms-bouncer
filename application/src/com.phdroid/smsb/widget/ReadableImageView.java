package com.phdroid.smsb.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.phdroid.smsb.R;

public class ReadableImageView extends ImageView{
	private static final int[] STATE_READ = {R.attr.state_read};
	private boolean mIsRead = false;

	public ReadableImageView(Context context) {
		super(context);
	}

	public ReadableImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ReadableImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void setRead(boolean isRead) {mIsRead = isRead;}

	@Override
	public int[] onCreateDrawableState(int extraSpace) {
		final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
		if (mIsRead) {
			mergeDrawableStates(drawableState, STATE_READ);
		}
		return drawableState;
	}
}
