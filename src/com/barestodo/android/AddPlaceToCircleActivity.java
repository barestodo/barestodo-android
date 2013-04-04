package com.barestodo.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.barestodo.android.model.Place;
import com.barestodo.android.service.tasks.AsyncCreatePlaceOperation;
import com.barestodo.android.service.tasks.HttpStatus;

import static com.barestodo.android.service.tasks.AsyncCreatePlaceOperation.PlaceReceiver;


public class AddPlaceToCircleActivity extends Activity implements PlaceReceiver {

    public static final String CIRCLE_ID="circle.id";

	private EditText editLabel;
	private EditText editLocation;
	private ImageButton validateAdd;
    private Long circleId;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_place_to_circle);
	   
		editLabel = (EditText)findViewById(R.id.nameEdit);
		editLocation = (EditText)findViewById(R.id.editLocation);
		validateAdd  = (ImageButton)findViewById(R.id.validateAddButton);
        Bundle b = getIntent().getExtras();
        circleId=b.getLong(CIRCLE_ID);
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
        new AsyncCreatePlaceOperation(circleId,new Place(editLabel.getText().toString(),editLocation.getText().toString()),this).execute();
    }

    @Override
    public void receivePlace(Place place) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(CirclePlacesListActivity.NEW_PLACE_CREATED,place);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public void onError(HttpStatus status) {
        Toast.makeText(AddPlaceToCircleActivity.this,
                getResources().getText(R.string.error_place_creation),
                Toast.LENGTH_LONG).show();
    }
}
