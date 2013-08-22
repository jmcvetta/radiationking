package us.heliotropic.radiationking;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;

public class ConfigActivity extends Activity implements OnItemSelectedListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_config);
		//
		// Populate spinner
		//
		BiMap<Integer, String> map = wakeupChoices();
		String[] spinnerArray = map.values().toArray(new String[0]);
		Spinner spinner = (Spinner) findViewById(R.id.spinner_wakeup);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, spinnerArray);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(this);
		//
		// Load preferences
		//
		SharedPreferences prefs = this.getSharedPreferences(
				getString(R.string.prefs_file), Context.MODE_PRIVATE);
		// Enabled Toggle
		Boolean enabled = prefs.getBoolean(
				getString(R.string.prefs_enabled_key), false);
		ToggleButton button = (ToggleButton) findViewById(R.id.toggle_enabled);
		button.setChecked(enabled);
		// Wakeup Interval Spinner
		Integer defaultWakeupInterval = getResources().getInteger(
				R.integer.wakeup_interval_default);
		Integer wakeupInterval = prefs.getInt(
				getString(R.string.prefs_wakeup_interval_key),
				defaultWakeupInterval);
		String intervalString = map.get(wakeupInterval);
		Integer spinnerPosition = adapter.getPosition(intervalString);
		spinner.setSelection(spinnerPosition);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.config, menu);
		return true;
	}

	public final ImmutableBiMap<Integer, String> wakeupChoices() {
		Integer intervals[] = { 10, 15, 30, 60 };

		ImmutableBiMap.Builder<Integer, String> builder = new ImmutableBiMap.Builder<Integer, String>();

		builder.put(0, getString(R.string.never));

		String template = getString(R.string.wakeup_interval_minutes);
		for (Integer i : intervals) {
			builder.put(i, String.format(template, i));
		}
		builder.build();

		final ImmutableBiMap<Integer, String> map = builder.build();

		return map;
	}

	public void onToggleClicked(View view) {
		SharedPreferences prefs = this.getSharedPreferences(
				getString(R.string.prefs_file), Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		String prefs_key = getString(R.string.prefs_enabled_key);
		Boolean origEnabled = prefs.getBoolean(prefs_key, false);
		boolean enabled = ((ToggleButton) view).isChecked();
		editor.putBoolean(prefs_key, enabled);
		editor.commit();
		if (enabled != origEnabled) {
			String template = getString(R.string.toast_toggle_enabled);
			String message;
			if (enabled) {
				message = String.format(template,
						getString(R.string.capital_enabled));
			} else {
				message = String.format(template,
						getString(R.string.capital_disabled));
			}
			Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

		}
	}

	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		SharedPreferences prefs = this.getSharedPreferences(
				getString(R.string.prefs_file), Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		String prefs_key = getString(R.string.prefs_wakeup_interval_key);
		Integer origInterval = prefs.getInt(prefs_key, getResources()
				.getInteger(R.integer.wakeup_interval_default));
		String selected = (String) parent.getItemAtPosition(pos);
		Integer interval = wakeupChoices().inverse().get(selected);
		editor.putInt(prefs_key, interval);
		editor.commit();
		if (interval != origInterval) {
			String message = String.format(
					getString(R.string.toast_set_wakeup_interval), selected);
			Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
		}
	}

	public void onNothingSelected(AdapterView<?> parent) {
		// Do nothing (?)
	}

}
