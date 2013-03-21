package com.barestodo.android;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.barestodo.android.exception.AsyncCallerServiceException;


public class AddPlaceToCircleActivity extends Activity {

	private EditText editLabel;
	private EditText editLocation;
	private ImageButton validateAdd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_place_to_circle);
	   
		editLabel = (EditText)findViewById(R.id.editLabel);
		editLocation = (EditText)findViewById(R.id.editLocation);
		validateAdd  = (ImageButton)findViewById(R.id.validateAddButton);
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
			try{
				validateAdd();
				finish();
			}catch (Exception e) {
			        	Toast.makeText(AddPlaceToCircleActivity.this,
			                    getResources().getText(R.string.error_place_creation),
			                    Toast.LENGTH_LONG).show();
            }
			}
		});
	}
	
	public void validateAdd(){
		Log.d("addActivity", editLabel.getText().toString());
        //TODO penser à mettre la place retournée dans la liste (elle a l'id)
        try{
		//placeRepository.addPlace(new Place(editLabel.getText().toString(),editLocation.getText().toString()));
        //FIXME FIX THAT !
        }catch(AsyncCallerServiceException e){
            StringBuilder builder = new StringBuilder(getResources().getText(R.string.error_place_creation));
            builder.append(":").append(e.getMessage());
            Toast.makeText(AddPlaceToCircleActivity.this,builder.toString(),Toast.LENGTH_LONG).show();
        }
    }

}
