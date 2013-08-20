package us.heliotropic.radiationking;

import java.util.TreeMap;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class ConfigActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_config);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.config, menu);
		return true;
	}
	
	public static TreeMap<Integer, String> wakeupChoices() {    
		Integer intervals[] = {10, 15, 30, 60};
	    TreeMap<Integer, String> map = new TreeMap<Integer,String>();

	    map.put(0, "Never");
	    
	    for (Integer i : intervals) {
	    	String s = "Every ";
	    	s += i.toString();
	    	s += " minutes";
	    	map.put(i, s);
	    }

	    return map;
	}


}
