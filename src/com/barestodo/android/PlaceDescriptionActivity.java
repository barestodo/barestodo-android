package com.barestodo.android;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.barestodo.android.exception.AsyncCallerServiceException;
import com.barestodo.android.place.Place;
import com.barestodo.android.service.IPlaceRepository;
import com.barestodo.android.service.RepositoryFactory;

public class PlaceDescriptionActivity extends Activity {
	
	private TextView placeLabel;
	private Button scheduleButton;
	private String placeId;
	private String scheduleDate = "2010-01-01";
	IPlaceRepository placeRepository = RepositoryFactory.getPlaceRepository();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_place_description);
		Bundle b = getIntent().getExtras();
				
		placeLabel = (TextView) findViewById(R.id.placeLabel);
		scheduleButton = (Button)findViewById(R.id.scheduleButton);
		placeLabel.setText(scheduleDate);
		placeId = b.get("placeToShow").toString();
		initScheduleButton();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_place_description, menu);
		return true;
	}
	
	private void initScheduleButton() {
		scheduleButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try{
					Log.d("schedule On click", placeId);
					placeRepository.scheduleEvent(placeId, scheduleDate);				
					
					finish();
			    }catch (Exception e) {
			       Toast.makeText(PlaceDescriptionActivity.this,
			                    getResources().getText(R.string.error_back_to_main),
			                    Toast.LENGTH_LONG).show();
					}
			}
		});
	}
	
	
}
