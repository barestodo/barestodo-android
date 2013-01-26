package com.barestodo.android;

import java.util.logging.Logger;

import com.barestodo.android.service.IPlaceRepository;
import com.barestodo.android.service.ListPlace;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class PlaceDescriptionActivity extends Activity {
	
	private TextView placeLabel;
	private Button homeButton;
	
	IPlaceRepository placeRepository = ListPlace.INSTANCE ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_place_description);
		Bundle b = getIntent().getExtras();
				
		placeLabel = (TextView) findViewById(R.id.placeLabel);
		homeButton = (Button)findViewById(R.id.backButton);
		initHomeButton();
		placeLabel.setText(placeRepository.getPlaceById(b.getString("placeId")).getLabel());	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_place_description, menu);
		return true;
	}
	
	private void initHomeButton() {
		homeButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(PlaceDescriptionActivity.this,
						MainActivity.class);
				startActivity(intent);
			}
		});
	}

}
