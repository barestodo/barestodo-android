package com.barestodo.android;

import java.util.logging.Logger;

import com.barestodo.android.service.IPlaceRepository;
import com.barestodo.android.service.ListPlace;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class PlaceDescriptionActivity extends Activity {
	
	TextView placeLabel;
	
	IPlaceRepository placeRepository = ListPlace.INSTANCE ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_place_description);
		Bundle b = getIntent().getExtras();
				
		placeLabel = (TextView) findViewById(R.id.placeLabel);
		
		if (b == null){
			placeLabel.setText("plouich");
		}else{
			placeLabel.setText(placeRepository.getPlaceById(b.getString("placeId")).getLabel());	
		}
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_place_description, menu);
		return true;
	}

}
