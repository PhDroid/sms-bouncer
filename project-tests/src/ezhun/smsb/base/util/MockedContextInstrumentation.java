package ezhun.smsb.base.util;

import android.app.Activity;
import android.app.Application;
import android.app.Instrumentation;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.view.KeyEvent;
import android.view.MotionEvent;

/**
 * Used for instrumentation injections.
 */
public class MockedContextInstrumentation extends Instrumentation {
	private Context context;
	private Instrumentation innerInstrumentation;

	public MockedContextInstrumentation(Instrumentation innerInstrumentation) {
		this.innerInstrumentation = innerInstrumentation;
	}

	public Context getProperContext() {
		return innerInstrumentation.getTargetContext();
	}

	@Override
	public Context getContext() {
		if (context == null) {
			context = new ResourceInjectedContext(getProperContext());
		}
		return context;
	}

	@Override
	public void onCreate(Bundle arguments) {
		innerInstrumentation.onCreate(arguments);
	}

	@Override
	public void start() {
		innerInstrumentation.start();
	}

	@Override
	public void onStart() {
		innerInstrumentation.onStart();
	}

	@Override
	public boolean onException(Object obj, Throwable e) {
		return innerInstrumentation.onException(obj, e);
	}

	@Override
	public void sendStatus(int resultCode, Bundle results) {
		innerInstrumentation.sendStatus(resultCode, results);
	}

	@Override
	public void finish(int resultCode, Bundle results) {
		innerInstrumentation.finish(resultCode, results);
	}

	@Override
	public void setAutomaticPerformanceSnapshots() {
		innerInstrumentation.setAutomaticPerformanceSnapshots();
	}

	@Override
	public void startPerformanceSnapshot() {
		innerInstrumentation.startPerformanceSnapshot();
	}

	@Override
	public void endPerformanceSnapshot() {
		innerInstrumentation.endPerformanceSnapshot();
	}

	@Override
	public void onDestroy() {
		innerInstrumentation.onDestroy();
	}

	@Override
	public ComponentName getComponentName() {
		return innerInstrumentation.getComponentName();
	}

	@Override
	public Context getTargetContext() {
		return innerInstrumentation.getTargetContext();
	}

	@Override
	public boolean isProfiling() {
		return innerInstrumentation.isProfiling();
	}

	@Override
	public void startProfiling() {
		innerInstrumentation.startProfiling();
	}

	@Override
	public void stopProfiling() {
		innerInstrumentation.stopProfiling();
	}

	@Override
	public void setInTouchMode(boolean inTouch) {
		innerInstrumentation.setInTouchMode(inTouch);
	}

	@Override
	public void waitForIdle(Runnable recipient) {
		innerInstrumentation.waitForIdle(recipient);
	}

	@Override
	public void waitForIdleSync() {
		innerInstrumentation.waitForIdleSync();
	}

	@Override
	public void runOnMainSync(Runnable runner) {
		innerInstrumentation.runOnMainSync(runner);
	}

	@Override
	public Activity startActivitySync(Intent intent) {
		return innerInstrumentation.startActivitySync(intent);
	}

	@Override
	public void addMonitor(ActivityMonitor monitor) {
		innerInstrumentation.addMonitor(monitor);
	}

	@Override
	public ActivityMonitor addMonitor(IntentFilter filter, ActivityResult result, boolean block) {
		return innerInstrumentation.addMonitor(filter, result, block);
	}

	@Override
	public ActivityMonitor addMonitor(String cls, ActivityResult result, boolean block) {
		return innerInstrumentation.addMonitor(cls, result, block);
	}

	@Override
	public boolean checkMonitorHit(ActivityMonitor monitor, int minHits) {
		return innerInstrumentation.checkMonitorHit(monitor, minHits);
	}

	@Override
	public Activity waitForMonitor(ActivityMonitor monitor) {
		return innerInstrumentation.waitForMonitor(monitor);
	}

	@Override
	public Activity waitForMonitorWithTimeout(ActivityMonitor monitor, long timeOut) {
		return innerInstrumentation.waitForMonitorWithTimeout(monitor, timeOut);
	}

	@Override
	public void removeMonitor(ActivityMonitor monitor) {
		innerInstrumentation.removeMonitor(monitor);
	}

	@Override
	public boolean invokeMenuActionSync(Activity targetActivity, int id, int flag) {
		return innerInstrumentation.invokeMenuActionSync(targetActivity, id, flag);
	}

	@Override
	public boolean invokeContextMenuAction(Activity targetActivity, int id, int flag) {
		return innerInstrumentation.invokeContextMenuAction(targetActivity, id, flag);
	}

	@Override
	public void sendStringSync(String text) {
		innerInstrumentation.sendStringSync(text);
	}

	@Override
	public void sendKeySync(KeyEvent event) {
		innerInstrumentation.sendKeySync(event);
	}

	@Override
	public void sendKeyDownUpSync(int key) {
		innerInstrumentation.sendKeyDownUpSync(key);
	}

	@Override
	public void sendCharacterSync(int keyCode) {
		innerInstrumentation.sendCharacterSync(keyCode);
	}

	@Override
	public void sendPointerSync(MotionEvent event) {
		innerInstrumentation.sendPointerSync(event);
	}

	@Override
	public void sendTrackballEventSync(MotionEvent event) {
		innerInstrumentation.sendTrackballEventSync(event);
	}

	@Override
	public Application newApplication(ClassLoader cl, String className, Context context) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		return innerInstrumentation.newApplication(cl, className, context);
	}

	@Override
	public void callApplicationOnCreate(Application app) {
		innerInstrumentation.callApplicationOnCreate(app);
	}

	@Override
	public Activity newActivity(Class<?> clazz, Context context, IBinder token, Application application, Intent intent, ActivityInfo info, CharSequence title, Activity parent, String id, Object lastNonConfigurationInstance) throws InstantiationException, IllegalAccessException {
		return innerInstrumentation.newActivity(clazz, context, token, application, intent, info, title, parent, id, lastNonConfigurationInstance);
	}

	@Override
	public Activity newActivity(ClassLoader cl, String className, Intent intent) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		return innerInstrumentation.newActivity(cl, className, intent);
	}

	@Override
	public void callActivityOnCreate(Activity activity, Bundle icicle) {
		innerInstrumentation.callActivityOnCreate(activity, icicle);
	}

	@Override
	public void callActivityOnDestroy(Activity activity) {
		innerInstrumentation.callActivityOnDestroy(activity);
	}

	@Override
	public void callActivityOnRestoreInstanceState(Activity activity, Bundle savedInstanceState) {
		innerInstrumentation.callActivityOnRestoreInstanceState(activity, savedInstanceState);
	}

	@Override
	public void callActivityOnPostCreate(Activity activity, Bundle icicle) {
		innerInstrumentation.callActivityOnPostCreate(activity, icicle);
	}

	@Override
	public void callActivityOnNewIntent(Activity activity, Intent intent) {
		innerInstrumentation.callActivityOnNewIntent(activity, intent);
	}

	@Override
	public void callActivityOnStart(Activity activity) {
		innerInstrumentation.callActivityOnStart(activity);
	}

	@Override
	public void callActivityOnRestart(Activity activity) {
		innerInstrumentation.callActivityOnRestart(activity);
	}

	@Override
	public void callActivityOnResume(Activity activity) {
		innerInstrumentation.callActivityOnResume(activity);
	}

	@Override
	public void callActivityOnStop(Activity activity) {
		innerInstrumentation.callActivityOnStop(activity);
	}

	@Override
	public void callActivityOnSaveInstanceState(Activity activity, Bundle outState) {
		innerInstrumentation.callActivityOnSaveInstanceState(activity, outState);
	}

	@Override
	public void callActivityOnPause(Activity activity) {
		innerInstrumentation.callActivityOnPause(activity);
	}

	@Override
	public void callActivityOnUserLeaving(Activity activity) {
		innerInstrumentation.callActivityOnUserLeaving(activity);
	}

	@Override
	public void startAllocCounting() {
		innerInstrumentation.startAllocCounting();
	}

	@Override
	public void stopAllocCounting() {
		innerInstrumentation.stopAllocCounting();
	}

	@Override
	public Bundle getAllocCounts() {
		return innerInstrumentation.getAllocCounts();
	}

	@Override
	public Bundle getBinderCounts() {
		return innerInstrumentation.getBinderCounts();
	}
}
