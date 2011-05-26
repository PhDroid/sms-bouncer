package ezhun.smsb.base.util;

import android.content.*;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * MockContext descendant with resource capabilities.
 */
public class ResourceInjectedContext extends Context {
	private Map<String, SharedPreferences> inMemoryPreferences;
	private Context properContext;

	private Context getProperContext() {
		return properContext;
	}

	public ResourceInjectedContext(Context properContext) {
		inMemoryPreferences = new HashMap<String, SharedPreferences>();
		this.properContext = properContext;
	}

	@Override
	public Resources getResources() {
		return getProperContext().getResources();
	}

	@Override
	public synchronized SharedPreferences getSharedPreferences(String name, int mode) {
		if (!inMemoryPreferences.containsKey(name)) {
			inMemoryPreferences.put(name, new InMemoryPreferences());
		}
		return inMemoryPreferences.get(name);
	}

	@Override
	public AssetManager getAssets() {
		return getProperContext().getAssets();
	}

	@Override
	public PackageManager getPackageManager() {
		return getProperContext().getPackageManager();
	}

	@Override
	public ContentResolver getContentResolver() {
		return getProperContext().getContentResolver();
	}

	@Override
	public Looper getMainLooper() {
		return getProperContext().getMainLooper();
	}

	@Override
	public Context getApplicationContext() {
		return getProperContext().getApplicationContext();
	}

	@Override
	public void setTheme(int resid) {
		getProperContext().setTheme(resid);
	}

	@Override
	public Resources.Theme getTheme() {
		return getProperContext().getTheme();
	}

	@Override
	public ClassLoader getClassLoader() {
		return getProperContext().getClassLoader();
	}

	@Override
	public String getPackageName() {
		return getProperContext().getPackageName();
	}

	@Override
	public ApplicationInfo getApplicationInfo() {
		return getProperContext().getApplicationInfo();
	}

	@Override
	public String getPackageResourcePath() {
		return getProperContext().getPackageResourcePath();
	}

	@Override
	public String getPackageCodePath() {
		return getProperContext().getPackageCodePath();
	}

	@Override
	public FileInputStream openFileInput(String name) throws FileNotFoundException {
		return getProperContext().openFileInput(name);
	}

	@Override
	public FileOutputStream openFileOutput(String name, int mode) throws FileNotFoundException {
		return getProperContext().openFileOutput(name, mode);
	}

	@Override
	public boolean deleteFile(String name) {
		return getProperContext().deleteFile(name);
	}

	@Override
	public File getFileStreamPath(String name) {
		return getProperContext().getFileStreamPath(name);
	}

	@Override
	public String[] fileList() {
		return getProperContext().fileList();
	}

	@Override
	public File getFilesDir() {
		return getProperContext().getFilesDir();
	}

	@Override
	public File getExternalFilesDir(String type) {
		return getProperContext().getExternalFilesDir(type);
	}

	@Override
	public File getCacheDir() {
		return getProperContext().getCacheDir();
	}

	@Override
	public File getExternalCacheDir() {
		return getProperContext().getExternalCacheDir();
	}

	@Override
	public File getDir(String name, int mode) {
		return getProperContext().getDir(name, mode);
	}

	@Override
	public SQLiteDatabase openOrCreateDatabase(String file, int mode, SQLiteDatabase.CursorFactory factory) {
		return getProperContext().openOrCreateDatabase(file, mode, factory);
	}

	@Override
	public File getDatabasePath(String name) {
		return getProperContext().getDatabasePath(name);
	}

	@Override
	public String[] databaseList() {
		return getProperContext().databaseList();
	}

	@Override
	public boolean deleteDatabase(String name) {
		return getProperContext().deleteDatabase(name);
	}

	@Override
	public Drawable getWallpaper() {
		return getProperContext().getWallpaper();
	}

	@Override
	public Drawable peekWallpaper() {
		return getProperContext().peekWallpaper();
	}

	@Override
	public int getWallpaperDesiredMinimumWidth() {
		return getProperContext().getWallpaperDesiredMinimumWidth();
	}

	@Override
	public int getWallpaperDesiredMinimumHeight() {
		return getProperContext().getWallpaperDesiredMinimumHeight();
	}

	@Override
	public void setWallpaper(Bitmap bitmap) throws IOException {
		getProperContext().setWallpaper(bitmap);
	}

	@Override
	public void setWallpaper(InputStream data) throws IOException {
		getProperContext().setWallpaper(data);
	}

	@Override
	public void clearWallpaper() throws IOException {
		getProperContext().clearWallpaper();
	}

	@Override
	public void startActivity(Intent intent) {
		getProperContext().startActivity(intent);
	}

	@Override
	public void startIntentSender(IntentSender intent, Intent fillInIntent, int flagsMask, int flagsValues, int extraFlags) throws IntentSender.SendIntentException {
		getProperContext().startIntentSender(intent, fillInIntent, flagsMask, flagsValues, extraFlags);
	}

	@Override
	public void sendBroadcast(Intent intent) {
		getProperContext().sendBroadcast(intent);
	}

	@Override
	public void sendBroadcast(Intent intent, String receiverPermission) {
		getProperContext().sendBroadcast(intent, receiverPermission);
	}

	@Override
	public void sendOrderedBroadcast(Intent intent, String receiverPermission) {
		getProperContext().sendOrderedBroadcast(intent, receiverPermission);
	}

	@Override
	public void sendOrderedBroadcast(Intent intent, String receiverPermission, BroadcastReceiver resultReceiver, Handler scheduler, int initialCode, String initialData, Bundle initialExtras) {
		getProperContext().sendOrderedBroadcast(intent, receiverPermission, resultReceiver, scheduler, initialCode, initialData, initialExtras);
	}

	@Override
	public void sendStickyBroadcast(Intent intent) {
		getProperContext().sendStickyBroadcast(intent);
	}

	@Override
	public void sendStickyOrderedBroadcast(Intent intent, BroadcastReceiver resultReceiver, Handler scheduler, int initialCode, String initialData, Bundle initialExtras) {
		getProperContext().sendStickyOrderedBroadcast(intent, resultReceiver, scheduler, initialCode, initialData, initialExtras);
	}

	@Override
	public void removeStickyBroadcast(Intent intent) {
		getProperContext().removeStickyBroadcast(intent);
	}

	@Override
	public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter) {
		return getProperContext().registerReceiver(receiver, filter);
	}

	@Override
	public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter, String broadcastPermission, Handler scheduler) {
		return getProperContext().registerReceiver(receiver, filter, broadcastPermission, scheduler);
	}

	@Override
	public void unregisterReceiver(BroadcastReceiver receiver) {
		getProperContext().unregisterReceiver(receiver);
	}

	@Override
	public ComponentName startService(Intent service) {
		return getProperContext().startService(service);
	}

	@Override
	public boolean stopService(Intent service) {
		return getProperContext().stopService(service);
	}

	@Override
	public boolean bindService(Intent service, ServiceConnection conn, int flags) {
		return getProperContext().bindService(service, conn, flags);
	}

	@Override
	public void unbindService(ServiceConnection conn) {
		getProperContext().unbindService(conn);
	}

	@Override
	public boolean startInstrumentation(ComponentName className, String profileFile, Bundle arguments) {
		return getProperContext().startInstrumentation(className, profileFile, arguments);
	}

	@Override
	public Object getSystemService(String name) {
		return getProperContext().getSystemService(name);
	}

	@Override
	public int checkPermission(String permission, int pid, int uid) {
		return getProperContext().checkPermission(permission, pid, uid);
	}

	@Override
	public int checkCallingPermission(String permission) {
		return getProperContext().checkCallingPermission(permission);
	}

	@Override
	public int checkCallingOrSelfPermission(String permission) {
		return getProperContext().checkCallingOrSelfPermission(permission);
	}

	@Override
	public void enforcePermission(String permission, int pid, int uid, String message) {
		getProperContext().enforcePermission(permission, pid, uid, message);
	}

	@Override
	public void enforceCallingPermission(String permission, String message) {
		getProperContext().enforceCallingPermission(permission, message);
	}

	@Override
	public void enforceCallingOrSelfPermission(String permission, String message) {
		getProperContext().enforceCallingOrSelfPermission(permission, message);
	}

	@Override
	public void grantUriPermission(String toPackage, Uri uri, int modeFlags) {
		getProperContext().grantUriPermission(toPackage, uri, modeFlags);
	}

	@Override
	public void revokeUriPermission(Uri uri, int modeFlags) {
		getProperContext().revokeUriPermission(uri, modeFlags);
	}

	@Override
	public int checkUriPermission(Uri uri, int pid, int uid, int modeFlags) {
		return getProperContext().checkUriPermission(uri, pid, uid, modeFlags);
	}

	@Override
	public int checkCallingUriPermission(Uri uri, int modeFlags) {
		return getProperContext().checkCallingUriPermission(uri, modeFlags);
	}

	@Override
	public int checkCallingOrSelfUriPermission(Uri uri, int modeFlags) {
		return getProperContext().checkCallingOrSelfUriPermission(uri, modeFlags);
	}

	@Override
	public int checkUriPermission(Uri uri, String readPermission, String writePermission, int pid, int uid, int modeFlags) {
		return getProperContext().checkUriPermission(uri, readPermission, writePermission, pid, uid, modeFlags);
	}

	@Override
	public void enforceUriPermission(Uri uri, int pid, int uid, int modeFlags, String message) {
		getProperContext().enforceUriPermission(uri, pid, uid, modeFlags, message);
	}

	@Override
	public void enforceCallingUriPermission(Uri uri, int modeFlags, String message) {
		getProperContext().enforceCallingUriPermission(uri, modeFlags, message);
	}

	@Override
	public void enforceCallingOrSelfUriPermission(Uri uri, int modeFlags, String message) {
		getProperContext().enforceCallingOrSelfUriPermission(uri, modeFlags, message);
	}

	@Override
	public void enforceUriPermission(Uri uri, String readPermission, String writePermission, int pid, int uid, int modeFlags, String message) {
		getProperContext().enforceUriPermission(uri, readPermission, writePermission, pid, uid, modeFlags, message);
	}

	@Override
	public Context createPackageContext(String packageName, int flags) throws PackageManager.NameNotFoundException {
		return getProperContext().createPackageContext(packageName, flags);
	}

	@Override
	public boolean isRestricted() {
		return getProperContext().isRestricted();
	}
}