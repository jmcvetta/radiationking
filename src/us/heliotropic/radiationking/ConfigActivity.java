package us.heliotropic.radiationking;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;

public class ConfigActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_config);
		
		BiMap<Integer, String> map = wakeupChoices();
		String[] spinnerArray = map.values().toArray(new String[0]);
		
		Spinner spinner = new Spinner(this);
	    ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this,
	        R.layout.activity_config, spinnerArray);
	    spinner.setAdapter(spinnerArrayAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.config, menu);
		return true;
	}
	
	public static final ImmutableBiMap<Integer, String> wakeupChoices() {    
		Integer intervals[] = {10, 15, 30, 60};

	    ImmutableBiMap.Builder<Integer, String> builder = new ImmutableBiMap.Builder<Integer, String>();

	    builder.put(0, "Never");
	    
	    for (Integer i : intervals) {
	    	String s = "Every ";
	    	s += i.toString();
	    	s += " minutes";
	    	builder.put(i, s);
	    }
	    builder.build();
	    
	    final ImmutableBiMap<Integer, String> map = builder.build();

	    return map;
	}


}
