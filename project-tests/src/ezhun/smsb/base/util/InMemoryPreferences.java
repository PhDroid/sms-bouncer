package ezhun.smsb.base.util;

import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

/**
 * Please, write short description of what this file is for.
 */
public class InMemoryPreferences implements SharedPreferences {
	private class InMemoryEditor implements Editor {

		public Editor putString(String s, String s1) {
			InMemoryPreferences.this.getPreferences().put(s, s1);
			return this;
		}

		public Editor putInt(String s, int i) {
			InMemoryPreferences.this.getPreferences().put(s, i);
			return this;
		}

		public Editor putLong(String s, long l) {
			InMemoryPreferences.this.getPreferences().put(s, l);
			return this;
		}

		public Editor putFloat(String s, float v) {
			InMemoryPreferences.this.getPreferences().put(s, v);
			return this;
		}

		public Editor putBoolean(String s, boolean b) {
			InMemoryPreferences.this.getPreferences().put(s, b);
			return this;
		}

		public Editor remove(String s) {
			InMemoryPreferences.this.getPreferences().remove(s);
			return this;
		}

		public Editor clear() {
			InMemoryPreferences.this.getPreferences().clear();
			return this;
		}

		public boolean commit() {
			return true;
		}
	}

	private Map<String, Object> preferences;
	private InMemoryEditor editor;

	public InMemoryPreferences() {
		this.preferences = new HashMap<String, Object>();
		this.editor = new InMemoryEditor();
	}

	private Map<String, Object> getPreferences() {
		return preferences;
	}

	public Map<String, Object> getAll() {
		return getPreferences();
	}

	public String getString(String s, String s1) {
		return getPreferences().get(s) != null ? (String)getPreferences().get(s) : s1;
	}

	public int getInt(String s, int i) {
		return getPreferences().get(s) != null ? (Integer)getPreferences().get(s) : i;
	}

	public long getLong(String s, long l) {
				return getPreferences().get(s) != null ? (Long)getPreferences().get(s) : l;
	}

	public float getFloat(String s, float v) {
				return getPreferences().get(s) != null ? (Float)getPreferences().get(s) : v;
	}

	public boolean getBoolean(String s, boolean b) {
				return getPreferences().get(s) != null ? (Boolean)getPreferences().get(s) : b;
	}

	public boolean contains(String s) {
		return getPreferences().containsKey(s);
	}

	public Editor edit() {
		return editor;
	}

	public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {
		throw new UnsupportedOperationException();
	}

	public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {
		throw new UnsupportedOperationException();
	}
}
