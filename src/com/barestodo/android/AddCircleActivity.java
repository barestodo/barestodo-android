package com.barestodo.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import com.barestodo.android.model.Circle;
import com.barestodo.android.service.tasks.AsyncCreateCircleOperation;
import com.barestodo.android.service.tasks.HttpStatus;


public class AddCircleActivity extends Activity implements AsyncCreateCircleOperation.CircleReceiver {


	private EditText nameInput;
	private ImageButton validateAdd;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_circle);
	   
		nameInput = (EditText)findViewById(R.id.circleNameEdit);
		validateAdd  = (ImageButton)findViewById(R.id.validateAddButton);
        initValidateButton();
	}

	private void initValidateButton() {
		validateAdd.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			try{
				validateAdd();
			}catch (Exception e) {
			        	Toast.makeText(AddCircleActivity.this,
			                    getResources().getText(R.string.error_circle_creation),
			                    Toast.LENGTH_LONG).show();
            }
			}
		});
	}
	
	public void validateAdd(){
		Log.d("addActivity", nameInput.getText().toString());
        new AsyncCreateCircleOperation(nameInput.getText().toString(),this).execute();
    }

    @Override
    public void receiveCircle(Circle circle) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(CirclesListActivity.NEW_CIRCLE_CREATED,circle);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public void onError(HttpStatus status) {
        Toast.makeText(AddCircleActivity.this,
                getResources().getText(R.string.error_place_creation),
                Toast.LENGTH_LONG).show();
    }
}
