package ezhun.smsb.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;

/**
 * Injects Activity methods with events.
 */
public class EventInjectedActivity extends Activity {
	private OnCreateCall onCreateEventCall = null;
	private OnTouchCall onTouchEventCall = null;

	public abstract class OnCreateCall {
		public abstract void OnBeforeCreate(Bundle savedInstanceState);
		public abstract void OnAfterCreate(Bundle savedInstanceState);
	}

	public abstract static class OnTouchCall {
		public abstract void OnBeforeTouch(MotionEvent event);
		public abstract boolean OnAfterTouch(MotionEvent event, boolean result);
	}

	public OnCreateCall getOnCreateEventCall() {
		return onCreateEventCall;
	}

	public void setOnCreateEventCall(OnCreateCall onCreateEventCall) {
		this.onCreateEventCall = onCreateEventCall;
	}

	public OnTouchCall getOnTouchEventCall() {
		return onTouchEventCall;
	}

	public void setOnTouchEventCall(OnTouchCall onTouchEventCall) {
		this.onTouchEventCall = onTouchEventCall;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (onCreateEventCall != null) {
			onCreateEventCall.OnBeforeCreate(savedInstanceState);
		}
		super.onCreate(savedInstanceState);
		if (onCreateEventCall != null) {
			onCreateEventCall.OnAfterCreate(savedInstanceState);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (onTouchEventCall != null) {
			onTouchEventCall.OnBeforeTouch(event);
		}
		boolean res = super.onTouchEvent(event);
		if (onTouchEventCall != null) {
			res = onTouchEventCall.OnAfterTouch(event, res);
		}
		return res;
	}
}
