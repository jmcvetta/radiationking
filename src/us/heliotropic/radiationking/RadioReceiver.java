package us.heliotropic.radiationking;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

public class RadioReceiver extends BroadcastReceiver {

	private static final String TAG = "RadioReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		Resources res = context.getResources();
		SharedPreferences prefs = context.getSharedPreferences(
				res.getString(R.string.prefs_file), Context.MODE_PRIVATE);
		Boolean enabled = prefs.getBoolean(
				res.getString(R.string.prefs_enabled_key), false);
		if (!enabled) {
			// Do nothing
			Toast.makeText(context, "RadiationKing not enabled",
					Toast.LENGTH_SHORT).show();
			return;
		}
		if (intent.getAction().equals(Intent.ACTION_USER_PRESENT)) {
			Toast.makeText(context, "User present!", Toast.LENGTH_SHORT).show();
			setAirplaneMode(context, true);
			Log.i(TAG, "Set airplane mode TRUE");
		}
		if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
			Toast.makeText(context, "Screen off!", Toast.LENGTH_SHORT).show();
			setAirplaneMode(context, false);
			Log.i(TAG, "Set airplane mode FALSE");
		}

	}

	public static void setAirplaneMode(Context context, Boolean enabled) {
		Settings.System.putInt(context.getContentResolver(),
				Settings.System.AIRPLANE_MODE_ON, enabled ? 0 : 1);

		// Post an intent to reload
		Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
		intent.putExtra("state", !enabled);
		context.sendBroadcast(intent);
		Toast.makeText(context, "Fubar!", Toast.LENGTH_SHORT).show();
	}

}
