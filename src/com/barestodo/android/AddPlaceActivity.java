package com.barestodo.android;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddPlaceActivity extends Activity {

	EditText editLabel;
	EditText editLocation;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_place);
	
		editLabel = (EditText)findViewById(R.id.editLabel);
		editLocation = (EditText)findViewById(R.id.editLocation);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_add_place, menu);
		return true;
	}
	


}
