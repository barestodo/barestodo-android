package com.barestodo.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.barestodo.android.place.Place;
import com.barestodo.android.service.IPlaceRepository;
import com.barestodo.android.service.RepositoryFactory;

public class AddPlaceActivity extends Activity {

	EditText editLabel;
	EditText editLocation;
	Button validateAdd;
	IPlaceRepository placeRepository = RepositoryFactory.getPlaceRepository();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_place);
	
		editLabel = (EditText)findViewById(R.id.editLabel);
		editLocation = (EditText)findViewById(R.id.editLocation);
		validateAdd  = (Button)findViewById(R.id.validateAddButton);
		initValidateButton();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_add_place, menu);
		return true;
	}
	
	private void initValidateButton() {
		validateAdd.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				validateAdd();
				Intent intent = new Intent(AddPlaceActivity.this,
						MainActivity.class);
				startActivity(intent);
			}
		});
	}
	
	public void validateAdd(){
		Log.d("addActivity", editLabel.getText().toString());
        //TODO penser à mettre la placeretournée dans la liste (elle a l'id)
		placeRepository.addPlace(new Place(editLabel.getText().toString(),editLocation.getText().toString()));
	}

}
