package com.phdroid.blackjack.ui;

import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Detects swipe action.
 */
public class HorizontalSwipeListener extends GestureDetector.SimpleOnGestureListener {
	private static final int SWIPE_MIN_DISTANCE = 100;
	private static final int SWIPE_THRESHOLD_VELOCITY = 75;

	public abstract static class Swipe {
		public abstract void doSwipe();
	}

	private GestureDetector gestureDetector;
	private Swipe swipeLeft;
	private Swipe swipeRight;

	public static HorizontalSwipeListener register(EventInjectedActivity activity){
		return new HorizontalSwipeListener(activity);
	}

	protected HorizontalSwipeListener(EventInjectedActivity activity) {
		EventInjectedActivity.OnTouchCall onTouchCall = new EventInjectedActivity.OnTouchCall() {
			@Override
			public void OnBeforeTouch(MotionEvent event) {
			}

			@Override
			public boolean OnAfterTouch(MotionEvent event, boolean result) {
				return !result && gestureDetector.onTouchEvent(event) || result;
			}
		};
		activity.setOnTouchEventCall(onTouchCall);

		gestureDetector = new GestureDetector(this);
	}

	public Swipe getSwipeLeft() {
		return swipeLeft;
	}

	public void setSwipeLeft(Swipe swipeLeft) {
		this.swipeLeft = swipeLeft;
	}

	public Swipe getSwipeRight() {
		return swipeRight;
	}

	public void setSwipeRight(Swipe swipeRight) {
		this.swipeRight = swipeRight;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
			if (swipeRight != null) {
				swipeRight.doSwipe();
				return true;
			}
		} else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
			if (swipeLeft != null) {
				swipeLeft.doSwipe();
				return true;
			}
		}
		return false;
	}

}